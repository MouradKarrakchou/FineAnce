package com.example.fineance.model;

import java.util.List;

public class DepenseUtilities {

    public static double getMontantTotal(List<Depense> depenseList) {
        return depenseList.stream().map(d -> d.getMontant() * getEuroConvertion(d)).reduce(0.0, Double::sum);
    }

    public static double getEuroConvertion(Depense depense) {
        return Devise.valueOf(depense.getDevise()).getValue();
    }
}
