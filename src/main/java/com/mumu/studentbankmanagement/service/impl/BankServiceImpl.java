package com.mumu.studentbankmanagement.service.impl;

import com.mumu.studentbankmanagement.frame.ConfigJFrame;
import com.mumu.studentbankmanagement.mapper.BankMapper;
import com.mumu.studentbankmanagement.model.BankInfo;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;
import com.mumu.studentbankmanagement.service.BankService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {
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
    public int openAccount(String cardNumber, String id, String password, String type, BigDecimal limit) {
        return bankMapper.openAccount(cardNumber, id, password,type,limit);
    }

    @Override
    public void withdraw(String cardNumber, String amount) {
        bankMapper.withdraw(cardNumber,amount);
    }

    @Override
    public BigDecimal getCardBalance(String cardNumber) {
        return bankMapper.getCardBalance(cardNumber);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void transfer(String payerCardNumber, String payeeCardNumber, String amount) {
        bankMapper.deposit(payeeCardNumber,amount);
        bankMapper.withdraw(payerCardNumber,amount);
    }

    @Override
    public void cancelAccount(String cardNumber) {
        bankMapper.cancelAccount(cardNumber);
    }

    @Override
    public void addBankInfo(BankInfo bankInfo) {
        bankMapper.addBankInfo(bankInfo);
    }

    @Override
    public List<BankInfo> getBankInfosByOwnerId(String id) {
        return bankMapper.getBankInfosByOwnerId(id);
    }

    @Override
    public String getCardTypeByCardNumber(String cardNumber) {
        return bankMapper.getCardTypeByCardNumber(cardNumber);
    }

    @Override
    public BigDecimal getCardLimitByCardNumber(String cardNumber) {
        return bankMapper.getCardLimitByCardNumber(cardNumber);
    }

    @Override
    public int getAllCardsCount() {
        return bankMapper.getAllCardsCount();
    }

    @Override
    public int loginByCardOwner(CardOwner cardOwner) {
        return bankMapper.loginByCardOwner(cardOwner);
    }

    @Override
    public String getEmail(String cardNumber) {
        return bankMapper.getEmail(cardNumber);
    }


}
