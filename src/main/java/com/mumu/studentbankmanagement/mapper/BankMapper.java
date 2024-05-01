package com.mumu.studentbankmanagement.mapper;

import com.mumu.studentbankmanagement.model.CardOwner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BankMapper {
    CardOwner login(String id, String password);

    int registerCardOwner(CardOwner cardOwner);
}
