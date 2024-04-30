package com.mumu.studentbankmanagement.model;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class CardOwner {
    private String id;
    private String name;
    private String password;
}
