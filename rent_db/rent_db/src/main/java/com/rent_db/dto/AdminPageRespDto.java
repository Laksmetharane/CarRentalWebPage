package com.rent_db.dto;

import java.util.List;

public record AdminPageRespDto(
        long totalRecords,
        int totalPages,
        List<AdminRespDto> data
) {
}
