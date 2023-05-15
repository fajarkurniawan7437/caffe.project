package org.project.model.constant;

public enum Gender {
    MALE("male", "M"),
    FEMALE("female", "F");

    private String name;
    private String code;

    Gender(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
