package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;

import javax.swing.*;
import java.awt.*;

public class BankRegisterJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel passwordConfirmLabel;
    private JPasswordField passwordConfirmField;
    private JPanel buttonPanel;
    private JButton registerButton;
    private JButton cancelButton;

    public BankRegisterJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(),BorderLayout.CENTER);
        gridBagPanel.add(0,0,0.2,idLabel=new JLabel("身份证号："));
        gridBagPanel.add(1,0,1,idTextField=new JTextField(18));
        gridBagPanel.add(0,1,0.2,nameLabel=new JLabel("姓名："));
        gridBagPanel.add(1,1,1,nameTextField=new JTextField(18));
        gridBagPanel.add(0,2,0.2,passwordLabel=new JLabel("密码："));
        gridBagPanel.add(1,2,1,passwordField=new JPasswordField(18));
        gridBagPanel.add(0,3,0.2,passwordConfirmLabel=new JLabel("确认密码："));
        gridBagPanel.add(1,3,1,passwordConfirmField=new JPasswordField(18));
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        buttonPanel.add(registerButton=new JButton("注册"));
        buttonPanel.add(cancelButton=new JButton("取消"));
    }
}
