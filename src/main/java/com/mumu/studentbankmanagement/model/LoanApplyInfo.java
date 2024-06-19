package com.mumu.studentbankmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanApplyInfo {
    private int id;
    private String cardOwnerId;
    private String cardOwnerName;
    private String loanAmount;
    private String loanMonth;
    private String loanType;
    private String repayCardNumber;
    private String loanCardNumber;
    private String status;
}
