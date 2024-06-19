package com.mumu.studentbankmanagement;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.mumu.studentbankmanagement.frame.MenuJFrame;
import com.mumu.studentbankmanagement.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class StartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JFrameFactory.create("MenuJFrame",JFrame.EXIT_ON_CLOSE,null);
//        int a=0;
//        a=a++;
//        System.out.println(a);
    }

}
