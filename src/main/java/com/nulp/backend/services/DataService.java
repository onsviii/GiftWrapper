package com.nulp.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulp.backend.comparators.*;
import com.nulp.backend.models.Category;
import com.nulp.backend.models.Sweet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private List<Category> categories;
    private List<SweetComparator> comparators;
    private List<Sweet> allSweets;
    private static DataService instance;

    private DataService() throws IOException{
            loadSweets();
            loadComparators();
            extractAllSweets();
    }

    public static DataService getInstance() throws IOException{
        if (instance == null) {
            instance = new DataService();
        }

        return instance;
    }

    private void loadSweets() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        categories = mapper.readValue(new File("src/main/resources/DB.json"),
                new TypeReference<>() {});
    }

    /**
     * Creates a list of available comparators.
     *
     * @return List of available comparators.
     */
    private void loadComparators() {
        comparators = new ArrayList<>();

        comparators.add(new NameComparator());
        comparators.add(new SweetTypeComparator());
        comparators.add(new WeightComparator());
        comparators.add(new CaloriesComparator());
        comparators.add(new FatComparator());
        comparators.add(new CarbohydratesComparator());
        comparators.add(new SugarsComparator());
        comparators.add(new ProteinsComparator());
    }

    private void extractAllSweets() {
        allSweets = new ArrayList<>();
        for (Category category : categories) {
            allSweets.addAll(category.getSweets());
        }
    }

    public Sweet findSweet(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }

        for (Sweet sweet : allSweets) {
            if (sweet.getName().equals(name)) {
                return sweet;
            }
        }

        return null;
    }

    public Category findCategory(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }

        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }

        return null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<SweetComparator> getComparators() {
        return comparators;
    }
    public List<Sweet> getAllSweets() {
        return allSweets;
    }
}
