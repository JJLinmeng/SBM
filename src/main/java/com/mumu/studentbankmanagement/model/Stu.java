package com.mumu.studentbankmanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Stu {
    public static final int ADMIN = 1;
    public static final int STUDENT = 2;

    private String id;
    private String name;
    private String password;
    private LocalDate birthday;
    private String speciality;
    private String province;
    private String city;
    private int entryYear;
    private String identityNumber;
    private int role;

    @Override
    public String toString() {
        return "学号为"+id+"专业为"+speciality+"入学年份为"+entryYear+"的"+name+"同学";
    }

    public Stu(String name, String password, LocalDate birthday, String speciality, int entryYear, String province, String city, int role,String identityNumber) {
        this.id = "6";
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.speciality = speciality;
        this.entryYear = entryYear;
        this.province = province;
        this.city = city;
        this.role = role;
        this.identityNumber=identityNumber;
    }
}
