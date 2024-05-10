package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class AddPayToOneJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JLabel idLabel;
    @Getter
    private JTextField idTextField;
    private JLabel moneyLabel;
    @Getter
    private JTextField moneyTextField;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton backButton;

    public AddPayToOneJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        this.gridBagPanel.add(0,0,0.2,idLabel=new JLabel("学号"));
        this.gridBagPanel.add(1,0,1,idTextField=new JTextField(10));
        this.gridBagPanel.add(0,2,0.2,moneyLabel=new JLabel("金额"));
        this.gridBagPanel.add(1,2,1,moneyTextField=new JTextField(10));
        this.buttonPanel.add(this.addButton=new JButton("设置"));
        this.buttonPanel.add(this.backButton=new JButton("返回"));
        this.addButton.addActionListener(e -> MouseClickFunction.setPayToOne(this,stuService));

    }
}
