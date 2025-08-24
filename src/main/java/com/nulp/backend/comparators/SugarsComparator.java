package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

public class SugarsComparator extends SweetComparator {
    @Override
    public int compare(Sweet sweet1, Sweet sweet2) {
        return ascending ? Double.compare(sweet1.getSugars(), sweet2.getSugars())
                : Double.compare(sweet2.getSugars(), sweet1.getSugars()) ;
    }

    @Override
    public String getNameCriteria() {
        return "Sugars";
    }
}
