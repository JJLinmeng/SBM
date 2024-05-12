package com.mumu.studentbankmanagement.service;

import com.mumu.studentbankmanagement.model.BankInfo;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {
    CardOwner login(String stuId, String stuPwd);

    int registerCardOwner(CardOwner cardOwner);

    int getCardNumber(String id);

    DebitCard[] getCards(String id);

    String  getCardOwnerByCardNumber(String cardNumber);

    String getCardPassword(String cardNumber);

    void deposit(String cardNumber, String amount);

    boolean isRegister(String id);

    int openAccount(String cardNumber, String id, String password, String type, BigDecimal limit);

    void withdraw(String cardNumber, String amount);

    BigDecimal getCardBalance(String cardNumber);

    void transfer(String payerCardNumber, String payeeCardNumber, String amount);

    void cancelAccount(String cardNumber);

    void addBankInfo(BankInfo bankInfo);

    List<BankInfo> getBankInfosByOwnerId(String id);

    String getCardTypeByCardNumber(String cardNumber);

    BigDecimal getCardLimitByCardNumber(String cardNumber);

    int getAllCardsCount();

    int loginByCardOwner(CardOwner cardOwner);

    String getEmail(String cardNumber);
}
