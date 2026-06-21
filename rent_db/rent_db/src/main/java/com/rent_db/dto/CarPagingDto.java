package com.rent_db.dto;


import java.util.List;

public record CarPagingDto(
        long totalRecords,
        int totalPages,
        List<CarDto> data
){
}
