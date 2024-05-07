package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.service.BankService;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class TransferJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JPanel buttonPanel;
    private JLabel payeeCardNumberLabel;
    @Getter
    private JTextField payeeCardNumberTextField;
    private JLabel payerCardNumberLabel;
    @Getter
    private JTextField payerCardNumberTextField;
    private JLabel amountLabel;
    @Getter
    private JTextField amountTextField;
    private JLabel payerPasswordLabel;
    @Getter
    private JPasswordField payerPasswordField;
    private JButton transferButton;
    private JButton backButton;
    public TransferJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        gridBagPanel.add(0,0,0.2,payeeCardNumberLabel=new JLabel("付款人卡号"));
        gridBagPanel.add(1,0,1,payerCardNumberTextField=new JTextField(16));
        gridBagPanel.add(0,1,0.2,payerCardNumberLabel=new JLabel("收款人卡号"));
        gridBagPanel.add(1,1,1,payeeCardNumberTextField=new JTextField(16));
        gridBagPanel.add(0,2,0.2,amountLabel=new JLabel("转账金额"));
        gridBagPanel.add(1,2,1,amountTextField=new JTextField(16));
        gridBagPanel.add(0,3,0.2,payerPasswordLabel=new JLabel("付款人卡号密码"));
        gridBagPanel.add(1,3,1,payerPasswordField=new JPasswordField(16));
        buttonPanel.add(transferButton=new JButton("转账"));
        buttonPanel.add(backButton=new JButton("返回"));
        transferButton.addActionListener(e-> MouseClickFunction.transfer(this, bankService));
        backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
