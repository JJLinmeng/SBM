package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.BankInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LogInfoJFrame extends ConfigJFrame{

    private JScrollPane jscrollPane;
    private JList<BankInfo> jlist;
    private JPanel buttonPanel;
    private JButton backButton;
    public LogInfoJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        List<BankInfo> bankInfos=bankService.getBankInfosByOwnerId(Loginer.cardOwner.getId());
        DefaultListModel<BankInfo> bankInfoDefaultListModel = new DefaultListModel<>();
        bankInfoDefaultListModel.addAll(bankInfos);
        this.add(jscrollPane=new JScrollPane(new JList<BankInfo>(bankInfoDefaultListModel)),BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        buttonPanel.add(backButton=new JButton("返回"));
        backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
