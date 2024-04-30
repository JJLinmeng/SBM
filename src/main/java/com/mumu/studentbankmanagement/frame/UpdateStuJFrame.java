package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.component.ProvinceCitySelector;
import com.mumu.studentbankmanagement.model.Stu;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;

public class UpdateStuJFrame extends ConfigJFrame{
    private GridBagPanel gridBagPanel;
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
        this.add(gridBagPanel=new GridBagPanel());
        gridBagPanel.add(0,0,0.2,idLabel=new JLabel("学号:"));
        gridBagPanel.add(1,0,1,idTextField=new JTextField(stu.getId()));
        gridBagPanel.add(0,1,0.2,nameLabel=new JLabel("姓名:"));
        gridBagPanel.add(1,1,1,nameTextField=new JTextField(stu.getName()));
    }

}
