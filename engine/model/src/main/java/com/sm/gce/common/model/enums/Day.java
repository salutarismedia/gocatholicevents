package com.sm.gce.common.model.enums;

public enum Day {
    CHRISTMAS, EASTER, HOLY, SUN, MON, TUE, WED, THU, FRI, SAT, FIRST_SAT, FIRST_FRI;

    public static Day getValue(String value) {
        if (value.equalsIgnoreCase("saturday") || value.equalsIgnoreCase("sat")) {
            return SAT;
        } else if (value.equalsIgnoreCase("sunday")
                || value.equalsIgnoreCase("sun")) {
            return SUN;
        }
        return null;
    }
}
