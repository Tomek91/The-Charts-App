package com.app.listaprzebojow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Info<T> {

    private T info;

    @Builder.Default
    private String error = "";

    @Builder.Default
    private HttpStatus httpStatusCode = HttpStatus.OK;
}
