package com.springcrudengine.product_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ContainsITCareValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface ContainsITCare {
    String message() default "Product name must contain 'IT-Care'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
