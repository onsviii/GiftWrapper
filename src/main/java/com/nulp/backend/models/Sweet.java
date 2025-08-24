package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "sweetType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Candy.class, name = "Candy"),
        @JsonSubTypes.Type(value = Cookie.class, name = "Cookie"),
        @JsonSubTypes.Type(value = Pastry.class, name = "Pastry"),
        @JsonSubTypes.Type(value = Cake.class, name = "Cake"),
        @JsonSubTypes.Type(value = Chocolate.class, name = "Chocolate")})
public abstract class Sweet {
    protected final String name;
    protected final String sweetType;
    protected final double weight;
    protected final double calories;
    protected final double fat;
    protected final double carbohydrates;
    protected final double sugars;
    protected final double proteins;

    public Sweet(String name, double weight, double calories, double fat,
                  double carbohydrates, double sugars, double proteins) {
        this.name = name;
        this.weight = weight;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.sugars = sugars;
        this.proteins = proteins;
        this.sweetType = this.getClass().getSimpleName();;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getCalories() {
        return calories;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getSugars() {
        return sugars;
    }

    public double getProteins() {
        return proteins;
    }

    public String getSweetType() { return sweetType; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Sweet sweet = (Sweet) obj;

        if (weight - sweet.getWeight() > 0.00001) return false;
        if (calories - sweet.getCalories() > 0.00001) return false;

        return (name.equals(sweet.getName())
                && sweetType.equals(sweet.getSweetType()));
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = result * 31 + sweetType.hashCode();
        result = result * 31 + (int) weight;
        result = result * 31 + (int) calories;

        return result;
    }
}
