package com.mumu.studentbankmanagement;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.mumu.studentbankmanagement.frame.MenuJFrame;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
//@EnableTransactionManagement
public class StudentBankManagementApplication {

    public static void main(String[] args)  {
        try {
            FlatLaf.setup(new FlatDarkFlatIJTheme());
        } catch( Exception ex ) {
            System.err.println("Failed to initialize LaF");
        }
        SpringApplication.run(StudentBankManagementApplication.class, args);
    }
}
