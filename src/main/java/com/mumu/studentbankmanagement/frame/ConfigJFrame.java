package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.service.StuService;

import javax.swing.*;

public abstract class ConfigJFrame extends JFrame {
    // 设置窗口布局为null
    protected static StuService stuService;
    public JFrame parentComponent;


    public static void set(StuService stuService){
        ConfigJFrame.stuService=stuService;
    }

    // 创建配置窗口类
    public ConfigJFrame(int closeWay,JFrame parentComponent)  {
        // 设置窗口关闭时的默认操作
        this.setDefaultCloseOperation(closeWay);
        this.setVisible(true);
        this.parentComponent=parentComponent;
    }

    public  void handle() {
        this.init();
        this.pack();
        this.setLocationRelativeTo(null);
    }
    public abstract void init() ;

}
