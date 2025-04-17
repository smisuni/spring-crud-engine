package com.springcrudengine.product_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContainsITCareValidator implements ConstraintValidator<ContainsITCare, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotBlank or @NotNull handle nulls
        }
        return value.contains("IT-Care");
    }
}
