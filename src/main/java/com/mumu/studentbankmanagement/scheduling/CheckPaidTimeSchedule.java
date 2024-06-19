package com.mumu.studentbankmanagement.scheduling;

import com.mumu.studentbankmanagement.Mail;
import com.mumu.studentbankmanagement.mapper.BankMapper;
import com.mumu.studentbankmanagement.model.Card;
import com.mumu.studentbankmanagement.model.LoanCalculator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CheckPaidTimeSchedule {
    @Autowired
    private BankMapper bankMapper;
    @PostConstruct
    public void startUp(){
        checkIsNearPaidTimeSchedule();
        checkIsOverPaidTimeSchedule();
    }
    @Scheduled(cron="0/18000 * * * * ?")
    public void checkIsNearPaidTimeSchedule(){
       List<Card> nearPaidTimeCard=bankMapper.getNearPaidTimeCard(LocalDateTime.now(),5);
       nearPaidTimeCard.forEach(card->{
          new Thread(() ->  Mail.sendMail(bankMapper.getEmail(card.getOwnerId()),"信用卡还款提醒","您有一张信用卡即将到期，请及时还款！")).start();
          bankMapper.modifyIsReminded(card.getNumber(),true);
       });

    }

    @Scheduled(cron="0/18000 * * * * ?")
    public void checkIsOverPaidTimeSchedule(){
        List<Card> overPaidTimeCard=bankMapper.getOverPaidTimeCard(LocalDateTime.now());
        overPaidTimeCard.forEach(card->{
            new Thread(() ->  Mail.sendMail(bankMapper.getEmail(card.getOwnerId()),"信用卡还款提醒","已到下个月还款,请注意还款日期")).start();

            String type=card.getLoanType();
            BigDecimal nextMonthToBePaid=new BigDecimal("0");
            if(type.equals("等额本金")){
                nextMonthToBePaid= LoanCalculator.calculateEqualPrincipal(card.getLoanAmount(),card.getInterestRate(),card.getAllMonth(),card.getCurrentPeriod()+1);
            }
            else if (type.equals("等额本息")){
                nextMonthToBePaid=LoanCalculator.calculateEqualPrincipalAndInterest(card.getLoanAmount(),card.getInterestRate(),card.getAllMonth());
            }
            BigDecimal currentMonthToBePaid=card.getCurrentMonthToBePaid().add(nextMonthToBePaid);
            LocalDateTime nextPayTime=card.getNextPayTime().plusMonths(1);
            boolean isReminded=false;
            int currentPeriod=card.getCurrentPeriod()+1;

            System.out.println(card.getNumber());
            System.out.println(currentMonthToBePaid);
            System.out.println(nextPayTime);
            System.out.println(currentPeriod);
            bankMapper.modifyCreditCardInfo(card.getNumber(),currentMonthToBePaid,nextPayTime,currentPeriod,isReminded);
        });
    }


}
