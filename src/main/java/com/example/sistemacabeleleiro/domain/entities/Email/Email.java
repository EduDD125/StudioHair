package com.example.sistemacabeleleiro.domain.entities.Email;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Email {
    private String value;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    private Email(String value) {
        if (value == null || !isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String getValue() {
        return value;
    }
    public void setValue(String valeu) { this.value = value;}

    public boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
