package com.mumu.studentbankmanagement.mapper;

import com.mumu.studentbankmanagement.model.BankInfo;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface BankMapper {
    CardOwner login(String id, String password);

    int registerCardOwner(CardOwner cardOwner);

    int getCardNumber(String id);

    DebitCard[] getCards(String id);

    String getCardOwnerByCardNumber(String cardNumber);

    String getCardPassword(String cardNumber);

    void deposit(String cardNumber, String amount);

    boolean isRegister(String id);

    int openAccount(String cardNumber, String id, String password);

    void withdraw(String cardNumber, String amount);

    BigDecimal getCardBalance(String cardNumber);

    void cancelAccount(String cardNumber);

    void addBankInfo(BankInfo bankInfo);

    List<BankInfo> getBankInfosByOwnerId(String id);
}
