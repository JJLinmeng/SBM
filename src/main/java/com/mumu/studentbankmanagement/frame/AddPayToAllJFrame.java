package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class AddPayToAllJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JLabel moneyLabel;
    @Getter
    private JTextField moneyTextField;
    private JButton addButton;
    private JButton backButton;
    private JPanel buttonPanel;
    public AddPayToAllJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel = new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel = new JPanel(),BorderLayout.SOUTH);
        gridBagPanel.add(0, 0, 0.2, moneyLabel = new JLabel("金额"));
        gridBagPanel.add(1, 0, 0.8, moneyTextField = new JTextField(10));
        buttonPanel.add(addButton = new JButton("设置"));
        buttonPanel.add(backButton = new JButton("返回"));

        addButton.addActionListener(e -> MouseClickFunction.setPayToAll(this, stuService));
        backButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
    }
}
