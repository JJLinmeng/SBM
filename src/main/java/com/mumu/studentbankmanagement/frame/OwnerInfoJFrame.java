package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.CardOwner;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OwnerInfoJFrame extends ConfigJFrame{
    private JList<CardOwner> ownerList;
    private JScrollPane scrollPane;
    private DefaultListModel <CardOwner> ownerListModel;
    private JPanel buttonPanel;
    private JButton selectButton;
    private JButton backButton;
    public OwnerInfoJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        ownerListModel=new DefaultListModel<>();
        ownerList=new JList<>(ownerListModel);
        scrollPane=new JScrollPane(ownerList);
        this.add(scrollPane,BorderLayout.CENTER);
        List<CardOwner> cardOwners=bankService.getAllCardsOwner();
        for (CardOwner cardOwner:cardOwners){
            ownerListModel.addElement(cardOwner);
        }
        this.add(buttonPanel=new JPanel(),BorderLayout.SOUTH);
        buttonPanel.add(selectButton=new JButton("详细信息"));
        buttonPanel.add(backButton=new JButton("返回"));

        selectButton.addActionListener(e-> MouseClickFunction.openJFrame("OwnerCardsJFrame",DISPOSE_ON_CLOSE,this,ownerList.getSelectedValue()));
        backButton.addActionListener(e-> MouseClickFunction.openJFrame("OwnerJFrame",DISPOSE_ON_CLOSE,this));
    }
}
