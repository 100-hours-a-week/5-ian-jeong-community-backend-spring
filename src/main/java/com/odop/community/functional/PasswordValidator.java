package com.odop.community.functional;

@FunctionalInterface
public interface PasswordValidator {
    boolean validate(String rawPassword, String encodedPassword);
}
