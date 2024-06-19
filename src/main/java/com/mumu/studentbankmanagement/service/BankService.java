package com.mumu.studentbankmanagement.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mumu.studentbankmanagement.model.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BankService {
    JSONObject register(Map<String, Object> paramForm);


    ResponseEntity<byte[]> downloadTXT(Map<String, Object> paramForm, String token);

    //以下是swing中用到的
    CardOwner login(String stuId, String stuPwd);
    JSONObject login(LoginParams loginParams);

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

    BankManager loginByBankManager(String id, String password);

    List<CardOwner> getAllCardsOwner();


    List<Card> getCardList(String token);

    JSONObject getUserInfo(String token);

    JSONObject exit(String token);

    PageInfo<BankInfo> getCardDetails(String token, Map<String, Object> paramForm);

    ResponseEntity<byte[]> downloadCSV(Map<String, Object> paramForm, String token);

    JSONObject transfer(Map<String, Object> paramForm, String token);

    JSONObject markDeposit(Map<String, Object> paramForm, String token);

    JSONObject markDraw(Map<String, Object> paramForm, String token);

    JSONObject openAccount(Map<String, Object> paramForm, String token);


    JSONObject getTypeNumber(Map<String, Object> paramForm, String token);

    JSONObject getVerificationCode(Map<String, Object> paramForm,String token);

    JSONObject getBalance(Map<String, Object> paramForm, String token);

    JSONObject uploadAvatar(Map<String, Object> paramForm, String token);

    JSONObject applyCreditCard(Map<String, Object> paramForm, String token);

    PageInfo<CreditCardApplyInfo> getApplyingCardInfo(Map<String, Object> paramForm, String token);

    JSONObject agreeApplyCard(Map<String, Object> paramForm, String token);

    JSONObject refuseApplyCard(Map<String, Object> paramForm, String token);

    JSONObject getCreditCardApplicationProgress(String token);

    JSONObject getLoanAndRepayCard(String token);

    JSONObject applyLoan(Map<String, Object> paramForm, String token);

    JSONObject getLoanApplicationProgress( String token);

    PageInfo<LoanApplyInfo> getApplyLoanInfo(Map<String, Object> paramForm, String token);

    JSONObject agreeApplyLoan(Map<String, Object> paramForm, String token);

    JSONObject refuseApplyLoan(Map<String, Object> paramForm, String token);
}
