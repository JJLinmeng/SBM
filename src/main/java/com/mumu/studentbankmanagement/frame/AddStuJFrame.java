package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.component.ProvinceCitySelector;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class AddStuJFrame extends ConfigJFrame {
    private JLabel nameLabel;
    protected JTextField nameTextField;
    private JLabel passwordLabel;
    protected JPasswordField passwordField;
    private JLabel birthdayLabel;
    protected JXDatePicker birthdayDatePicker;
    private JLabel specialityLabel;
    protected JTextField specialityTextField;
    private JLabel entryYearLabel;
    protected JTextField entryYearTextField;
    private JLabel provinceCityLabel;
    protected ProvinceCitySelector provinceCitySelector;
    private JLabel identityNumberLabel;
    @Getter
    private JTextField identityNumberTextField;

    private JButton confirmButton;
    private JButton resetButton;

    private JPanel buttonPanel;
    private GridBagPanel gridBagPanel;

    public AddStuJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.parentComponent = parentComponent;
    }


    @Override
    public void init() {
        int col = 13;
        this.add(this.gridBagPanel = new GridBagPanel(), BorderLayout.CENTER);
        this.add(this.buttonPanel = new JPanel(), BorderLayout.SOUTH);
        this.gridBagPanel.add(0, 0, 0.2, this.nameLabel = new JLabel("姓名:"));
        this.gridBagPanel.add(1, 0, 1, this.nameTextField = new JTextField(col));
        this.gridBagPanel.add(0, 1, 0.2, this.passwordLabel = new JLabel("密码:"));
        this.gridBagPanel.add(1, 1, 1, this.passwordField = new JPasswordField(col));
        this.gridBagPanel.add(0, 2, 0.2, this.birthdayLabel = new JLabel("生日:"));
        this.gridBagPanel.add(1, 2, 1, this.birthdayDatePicker = new JXDatePicker());
        this.gridBagPanel.add(0, 3, 0.2, this.specialityLabel = new JLabel("专业:"));
        this.gridBagPanel.add(1, 3, 1, this.specialityTextField = new JTextField(col));
        this.gridBagPanel.add(0, 4, 0.2, this.entryYearLabel = new JLabel("入学年份:"));
        this.gridBagPanel.add(1, 4, 1, this.entryYearTextField = new JTextField(col));
        this.gridBagPanel.add(0, 5, 0.2, this.provinceCityLabel = new JLabel("户籍省市:"));
        this.gridBagPanel.add(1, 5, 1, this.provinceCitySelector = new ProvinceCitySelector());
        this.gridBagPanel.add(0, 6, 0.2, this.identityNumberLabel = new JLabel("身份证号:"));
        this.gridBagPanel.add(1, 6, 1, this.identityNumberTextField = new JTextField(col));
        this.buttonPanel.add(this.confirmButton = new JButton("确认"));
        this.buttonPanel.add(this.resetButton = new JButton("重置"));
        this.resetButton.addActionListener(e -> MouseClickFunction.resetStuInfo(this));
        ActionListener[] actionListeners = this.confirmButton.getActionListeners();
        if (actionListeners != null && actionListeners.length > 0) {
            this.confirmButton.removeActionListener(actionListeners[0]);
        }
        this.confirmButton.addActionListener(e -> MouseClickFunction.addStudent(this, stuService));
    }

}
