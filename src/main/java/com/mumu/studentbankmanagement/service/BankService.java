package com.mumu.studentbankmanagement.service;

import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;

public interface BankService {
    CardOwner login(String stuId, String stuPwd);

    int registerCardOwner(CardOwner cardOwner);

    int getCardNumber(String id);

    DebitCard[] getCards(String id);

    String  getCardOwnerByCardNumber(String cardNumber);

    String getCardPassword(String cardNumber);

    void deposit(String cardNumber, String amount);

    boolean isRegister(String id);

    int openAccount(String cardNumber,String id, String password);
}
