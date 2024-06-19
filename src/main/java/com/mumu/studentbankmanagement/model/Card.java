package com.mumu.studentbankmanagement.model;

import com.mumu.studentbankmanagement.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private int id;
    private String ownerId;
    private boolean isFavorite;
    private String image;
    private String bank;
    private String type;
    private String number;
    private BigDecimal balance;
    private String password;
    private  String level;
    private BigDecimal loanAmount;
    private int allMonth;
    private LocalDateTime loanTime;
    private BigDecimal currentLoan;
    private BigDecimal currentMonthToBePaid;
    private LocalDateTime nextPayTime;
    private String repaymentCardNumber;
    private BigDecimal interestRate;
    private String loanType;
    private int currentPeriod;


}