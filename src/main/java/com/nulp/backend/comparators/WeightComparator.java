package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class WeightComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getWeight(), sweet2.getWeight())
                : Double.compare(sweet2.getWeight(), sweet1.getWeight());
    }

    @Override
    public String getNameCriteria() {
        return "Weight";
    }
}
