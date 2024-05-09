package com.mumu.studentbankmanagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Getter
@Setter
public class BankInfo {
    private int id;
    private LocalDateTime time;
    private String type;
    private BigDecimal amount;
    private String ownerId;
    private String cardNumber;
    public BankInfo(LocalDateTime time,String type,BigDecimal amount,String ownerId,String cardNumber){
        this.time=time;
        this.type=type;
        this.amount=amount;
        this.ownerId=ownerId;
        this.cardNumber=cardNumber;
    }
}
