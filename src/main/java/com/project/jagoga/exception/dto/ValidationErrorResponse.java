package com.project.jagoga.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ValidationErrorResponse {

    private List<ErrorField> errors;

    public ValidationErrorResponse(List<ErrorField> errors) {
        this.errors = errors;
    }

    public static ValidationErrorResponse from(BindingResult bindingResult) {
        return new ValidationErrorResponse(ErrorField.from(bindingResult));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorField {
        private String field;
        private String value;
        private String reason;

        public static List<ErrorField> from(BindingResult bindingResult) {
            List<ErrorField> errorFields = bindingResult.getAllErrors().stream().map(error ->
                    new ErrorField(((FieldError) error).getField(), String.valueOf(((FieldError) error).getRejectedValue()),
                            ((FieldError) error).getDefaultMessage())).collect(Collectors.toList());
            return errorFields;
        }
    }
}
