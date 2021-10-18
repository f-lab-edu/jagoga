package com.project.jagoga.roominventory.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryAddPeriodValidator implements ConstraintValidator<InventoryAddPeriod, InventoryValidPeriod> {

    @Override
    public void initialize(InventoryAddPeriod constraintAnnotation) {

    }

    @Override
    public boolean isValid(InventoryValidPeriod validPeriod, ConstraintValidatorContext context) {
        return validPeriod.isValidPeriod();
    }
}
