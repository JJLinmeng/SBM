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
    public CardOwnerJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(navbarPanel=new JPanel(), BorderLayout.NORTH);
        this.add(sidePanel=new JPanel(new GridLayout(5,1)), BorderLayout.WEST);
        navbarPanel.add(navbarLabel=new JLabel("欢迎您:"+ Loginer.cardOwner.getName()));
        sidePanel.add(personalInfoLabel=new JLabel("个人信息"));
        personalInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("CardOwnerInfoJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerJFrame.this);
            }
        });
    }
}
