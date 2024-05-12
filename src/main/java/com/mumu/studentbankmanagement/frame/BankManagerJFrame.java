package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;

public class BankManagerJFrame extends ConfigJFrame{
   private JPanel buttonPanel;
    private JButton ownerButton;
    private JButton backButton;

    public BankManagerJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(buttonPanel=new JPanel());
        buttonPanel.add(ownerButton=new JButton("查看卡主"));
        buttonPanel.add(backButton=new JButton("返回"));

        ownerButton.addActionListener(e-> MouseClickFunction.openJFrame("OwnerInfoJFrame",DISPOSE_ON_CLOSE,this));
        backButton.addActionListener(e-> {
            MouseClickFunction.openJFrame("MenuJFrame",DISPOSE_ON_CLOSE,this);
            MouseClickFunction.closeJFrame(this);
        });

    }
}
