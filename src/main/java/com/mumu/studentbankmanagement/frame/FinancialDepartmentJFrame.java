package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;

public class FinancialDepartmentJFrame extends ConfigJFrame{
    private JPanel buttonPanel;
    private JButton transferButton;
    private JButton addPayToOneButton;
    private JButton addPayToAllButton;
    private JButton backButton;
    public FinancialDepartmentJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(buttonPanel=new JPanel());
        buttonPanel.add(transferButton=new JButton("转账"));
        buttonPanel.add(addPayToOneButton=new JButton("给指定学生添加缴费"));
        buttonPanel.add(addPayToAllButton=new JButton("给所有学生添加缴费"));
        buttonPanel.add(backButton=new JButton("返回"));
        addPayToOneButton.addActionListener(e-> MouseClickFunction.openJFrame("AddPayToOneJFrame", JFrame.DISPOSE_ON_CLOSE, this));
        addPayToAllButton.addActionListener(e-> MouseClickFunction.openJFrame("AddPayToAllJFrame", JFrame.DISPOSE_ON_CLOSE, this));

        backButton.addActionListener(e-> {
            MouseClickFunction.closeJFrame(this);
            MouseClickFunction.openJFrame("MenuJFrame", JFrame.DISPOSE_ON_CLOSE, null);
        });
    }
}
