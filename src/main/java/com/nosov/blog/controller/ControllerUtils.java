package com.nosov.blog.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                fieldError -> fieldError.getDefaultMessage()
        );
        Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(fieldErrorMapCollector);
        return errorsMap;
    }


}
