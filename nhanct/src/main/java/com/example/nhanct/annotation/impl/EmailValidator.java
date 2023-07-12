package com.example.nhanct.annotation.impl;

import com.example.nhanct.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * demo
 *
 * @author Smartee
 * @date 7/3/2023 9:36 AM
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email email) {
        System.out.println("initialize : EmailValidator");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        return s != null && isValidEmailFormat(s);
    }

    public static boolean isValidEmailFormat(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@gmail.com";
        return email.matches(regex);
    }
}