package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class OpenAccountJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JPanel buttonPanel;
    private JLabel ownerIdLabel;
    @Getter
    private JTextField ownerIdTextField;
    private JLabel passwordLabel;
    @Getter
    private JPasswordField passwordTextField;
    private JLabel confirmPasswordLabel;
    @Getter
    private JPasswordField confirmPasswordTextField;
    private JButton openAccountButton;
    private JButton backButton;
    public OpenAccountJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        gridBagPanel.add(0,0,0.2,ownerIdLabel=new JLabel("身份证号:"));
        gridBagPanel.add(1,0,1,ownerIdTextField=new JTextField(18));
        gridBagPanel.add(0,1,0.2,passwordLabel=new JLabel("密码:"));
        gridBagPanel.add(1,1,1,passwordTextField=new JPasswordField(18));
        gridBagPanel.add(0,2,0.2,confirmPasswordLabel=new JLabel("确认密码:"));
        gridBagPanel.add(1,2,1,confirmPasswordTextField=new JPasswordField(18));
        buttonPanel.add(openAccountButton=new JButton("开户"));
        buttonPanel.add(backButton=new JButton("返回"));
        openAccountButton.addActionListener(e-> MouseClickFunction.openAccount(this,bankService));
        backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
