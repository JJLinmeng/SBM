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
   private String cardNumber;
   private String ownerId;
   private BigDecimal balance;
   private String password;
   private BigDecimal limit;
   public String toString(){
       return cardNumber+" "+ownerId+" "+balance+(limit.compareTo(new BigDecimal("0"))==0?"借记卡":"信用卡");
   }

}
