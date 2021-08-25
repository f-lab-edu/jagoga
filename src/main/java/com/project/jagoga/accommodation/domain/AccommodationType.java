package com.project.jagoga.accommodation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.jagoga.exception.accommodation.NotFoundAccommodationTypeException;

import java.util.HashMap;
import java.util.Map;

public enum AccommodationType {
    PENSION;

    public static Map<String, AccommodationType> map = new HashMap<>();
    static {
        map.put(PENSION.name(), PENSION);
    }

    @JsonCreator
    public static AccommodationType getType(String typeName) {
        if (map.get(typeName) == null) {
            throw new NotFoundAccommodationTypeException();
        }
        return map.get(typeName);
    }
}
