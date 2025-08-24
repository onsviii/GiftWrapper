package com.nulp.backend.comparators;

import com.nulp.backend.models.Sweet;

import java.util.Comparator;

public abstract class SweetComparator implements Comparator<Sweet> {
    boolean ascending;

    public SweetComparator() {
        this.ascending = true;
    }

    public SweetComparator(Boolean ascending) {
        this.ascending = ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public abstract int compare(Sweet sweet1, Sweet sweet2);
    public abstract String getNameCriteria();

}
