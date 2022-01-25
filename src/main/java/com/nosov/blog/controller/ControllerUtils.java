package com.nosov.blog.controller;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtils {

  static Map<String, String> getErrors(BindingResult bindingResult) {
    Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
        fieldError -> fieldError.getField() + "Error",
        fieldError -> fieldError.getDefaultMessage()
    );
    Map<String, String> errorsMap = bindingResult.getFieldErrors().stream()
        .collect(fieldErrorMapCollector);
    return errorsMap;
  }


}
