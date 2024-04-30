package com.mumu.studentbankmanagement.service.impl;

import com.mumu.studentbankmanagement.frame.ConfigJFrame;
import com.mumu.studentbankmanagement.mapper.BankMapper;
import com.mumu.studentbankmanagement.mapper.StuMapper;
import com.mumu.studentbankmanagement.model.CardOwner;
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
}
