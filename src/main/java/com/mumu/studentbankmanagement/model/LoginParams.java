package com.mumu.studentbankmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginParams {
    private String id;
    private String password;
    private String longitude;
    private String latitude;
}
