package com.sm.gce.common.model.enums;

public enum EventType {
    MASS, VIGIL_MASS, EF_MASS, ADORATION, CONFESSION, LEGION_OF_MARY, KNIGHTS_OF_COLUMBUS, PATRICIANS, OTHER;

    public static EventType getEventType(String value) {
        if (value.equals("mass") || value.equals("Mass")) {
            return MASS;
        }
        // TODO - add more translations
        return OTHER;
    }
}
