package com.statkevich.receipttask.util;

import com.statkevich.receipttask.domain.dto.ProductCreateDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private final static Pattern ROW_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");


    private ValidationUtil() {
        throw new IllegalStateException("Utils class");
    }

    public static boolean validate(ProductCreateDto productCreateDto) {
        String name = productCreateDto.name();
        if (name.isEmpty() || name.isBlank()) {
            return false;
        }
        Matcher matcher = ROW_PATTERN.matcher(name);
        return matcher.matches();
    }

}
