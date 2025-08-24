package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class SweetTypeComparator extends SweetComparator {

    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? sweet1.getSweetType().compareTo(sweet2.getSweetType())
                : sweet2.getSweetType().compareTo(sweet1.getSweetType());
    }

    @Override
    public String getNameCriteria() {
        return "Sweet Type";
    }
}