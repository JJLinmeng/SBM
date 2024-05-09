package com.mumu.studentbankmanagement.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public String toString(){
        if(type.equals("转账")||type.equals("取款")||type.equals("存款")){
            return "用户"+ownerId+",卡号"+cardNumber+type+amount+"元";
        }
        if(type.equals("开户")||type.equals("销户")){
            return "用户"+ownerId+",卡号"+cardNumber+type;
        }
        if(type.equals("注册")){
            return "用户"+ownerId+type;
        }
        return type+"未标记";
    }
}
