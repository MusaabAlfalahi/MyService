package net.shahid.microservices.myservice.domain.enums;

import lombok.Getter;
import net.shahid.microservices.myservice.exception.InValidTypeValueException;

@Getter
public enum Type {
    LIVE("live"),
    MOVIE("movie"),
    SERIES("series");

    private final String value;
    Type(String value) {
        this.value = value;
    }

    public static Type getByName(String type) {
        try {
            return Type.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new InValidTypeValueException("Invalid type enum value");
        }
    }
}
