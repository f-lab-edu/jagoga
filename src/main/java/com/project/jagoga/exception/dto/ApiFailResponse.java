package com.project.jagoga.exception.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiFailResponse {

    private final String status = "fail";
    private List<ErrorField> data;

    private ApiFailResponse(List<ErrorField> errors) {
        this.data = errors;
    }

    public static ApiFailResponse createInstance(BindingResult bindingResult) {
        return new ApiFailResponse(ErrorField.createInstance(bindingResult));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorField {
        private String field;
        private String reason;

        public static List<ErrorField> createInstance(BindingResult bindingResult) {
            List<ErrorField> errorFields = bindingResult.getAllErrors().stream().map(error ->
                    new ErrorField(((FieldError) error).getField(),
                            ((FieldError) error).getDefaultMessage())).collect(Collectors.toList());
            return errorFields;
        }
    }
}
