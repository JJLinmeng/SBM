package com.mumu.studentbankmanagement.service.impl;

import com.mumu.studentbankmanagement.frame.ConfigJFrame;
import com.mumu.studentbankmanagement.mapper.BankMapper;
import com.mumu.studentbankmanagement.mapper.StuMapper;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;
import com.mumu.studentbankmanagement.service.BankService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class bankServiceImpl implements BankService {
    @Autowired
    private BankMapper bankMapper;
    @PostConstruct
    public void init(){ConfigJFrame.set(this);}

    @Override
    public CardOwner login(String Id, String Pwd) {
        return bankMapper.login(Id, Pwd);
    }

    @Override
    public int registerCardOwner(CardOwner cardOwner) {
        return bankMapper.registerCardOwner(cardOwner);
    }

    @Override
    public int getCardNumber(String id) {
        return bankMapper.getCardNumber(id);
    }

    @Override
    public DebitCard[] getCards(String id) {
        return bankMapper.getCards(id);
    }

    @Override
    public String getCardOwnerByCardNumber(String cardNumber) {
        return bankMapper.getCardOwnerByCardNumber(cardNumber);
    }

    @Override
    public String getCardPassword(String cardNumber) {
        return bankMapper.getCardPassword(cardNumber);
    }

    @Override
    public void deposit(String cardNumber, String amount) {
        bankMapper.deposit(cardNumber, amount);
    }

    @Override
    public boolean isRegister(String id) {
        return bankMapper.isRegister(id);
    }

    @Override
    public int openAccount(String cardNumber, String id, String password) {
        return bankMapper.openAccount(cardNumber, id, password);
    }
}
