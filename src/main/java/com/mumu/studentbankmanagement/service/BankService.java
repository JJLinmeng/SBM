package com.mumu.studentbankmanagement.service;

import com.mumu.studentbankmanagement.model.CardOwner;

public interface BankService {
    CardOwner login(String stuId, String stuPwd);
}
