package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.CardOwner;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class BankRegisterJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JLabel idLabel;
    @Getter
    private JTextField idTextField;
    private JLabel nameLabel;
    @Getter
    private JTextField nameTextField;
    private JLabel passwordLabel;
    @Getter
    private JPasswordField passwordField;
    private JLabel passwordConfirmLabel;
    @Getter
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
        registerButton.addActionListener(e-> MouseClickFunction.registerCardOwner(this,bankService));
        cancelButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
