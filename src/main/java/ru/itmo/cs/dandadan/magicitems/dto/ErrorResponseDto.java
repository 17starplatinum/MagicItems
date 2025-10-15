package ru.itmo.cs.dandadan.magicitems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private int code;
    private String message;
    private long timestamp;
}
