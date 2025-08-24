package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @JsonProperty("category")
    private String name;
    private List<Sweet> sweets;

    public Category() {}

    public Category(String name, List<Sweet> sweets) {
        this.name = name;
        this.sweets = sweets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sweet> getSweets() {
        return sweets;
    }

    public void setSweets(List<Sweet> sweets) {
        this.sweets = sweets;
    }
}
