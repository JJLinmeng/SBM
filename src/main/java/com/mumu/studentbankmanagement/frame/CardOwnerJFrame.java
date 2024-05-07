package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CardOwnerJFrame extends ConfigJFrame{
    private JPanel navbarPanel;
    private JLabel personalInfoLabel;
    private JPanel sidePanel;
    private JLabel navbarLabel;
    private JLabel openAccountLabel;
    private JLabel depositLabel;
    private JLabel withdrawLabel;
    private JLabel transferJLabel;
    public CardOwnerJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(navbarPanel=new JPanel(), BorderLayout.NORTH);
        this.add(sidePanel=new JPanel(new GridLayout(5,1)), BorderLayout.WEST);
        navbarPanel.add(navbarLabel=new JLabel("欢迎您:"+ Loginer.cardOwner.getName()));
        sidePanel.add(personalInfoLabel=new JLabel("个人信息"));
        sidePanel.add(openAccountLabel=new JLabel("开卡"));
        sidePanel.add(depositLabel=new JLabel("存款"));
        sidePanel.add(withdrawLabel=new JLabel("取款"));
        sidePanel.add(transferJLabel=new JLabel("转账"));


        depositLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("DepositJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);
            }
        });

        openAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("OpenAccountJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);
            }
        });

        personalInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("CardOwnerInfoJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);
            }
        });


        withdrawLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("WithdrawJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);

            }
        });

        transferJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("TransferJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);
            }
        });

    }
}
