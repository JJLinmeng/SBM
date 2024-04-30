package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class BankLoginJFrame extends ConfigJFrame {

    private JLabel IdLabel;
    @Getter
    private JTextField IdTextField;

    private JLabel PwdLabel;
    @Getter
    private JPasswordField PasswordTextField;
    private GridBagPanel infoPanel;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton closeButton;
    private JButton registerButton;
    @Getter
    private JFrame parentComponent;
    public BankLoginJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.parentComponent = parentComponent;
    }


    @Override
    public void init() {
        this.add(this.infoPanel = new GridBagPanel(), BorderLayout.CENTER);
        this.add(this.buttonPanel = new JPanel(new FlowLayout()), BorderLayout.SOUTH);
        this.infoPanel.add(0, 0, 0.5, this.IdLabel = new JLabel("身份证号"));
        this.infoPanel.add(1, 0, 1, this.IdTextField = new JTextField(20));
        this.infoPanel.add(0, 1, 0.5, this.PwdLabel = new JLabel("密码"));
        this.infoPanel.add(1, 1, 1, this.PasswordTextField = new JPasswordField(20));
        this.buttonPanel.add(this.loginButton = new JButton("登录"));
        this.buttonPanel.add(this.closeButton = new JButton("退出"));
        this.buttonPanel.add(this.registerButton=new JButton("注册"));
        this.closeButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
        this.loginButton.addActionListener(e -> MouseClickFunction.checkIsLogin(this, bankService));
    }
}
