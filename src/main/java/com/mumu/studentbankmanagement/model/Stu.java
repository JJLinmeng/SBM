package com.mumu.studentbankmanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Stu {
    public static final int ADMIN = 1;
    public static final int STUDENT = 2;
    private String id; // 学号
    private String name; // 姓名
    private String password; // 密码
    private LocalDate birthday; // 出生日期
    private String speciality; // 专业
    private String province; // 省
    private String city; // 市
    private int entryYear; // 入学日期
    private int role;

    @Override
    public String toString() {
        return "学号为"+id+"专业为"+speciality+"入学年份为"+entryYear+"的"+name+"同学";
    }

    public Stu(String name, String password, LocalDate birthday, String speciality, int entryYear, String province, String city, int role) {
        this.id = "4";
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.speciality = speciality;
        this.entryYear = entryYear;
        this.province = province;
        this.city = city;
        this.role = role;
    }
}
