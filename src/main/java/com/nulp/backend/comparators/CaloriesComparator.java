package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class CaloriesComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getCalories(), sweet2.getCalories())
                : Double.compare(sweet2.getCalories(), sweet1.getCalories());
    }

    @Override
    public String getNameCriteria() {
        return "Calories";
    }
}
