package com.mumu.studentbankmanagement.model;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    private String cardNumber;
    private String ownerId;
    private BigDecimal balance;
    private String password;
}
