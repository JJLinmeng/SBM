package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class CancelAccountJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
    private JPanel buttonPanel;
    private JLabel cardNumberLabel;
    @Getter
    private JTextField cardNumberTextField;
    private JLabel ownerIdLabel;
    @Getter
    private JTextField ownerIdTextField;
    private JLabel passwordLabel;
    @Getter
    private JPasswordField passwordTextField;
    private JButton cancelAccountButton;
    private JButton backButton;
    public CancelAccountJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        gridBagPanel.add(0,0,0.2,cardNumberLabel=new JLabel("卡号"));
        gridBagPanel.add(1,0,1,cardNumberTextField=new JTextField(18));
        gridBagPanel.add(0,1,0.2,ownerIdLabel=new JLabel("身份证号"));
        gridBagPanel.add(1,1,1,ownerIdTextField=new JTextField(18));
        gridBagPanel.add(0,2,0.2,passwordLabel=new JLabel("密码"));
        gridBagPanel.add(1,2,1,passwordTextField=new JPasswordField(18));

        buttonPanel.add(cancelAccountButton=new JButton("注销"));
        buttonPanel.add(backButton=new JButton("返回"));

        cancelAccountButton.addActionListener(e -> MouseClickFunction.cancelAccount(this,bankService));

        backButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
    }
}
