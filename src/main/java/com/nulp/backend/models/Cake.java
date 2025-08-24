package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cake extends Sweet{
    @JsonProperty("doughType")
    protected final String doughType;
    @JsonProperty("filling")
    protected final String filling;

    public Cake() {
        super("Napoleon", 80.0, 372.0,22.0, 39.0, 12.0, 6.0);
        this.doughType = "Flaky";
        this.filling = "Cream";
    }

    public Cake(String name, double weight, double calories, double fat,
                  double carbohydrates, double sugars, double proteins,
                  String doughType, String filling) {
        super(name, weight, calories, fat, carbohydrates, sugars, proteins);
        this.doughType = doughType;
        this.filling = filling;
    }

    public String toString() {
        return String.format("""
            Name: %s
            \tsweet type: %s
            \tdough type: %s
            \tfilling: %s
            \tweight: %.2f
            \tcalories: %.2f
            \tfat: %.2f
            \tcarbohydrates: %.2f
            \tsugars: %.2f
            \tproteins: %.2f
            """, name, sweetType, doughType, filling, weight, calories, fat,
                carbohydrates, sugars, proteins);
    }
}
