package com.moses.model;

import org.parceler.Parcel;

@Parcel
public class Currency {
    private String prefix;
    private String description;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}