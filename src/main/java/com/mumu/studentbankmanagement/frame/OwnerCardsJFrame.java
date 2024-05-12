package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.DebitCard;

import javax.swing.*;
import java.awt.*;

public class OwnerCardsJFrame extends ConfigJFrame{
    private JList<DebitCard> debitCardList;
    private CardOwner cardOwner;
    private DefaultListModel<DebitCard> debitCardListModel;
    private JScrollPane debitCardScrollPane;
    private JPanel buttonPanel;
    private JButton backButton;

    public OwnerCardsJFrame(int closeWay, JFrame parentComponent, CardOwner cardOwner) {
        super(closeWay, parentComponent);
        this.cardOwner=cardOwner;
    }

    @Override
    public void init() {
        this.add(debitCardScrollPane = new JScrollPane(debitCardList = new JList<>(debitCardListModel = new DefaultListModel<>())), BorderLayout.CENTER);
        DebitCard[] cards = bankService.getCards(cardOwner.getId());
        for (DebitCard card : cards) {
            debitCardListModel.addElement(card);
        }
        this.add(buttonPanel = new JPanel(), BorderLayout.SOUTH);
        buttonPanel.add(backButton = new JButton("返回"));
        backButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
    }

}
