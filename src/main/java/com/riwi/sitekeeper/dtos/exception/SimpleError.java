package com.riwi.sitekeeper.dtos.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SimpleError {
    private Integer code;
    private String status;
}