package com.mumu.studentbankmanagement.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanCalculator {

        public static BigDecimal calculateEqualPrincipal(BigDecimal amount, BigDecimal annualRate, int totalMonths, int currentMonth) {
            BigDecimal monthlyRate = annualRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            BigDecimal monthlyPrincipal = amount.divide(new BigDecimal(totalMonths), 10, RoundingMode.HALF_UP);
            BigDecimal remainingPrincipal = amount.subtract(monthlyPrincipal.multiply(new BigDecimal(currentMonth - 1)));
            BigDecimal interest = remainingPrincipal.multiply(monthlyRate);
            return monthlyPrincipal.add(interest).setScale(2, RoundingMode.HALF_UP);
        }


        public static BigDecimal calculateEqualPrincipalAndInterest(BigDecimal principal, BigDecimal annualRate, int totalMonths) {
            BigDecimal monthlyRate = annualRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            BigDecimal numerator = principal.multiply(monthlyRate).multiply((monthlyRate.add(BigDecimal.ONE)).pow(totalMonths));
            BigDecimal denominator = (monthlyRate.add(BigDecimal.ONE)).pow(totalMonths).subtract(BigDecimal.ONE);
            return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        }
    }

