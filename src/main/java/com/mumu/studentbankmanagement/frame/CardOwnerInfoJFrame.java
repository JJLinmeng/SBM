package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardOwnerInfoJFrame extends ConfigJFrame{
   private JPanel cardOwnerInfoPanel;
    private JLabel cardNumberLabel;
    private JLabel nameLabel;
    public CardOwnerInfoJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
      this.add(cardOwnerInfoPanel=new JPanel(new GridLayout(2,1)));
      cardOwnerInfoPanel.add(nameLabel=new JLabel("姓名："+ "*" +Loginer.cardOwner.getName().substring(1)));
      cardOwnerInfoPanel.add(cardNumberLabel=new JLabel("银行卡:"+bankService.getCardNumber(Loginer.cardOwner.getId())));
      cardNumberLabel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent evt) {
              MouseClickFunction.openJFrame("CardsJFrame",JFrame.DISPOSE_ON_CLOSE,CardOwnerInfoJFrame.this);
          }
      });
    }

    public int getNum() {
        return Integer.parseInt( this.cardNumberLabel.getText().split(":")[1]);
    }
}
