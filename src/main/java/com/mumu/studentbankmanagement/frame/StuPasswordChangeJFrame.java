package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class StuPasswordChangeJFrame extends ConfigJFrame{
    private GridBagPanel infoPanel;
    private JLabel oldPasswordLabel;
    @Getter
    private JPasswordField oldPasswordField;
    private JLabel newPasswordLabel;
    @Getter
    private JPasswordField newPasswordField;
    private JLabel confirmPasswordLabel;
    @Getter
    private JPasswordField confirmPasswordField;
    private JButton changeButton;
    public StuPasswordChangeJFrame(int closeWay, JFrame stuPersonalCenterJFrame) {
        super(closeWay, stuPersonalCenterJFrame);
    }

    @Override
    public void init() {
        this.add(this.infoPanel=new GridBagPanel(),BorderLayout.CENTER);
        this.infoPanel.add(0,0,0.2,this.oldPasswordLabel=new JLabel("旧密码："));
        this.infoPanel.add(1,0,1,this.oldPasswordField=new JPasswordField(20));
        this.infoPanel.add(0,1,0.2,this.newPasswordLabel=new JLabel("新密码："));
        this.infoPanel.add(1,1,1,this.newPasswordField=new JPasswordField(20));
        this.infoPanel.add(0,2,0.2,this.confirmPasswordLabel=new JLabel("确认密码："));
        this.infoPanel.add(1,2,1,this.confirmPasswordField=new JPasswordField(20));
        this.add(this.changeButton=new JButton("修改"), BorderLayout.SOUTH);
        this.changeButton.addActionListener(e-> MouseClickFunction.modifyStuPassword(this,stuService));
    }
}
