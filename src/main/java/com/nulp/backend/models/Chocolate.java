package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chocolate extends Sweet{
    @JsonProperty("chocolateType")
    protected final String chocolateType;

    public Chocolate() {
        super("Dark Chocolate Bar", 100,550,35,50,40,6);
        this.chocolateType = "Dark Chocolate";
    }

    public Chocolate (String name, double weight, double calories, double fat,
                 double carbohydrates, double sugars, double proteins,
                 String chocolateType) {
        super(name, weight, calories, fat, carbohydrates, sugars, proteins);
        this.chocolateType = chocolateType;
    }

    @Override
    public String toString() {
        return String.format("""
            Name: %s
            \tsweet type: %s
            \tchocolate type: %s
            \tweight: %.2f
            \tcalories: %.2f
            \tfat: %.2f
            \tcarbohydrates: %.2f
            \tsugars: %.2f
            \tproteins: %.2f
            """, name, sweetType, chocolateType, weight, calories, fat,
                carbohydrates, sugars, proteins);
    }
}
