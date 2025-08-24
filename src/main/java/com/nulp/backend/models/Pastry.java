package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pastry extends Sweet{
    @JsonProperty("crustType")
    protected final String crustType;
    @JsonProperty("filling")
    protected final String filling;

    public Pastry() {
        super("Napoleon", 80.0, 372.0,22.0, 39.0, 12.0, 6.0);
        this.crustType = "Flaky";
        this.filling = "Cream";
    }

    public Pastry(String name, double weight, double calories, double fat,
                  double carbohydrates, double sugars, double proteins,
                  String crustType, String filling) {
        super(name, weight, calories, fat, carbohydrates, sugars, proteins);
        this.crustType = crustType;
        this.filling = filling;
    }

    public String toString() {
        return String.format("""
                Name: %s
                \tsweet type: %s
                \tcrust type: %s
                \tfilling: %s
                \tweight: %.2f
                \tcalories: %.2f
                \tfat: %.2f
                \tcarbohydrates: %.2f
                \tsugars: %.2f
                \tproteins: %.2f
                """, name, sweetType, crustType, filling, weight, calories, fat,
                carbohydrates, sugars, proteins);
    }
}
