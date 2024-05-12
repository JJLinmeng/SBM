package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.component.MenuLabel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuJFrame extends ConfigJFrame {
    // 定义学生系统按钮和银行系统按钮
    MenuLabel studentLabel;
    MenuLabel bankLabel;
    public MenuJFrame(int closeWay)  {
        // 调用父类的构造函数
        super(closeWay,null);
    }

    @Override
    public void init() {
        Loginer.cardOwner=null;
        Loginer.user=null;
        Loginer.bankManager=null;
        // 使用FlowLayout布局
        this.setLayout(new FlowLayout());
        // 初始化学生系统按钮和银行系统按钮
        try {
            this.add(this.studentLabel = new MenuLabel("学生系统", "static/images/student.png",this));
            this.add(this.bankLabel = new MenuLabel("银行系统", "static/images/bank.png",this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}