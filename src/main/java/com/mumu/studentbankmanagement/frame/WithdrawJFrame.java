package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class WithdrawJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JPanel buttonPanel;
    private JLabel cardNumberLabel;
    @Getter
    private JTextField cardNumberTextField;
    private JLabel moneyLabel;
    @Getter
    private JTextField moneyTextField;
    private JLabel passwordLabel;
    @Getter
    private JPasswordField passwordField;
    private JButton withdrawButton;
    private JButton backButton;
    public WithdrawJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        gridBagPanel.add(0,0,0.2,cardNumberLabel=new JLabel("卡号:"));
        gridBagPanel.add(1,0,1,cardNumberTextField=new JTextField(16));
        gridBagPanel.add(0,1,0.2,moneyLabel=new JLabel("金额:"));
        gridBagPanel.add(1,1,1,moneyTextField=new JTextField(16));
        gridBagPanel.add(0,2,0.2,passwordLabel=new JLabel("密码:"));
        gridBagPanel.add(1,2,1,passwordField=new JPasswordField(16));
        buttonPanel.add(withdrawButton=new JButton("取款"));
        buttonPanel.add(backButton=new JButton("返回"));
        withdrawButton.addActionListener(e-> MouseClickFunction.withdraw(this,bankService));
        backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
