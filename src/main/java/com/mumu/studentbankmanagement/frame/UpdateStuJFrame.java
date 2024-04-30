package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.ProvinceCitySelector;
import com.mumu.studentbankmanagement.model.Stu;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;

public class UpdateStuJFrame extends ConfigJFrame{
    private Stu stu;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JLabel birthdayLabel;
    private JXDatePicker birthdayDatePicker;
    private JLabel specialtyLabel;
    private JTextField specialtyTextField;
    private JLabel entryYearLabel;
    private JTextField entryYearTextField;
    private JLabel provinceCityLabel;
    private ProvinceCitySelector provinceCitySelector;


    public UpdateStuJFrame(int closeWay, JFrame parentComponent, Stu stu) {
        super(closeWay, parentComponent);
        this.stu=stu;
    }


    @Override
    public void init() {
        if(stu==null){
            this.setVisible(false);
            JOptionPane.showMessageDialog(this,"未选择学生");
            return;
        }

    }

}
