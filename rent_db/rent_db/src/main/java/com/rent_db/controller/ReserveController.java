package com.rent_db.controller;

import com.rent_db.dto.*;
import com.rent_db.enums.Reservation_Status;
import com.rent_db.model.Reservation;
import com.rent_db.service.ReserveService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservation")
@CrossOrigin(origins="http://localhost:5173")
public class ReserveController {
    private final ReserveService reserveService;
    @GetMapping("/all")
    public ReservePagingDto getAll(@RequestParam int page,@RequestParam int size){
        return reserveService.getAll(page,size);
    }

    @GetMapping("/get-id/{id}")
    public Reservation getById(@PathVariable int id){
        return reserveService.getById(id);
    }

    @GetMapping("/getByStatus")
    public ReservePagingDto getByStatus(Principal principal,@RequestParam Reservation_Status reservationStatus,@RequestParam int page,@RequestParam int size){
        String username = principal.getName();
        return reserveService.getByStatus(username,reservationStatus,page,size);
    }

    @PostMapping("/add")
    public void addReservation(@RequestBody ReserveReqDto reserveReqDto, Principal principal){
        String UserName = principal.getName();
        reserveService.addReservation(reserveReqDto,UserName);
    }

    @PutMapping("/update/{id}")
    public void updateReservation(
            @PathVariable int id,
            @RequestBody ReserveReqDto dto,Principal principal) {
        String username = principal.getName();
        reserveService.updateReservation(id,dto,username);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) {

        reserveService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully");
    }

    @GetMapping("/all/for-customer")
    public List<ReserveAllRespDto> getReservationByCustomer(Principal principal){
        String customerUsername = principal.getName();
        return reserveService.getReservationByCustomer(customerUsername);
    }

    @GetMapping("/all/for-agent")
    public ReservePagingDto getReservationByAgent(Principal principal,@RequestParam(defaultValue="0",required=false)int page,
                                                     @RequestParam(defaultValue="10",required =false)int size){
        String agentUsername = principal.getName();
        return reserveService.getReservationByAgent(agentUsername,page,size);
    }

    @PostMapping("/assign/{reservationId}")
    public void assignReservationToAgent(@RequestBody AgentDto dto, @PathVariable int reservationId){
        reserveService.assignReservationToAgent(dto,reservationId);
    }

    @PostMapping("/accepts_reservation")
    public void addMileageFuelStatus(Principal principal,@RequestBody mileageFuelDto dto){
        String username = principal.getName();
        reserveService.addMileageFuelStatus(username,dto);
    }

    @PostMapping("/getOverallAmount/{id}")//calculated by the rental agent
    public double calculateExtraAmount(@PathVariable int id,@RequestBody mileageFuelDto dto){

        return reserveService.calculateExtraAmount(id,dto);
    }

    @PostMapping("/approveReservation/{id}")
    public void approveReservation(@PathVariable int id){
        reserveService.approveReservation(id);
    }

    @PutMapping("/return/{id}")
    public void returnCar(@PathVariable int id){
        reserveService.returnCar(id);
    }

}
