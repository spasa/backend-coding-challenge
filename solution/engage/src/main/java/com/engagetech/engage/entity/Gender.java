package com.engagetech.engage.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum Gender {
    
    MALE              ((short) 1, "Male", ""),
    FEMALE            ((short) 2, "Female", ""),
    OTHER             ((short) 3, "Other", "");
    
    private static final Map<Short, Gender> valuesMap = new HashMap<Short, Gender>();

    static {
        for (Gender g : EnumSet.allOf(Gender.class)) {
            valuesMap.put(g.getId(), g);
        }
    }
    
    private short id;
    private String code;
    private String description;

    private Gender(short id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public short getId() {
        return id;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }

    public static Gender get(short id) {
        return valuesMap.get(id);
    }
    
}
