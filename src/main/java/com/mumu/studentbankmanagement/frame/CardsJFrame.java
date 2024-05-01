package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.model.DebitCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardsJFrame extends ConfigJFrame {
    private JPanel cardsPanel;
    private int num;
    private CardOwnerInfoJFrame cardOwnerInfoJFrame;

    public CardsJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.cardOwnerInfoJFrame=(CardOwnerInfoJFrame) parentComponent;
        this.num=cardOwnerInfoJFrame.getNum();
    }

    @Override
    public void init() {
        this.add(cardsPanel=new JPanel(new GridLayout(num,1)));
        for(DebitCard card:bankService.getCards(Loginer.cardOwner.getId())){

            cardsPanel.add(new JLabel("卡号: ****"+card.getCardNumber().substring(4)+"  余额: "+card.getBalance()));
        }

    }
}
