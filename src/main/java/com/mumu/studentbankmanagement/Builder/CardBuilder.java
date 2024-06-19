package com.mumu.studentbankmanagement.Builder;



import com.mumu.studentbankmanagement.model.Card;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardBuilder {
    private int id;
    private String ownerId;
    private boolean isFavorite;
    private String image;
    private String bank;
    private String type;
    private String number;
    private BigDecimal balance;
    private String password;
    private String level;
    private BigDecimal loanAmount;
    private int allMonth;
    private LocalDateTime loanTime;
    private BigDecimal currentLoan;
    private BigDecimal currentMonthToBePaid;
    private LocalDateTime nextPayTime;
    private String repaymentCardNumber;
    private BigDecimal interestRate;
    private String loanType;
    private int currentPeriod;
    public CardBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CardBuilder setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public CardBuilder setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        return this;
    }

    public CardBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public CardBuilder setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public CardBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public CardBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public CardBuilder setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public CardBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CardBuilder setLevel(String level) {
        this.level = level;
        return this;
    }


    public CardBuilder setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
        return this;
    }


    public CardBuilder setAllMonth(int allMonth) {
        this.allMonth = allMonth;

        return this;
    }


    public CardBuilder setLoanTime(LocalDateTime loanTime) {

        this.loanTime = loanTime;

        return this;
    }


    public CardBuilder setCurrentLoan(BigDecimal currentLoan) {

        this.currentLoan = currentLoan;

        return this;
    }


    public CardBuilder setCurrentMonthToBePaid(BigDecimal currentMonthToBePaid) {

        this.currentMonthToBePaid = currentMonthToBePaid;

        return this;

    }


    public CardBuilder setNextPayTime(LocalDateTime nextPayTime) {

        this.nextPayTime = nextPayTime;

        return this;
    }


    public CardBuilder setRepaymentCardNumber(String repaymentCardNumber) {

        this.repaymentCardNumber = repaymentCardNumber;

        return this;

    }


    public CardBuilder setInterestRate(BigDecimal interestRate) {

        this.interestRate = interestRate;

        return this;
    }


    public CardBuilder setLoanType(String loanType) {

        this.loanType = loanType;

        return this;
    }

    public CardBuilder setCurrentPeriod(int currentPeriod) {

        this.currentPeriod = currentPeriod;

        return this;
    }

    public Card build() {
      return new Card(id, ownerId, isFavorite, image, bank, type, number, balance, password, level, loanAmount, allMonth, loanTime, currentLoan, currentMonthToBePaid, nextPayTime, repaymentCardNumber, interestRate, loanType,currentPeriod);
    }
}