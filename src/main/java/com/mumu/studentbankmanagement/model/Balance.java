package com.mumu.studentbankmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private String currency;
    private String amount;

    // getters, setters, and constructors
}