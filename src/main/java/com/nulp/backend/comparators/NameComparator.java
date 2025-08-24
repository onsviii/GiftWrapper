package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class NameComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
            return ascending ? sweet1.getName().compareTo(sweet2.getName())
                    : sweet2.getName().compareTo(sweet1.getName());
    }

    @Override
    public String getNameCriteria() {
        return "Name";
    }
}
