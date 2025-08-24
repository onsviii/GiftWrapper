package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Candy extends Sweet {
    @JsonProperty("candyType")
    protected final String candyType;

    public Candy() {
        super("Chocolate Candy", 15,560,39,46,40,7);
        this.candyType = "Dark Chocolate";
    }

    public Candy(String name, double weight, double calories, double fat,
                 double carbohydrates, double sugars, double proteins,
                 String candyType) {
        super(name, weight, calories, fat, carbohydrates, sugars, proteins);
        this.candyType = candyType;
    }

    @Override
    public String toString() {
        return String.format("""
                Name: %s
                \tsweet type: %s
                \tcandy type: %s
                \tweight: %.2f
                \tcalories: %.2f
                \tfat: %.2f
                \tcarbohydrates: %.2f
                \tsugars: %.2f
                \tproteins: %.2f
                """, name, sweetType, candyType, weight, calories, fat,
                carbohydrates, sugars, proteins);
    }
}

