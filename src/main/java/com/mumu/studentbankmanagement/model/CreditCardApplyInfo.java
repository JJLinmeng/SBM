package com.mumu.studentbankmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardApplyInfo {
    private int id;
    private String cardOwnerId;
    private String cardPassword;
    private String cardOwnerName;
    private String applyTime;
}
