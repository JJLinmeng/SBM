package com.mumu.studentbankmanagement.mapper;

import com.mumu.studentbankmanagement.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface BankMapper {
    CardOwner login(String id, String password);
    int  loginByCardOwner(CardOwner cardOwner);

    int registerCardOwner(CardOwner cardOwner);

    int getCardNumber(String id);

    DebitCard[] getCards(String id);

    String getCardOwnerByCardNumber(String cardNumber);

    String getCardPassword(String cardNumber);

    void deposit(String cardNumber, String amount);

    boolean isRegister(String id);

    int openAccount(String cardNumber, String id, String password,String type,BigDecimal limit);

    void withdraw(String cardNumber, String amount);

    BigDecimal getCardBalance(String cardNumber);

    void cancelAccount(String cardNumber);

    void addBankInfo(BankInfo bankInfo);

    List<BankInfo> getBankInfosByOwnerId(String id);

    String getCardTypeByCardNumber(String cardNumber);

    BigDecimal getCardLimitByCardNumber(String cardNumber);

    int  getAllCardsCount();

    String getEmail(String id);

    BankManager loginByBankManager(String id, String password);

    List<CardOwner> getAllCardsOwner();

    void register(String id, String name, String password, String email,int role);

    int exist(String id);

    List<Card> getCardList(String id);

    CardOwner getUserInfo(String id);

    int updateLastLoginTime(String id, LocalDateTime time);

    @Mapper
    List<BankInfo> getCardDetails(String start, String end, String registeredAccount,String category);

    String getCardName(String cardNumber);

    String getCardPasswordByCardNumber(String payerCardNumber);


    void withdrawCard(String cardNumber, BigDecimal amount);

    void depositCard(String cardNumber, BigDecimal amount);

    String getEmailByCardNumber(String cardNumber);

    String getCardOwnerIdByCardNumber(String cardNumber);

    BigDecimal getCardBalanceByCardNumber(String cardNumber);

    void markDeposit(String cardNumber, BigDecimal amount);

    void markDraw(String cardNumber, BigDecimal amount);


    boolean isCardOwnerExist(String id, String name);

    String getCardOwnerPasswordByCardOwnerId(String id);

     String getAllCardsCountFromCard();

    void addCard(Card card);

    int getDetailCardLevelNumber(String id, String level);

    void insertVerificationCode(String email, String code);

    boolean isEmailCodeExistEmail(String email);

    void updateVerificationCode(String email, String code);

    String getVerificationCode(String email);

    boolean isCardNumberExist(String cardNumber);


    void uploadAvatar(String image, String id);

    void applyCreditCard(String id, String name, String password,LocalDateTime time,String status);

    int getCreditCardCount(String id);

    int getCreditCardApplyCount(String id);

    CardOwner loginAdmin(String id, String password);

    List<CreditCardApplyInfo> getApplyingCardInfo();

    void modifyApplyCreditInfo(String id,String status);

    String getCreditCardApplicationProgress(String id);

    void deleteCreditCardApplyInfo(String id);

    String getCreditCardNumber(String cardOwnerId);

    String getRepayCardNumber(String cardOwnerId);

    void applyLoan(String id, String name, BigDecimal loanAmount, int loanMonth, String loanType, String repayCardNumber, String loanCardNumber,String status);

    int getLoanApplyCount(String id);

    String getLoanApplicationProgress(String id);

    List<LoanApplyInfo> getApplyingLoanInfo();

    void agreeApplyLoan(String loanCardNumber,BigDecimal loanAmount, int loanMonth, LocalDateTime loanTime, BigDecimal currentLoan, BigDecimal currentToBePaidMoney, LocalDateTime nextPayTime, String repayCardNumber, BigDecimal interestRate,String loanType);

    void modifyApplyLoanInfo(String loanCardNumber, String status);

    void deleteApplyLoanInfo(String id);

    List<Card> getNearPaidTimeCard(LocalDateTime currentTime,int days);

    void modifyIsReminded(String number, boolean isReminded);

    List<Card> getOverPaidTimeCard(LocalDateTime now);

    void modifyCreditCardInfo(String number, BigDecimal currentMonthToBePaid, LocalDateTime nextPayTime, int currentPeriod, boolean isReminded);
}
