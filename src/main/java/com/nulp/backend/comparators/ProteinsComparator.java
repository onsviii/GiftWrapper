package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class ProteinsComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getProteins(), sweet2.getProteins())
                : Double.compare(sweet2.getProteins(), sweet1.getProteins());
    }

    @Override
    public String getNameCriteria() {
        return "Proteins";
    }
}
