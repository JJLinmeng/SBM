package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;

import javax.swing.*;
import java.awt.*;

public class CardOwnerJFrame extends ConfigJFrame{
    private JPanel navbarPanel;
    private JLabel navbarLabel;
    public CardOwnerJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(navbarPanel=new JPanel(), BorderLayout.NORTH);
        navbarPanel.add(navbarLabel=new JLabel("欢迎您:"+ Loginer.cardOwner.getName()));
    }
}
