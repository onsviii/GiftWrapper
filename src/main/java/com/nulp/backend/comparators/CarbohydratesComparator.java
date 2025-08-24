package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class CarbohydratesComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getCarbohydrates(), sweet2.getCarbohydrates())
                : Double.compare(sweet2.getCarbohydrates(), sweet1.getCarbohydrates()) ;
    }

    @Override
    public String getNameCriteria() {
        return "Carbohydrates";
    }
}
