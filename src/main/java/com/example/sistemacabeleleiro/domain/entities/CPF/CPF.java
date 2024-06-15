package com.example.sistemacabeleleiro.domain.entities.CPF;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF {
    private final String value;
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    private CPF(String value) {
        if (value == null || !isValidCPF(value)) {
            throw new IllegalArgumentException("Invalid CPF format: " + value);
        }
        this.value = value;
    }

    public static CPF of(String value) {
        return new CPF(value);
    }

    public String getValue() {
        return value;
    }

    public boolean isValidCPF(String cpf) {
        Matcher matcher = CPF_PATTERN.matcher(cpf);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf = (CPF) o;
        return Objects.equals(value, cpf.value);
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
