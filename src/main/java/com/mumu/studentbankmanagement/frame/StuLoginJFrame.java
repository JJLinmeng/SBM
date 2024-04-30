package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Getter
public class StuLoginJFrame extends ConfigJFrame {
    private JLabel stuIdLabel;
    private JTextField stuIdTextField;

    private JLabel stuPwdLabel;
    private JPasswordField stuPasswordTextField;
    private GridBagPanel infoPanel;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton closeButton;
    private JFrame parentComponent;

    public StuLoginJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.parentComponent = parentComponent;
    }

    @Override
    public void init() {
        this.add(this.infoPanel = new GridBagPanel(), BorderLayout.CENTER);
        this.add(this.buttonPanel = new JPanel(new FlowLayout()), BorderLayout.SOUTH);

        this.infoPanel.add(0, 0, 0.5, this.stuIdLabel = new JLabel("学号/工号"));
        this.infoPanel.add(1, 0, 1, this.stuIdTextField = new JTextField(20));
        this.infoPanel.add(0, 1, 0.5, this.stuPwdLabel = new JLabel("密码"));
        this.infoPanel.add(1, 1, 1, this.stuPasswordTextField = new JPasswordField(20));
        this.buttonPanel.add(this.loginButton = new JButton("登录"));
        this.buttonPanel.add(this.closeButton = new JButton("退出"));
        this.closeButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
        this.loginButton.addActionListener(e -> MouseClickFunction.checkIsLogin(this, stuService));
    }

}
