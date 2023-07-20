package com.example.nhanct.annotation;

import com.example.nhanct.annotation.impl.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * demo
 *
 * @author NhanCT
 * @date 7/3/2023 9:35 AM
 */
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Email {

    public String message() default "Email was wrong format!";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

}