package com.moses.model;

import com.google.gson.annotations.SerializedName;

public enum StatusEnum {
    @SerializedName("inactive")
    INACTIVE(0),

    @SerializedName("active")
    ACTIVE(1);

    private final int value;

    public int getValue() {
        return value;
    }

    private StatusEnum(int value) {
        this.value = value;
    }
}
