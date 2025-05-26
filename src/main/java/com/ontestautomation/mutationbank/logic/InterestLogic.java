package com.ontestautomation.mutationbank.logic;

public class InterestLogic {

    public static double addInterestToBalance(double initialBalance) {

        if (initialBalance < 1000) {
            return initialBalance * 1.01;
        }

        if (initialBalance < 5000) {
            return initialBalance * 1.02;
        }

        return initialBalance * 1.03;
    }
}
