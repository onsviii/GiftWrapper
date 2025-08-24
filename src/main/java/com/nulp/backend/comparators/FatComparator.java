package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class FatComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getFat(), sweet2.getFat())
                : Double.compare(sweet2.getFat(), sweet1.getFat());
    }

    @Override
    public String getNameCriteria() {
        return "Fat";
    }
}
