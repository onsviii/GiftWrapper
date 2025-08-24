package com.nulp.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nulp.backend.comparators.SweetComparator;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gift{
    private final List<Sweet> sweets;
    private double weight;

    public Gift(List<Sweet> sweets) {
        this.sweets = sweets;

        for (Sweet sweet : sweets) {
            this.weight += sweet.getWeight();
        }
    }

    public double getWeight() {return weight; }

    public List<Sweet> getSweets() {
        return new ArrayList<>(sweets);
    }

    public void setSweets(List<Sweet> sweets) {
        this.sweets.clear();
        this.sweets.addAll(sweets);
    }

    public List<Sweet> getUniqueSweets() {
        List<Sweet> uniqueSweets = new ArrayList<>();

        for (Sweet sweet : sweets) {
            if (!uniqueSweets.contains(sweet)) {
                uniqueSweets.add(sweet);
            }
        }

        return uniqueSweets;
    }

    public int getSweetQuantity(Sweet sweet) {
        int quantity = 0;

        for (Sweet thisSweet : sweets) {
            if (thisSweet.equals(sweet)) {
                quantity++;
            }
        }

        return quantity;
    }

    public double getTotalWeightSweet(Sweet sweet) {
        double totalWeight = 0;

        for (Sweet thisSweet : sweets) {
            if (thisSweet.equals(sweet)) {
                totalWeight += thisSweet.getWeight();
            }
        }

        return totalWeight;
    }

    public Sweet getSweet(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.getUniqueSweets().size()) {
            throw new IndexOutOfBoundsException("Index was outside the"
                    + " bounds of the array");
        }

        return this.getUniqueSweets().get(index);
    }

    public void addSweet(Sweet sweet, int quantity) throws
            IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than"
                    + " zero");
        }

        for (int i = 0; i < quantity; i++) {
            sweets.add(sweet);
            weight += sweet.getWeight();
        }
    }

    public void deleteSweet(Sweet removedSweet, int quantity) throws
            IllegalArgumentException {
        if (sweets.isEmpty()) {
            throw new IllegalStateException("Unable to remove sweets. The gift"
                    + " is empty");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than"
                    + " zero");
        } else if (quantity > this.getSweetQuantity(removedSweet)) {
            throw new IllegalArgumentException("Quantity must be less than"
                    + " total quantity of current sweets");
        } else if (!sweets.contains(removedSweet)) {
            throw new IllegalArgumentException("Sweets must be in the gift to"
                    + " remove them");
        }

        int currentQuantity = 0;

        for (int i = 0; i < sweets.size();) {
            if (currentQuantity == quantity) {
                return;
            }

            Sweet thisSweet = sweets.get(i);

            if (removedSweet.equals(thisSweet)) {
                weight -= thisSweet.getWeight();
                sweets.remove(thisSweet);
                currentQuantity++;
                continue;
            }

            i++;
        }
    }

    public void deleteSweet(int index, int quantity) throws IllegalArgumentException,
            IndexOutOfBoundsException {
        if (sweets.isEmpty()) {
            throw new IllegalStateException("Unable to remove sweets. The gift"
                    + " is empty");
        } else if (index < 0 || index >= this.getUniqueSweets().size()) {
            throw new IndexOutOfBoundsException("Index was outside the"
                    + " bounds of the array");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than"
                    + " zero");
        } else if (quantity > this.getSweetQuantity(this.getSweet(index))) {
            throw new IllegalArgumentException("Quantity must be less than"
                    + " total quantity of current sweets");
        }

        int currentQuantity = 0;
        Sweet removedSweet = this.getUniqueSweets().get(index);

        for (int i = 0; i < sweets.size();) {
            if (currentQuantity == quantity) {
                return;
            }

            Sweet thisSweet = sweets.get(i);

            if (removedSweet.equals(thisSweet)) {
                weight -= thisSweet.getWeight();
                sweets.remove(thisSweet);
                currentQuantity++;
                continue;
            }

            i++;
        }
    }

    public void deleteAllSweets() {
        if (sweets.isEmpty()) {
            throw new IllegalStateException("Unable to remove sweets. The gift"
                    + " is empty");
        }

        sweets.clear();
        weight = 0d;
    }

    public void sortSweets(SweetComparator comparator) throws
            IllegalArgumentException {
        if (sweets.isEmpty()) {
            throw new IllegalStateException("Unable to sort gift. The gift"
                    + " is empty");
        }

        sweets.sort(comparator);
    }

}
