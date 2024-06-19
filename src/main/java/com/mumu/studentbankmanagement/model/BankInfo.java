package com.mumu.studentbankmanagement.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private BigDecimal balance;
    public BankInfo(LocalDateTime time,String type,BigDecimal amount,String ownerId,String cardNumber){
        this.time=time;
        this.type=type;
        this.amount=amount;
        this.ownerId=ownerId;
        this.cardNumber=cardNumber;

    }
    public BankInfo(LocalDateTime time,String type,BigDecimal amount,String ownerId,String cardNumber,BigDecimal balance){
        this.time=time;
        this.type=type;
        this.amount=amount;
        this.ownerId=ownerId;
        this.cardNumber=cardNumber;
        this.balance=balance;
    }

    public String setDateFormat(LocalDateTime time){

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");


        String formattedTime = time.format(dateTimeFormatter);

       return formattedTime;
    }


    public String toString(){
        if(type.equals("转账")||type.equals("取款")||type.equals("存款")||type.equals("收账")){
            return setDateFormat(time)+" 用户"+ownerId+",卡号"+cardNumber+type+amount+"元";
        }
        if(type.equals("开户")||type.equals("销户")){
            return setDateFormat(time)+" 用户"+ownerId+",卡号"+cardNumber+type;
        }
        if(type.equals("注册")){
            return setDateFormat(time)+" 用户"+ownerId+type;
        }
        else return setDateFormat(time)+type+" 未标记";
    }
}
