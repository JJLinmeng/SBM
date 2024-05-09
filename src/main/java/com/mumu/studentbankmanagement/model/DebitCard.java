package com.mumu.studentbankmanagement.model;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    protected String cardNumber;
    protected String ownerId;
    protected BigDecimal balance;
    protected String password;
    private BigDecimal limit;
}
