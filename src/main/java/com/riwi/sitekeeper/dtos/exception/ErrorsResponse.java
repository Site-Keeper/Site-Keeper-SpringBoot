package com.riwi.sitekeeper.dtos.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ErrorsResponse extends SimpleError {
    private List<String> errors;
}
