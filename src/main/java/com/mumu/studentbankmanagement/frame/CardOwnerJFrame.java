package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CardOwnerJFrame extends ConfigJFrame {
    private JPanel navbarPanel;
    private JLabel personalInfoLabel;
    private JPanel sidePanel;
    private JLabel navbarLabel;
    private JLabel openAccountLabel;
    private JLabel depositLabel;
    private JLabel withdrawLabel;
    private JLabel transferJLabel;
    private JLabel cancelAccountLabel;
    private JLabel logInfoLabel;
    private JLabel logoutLabel;

    public CardOwnerJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(navbarPanel = new JPanel(), BorderLayout.NORTH);
        this.add(sidePanel = new JPanel(new GridLayout(8, 1)), BorderLayout.WEST);
        navbarPanel.add(navbarLabel = new JLabel("欢迎您:" + Loginer.cardOwner.getName()));
        sidePanel.add(personalInfoLabel = new JLabel("个人信息"));
        sidePanel.add(openAccountLabel = new JLabel("开卡"));
        sidePanel.add(depositLabel = new JLabel("存款"));
        sidePanel.add(withdrawLabel = new JLabel("取款"));
        sidePanel.add(transferJLabel = new JLabel("转账"));
        sidePanel.add(cancelAccountLabel = new JLabel("销户"));
        sidePanel.add(logInfoLabel = new JLabel("日志"));
        sidePanel.add(logoutLabel = new JLabel("退出"));

        depositLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("DepositJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });

        openAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("OpenAccountJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });

        personalInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("CardOwnerInfoJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });


        withdrawLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("WithdrawJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);

            }
        });

        transferJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("TransferJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });

        cancelAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("CancelAccountJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });

        logInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                     MouseClickFunction.openJFrame("LogInfoJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
            }
        });
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("MenuJFrame", JFrame.DISPOSE_ON_CLOSE, CardOwnerJFrame.this);
                MouseClickFunction.closeJFrame(CardOwnerJFrame.this);
            }
        });
    }
}
