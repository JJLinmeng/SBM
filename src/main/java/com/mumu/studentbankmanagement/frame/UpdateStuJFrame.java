package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Util.DateUtil;
import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.component.ProvinceCitySelector;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.Stu;
import lombok.Getter;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class UpdateStuJFrame extends ConfigJFrame{
    @Getter
    private StuInfoListJFrame parentComponent;
    private GridBagPanel gridBagPanel;
    private JPanel buttonPanel;
    private Stu stu;
    private JLabel idLabel;
    @Getter
    private JTextField idTextField;
    private JLabel nameLabel;
    @Getter
    private JTextField nameTextField;
    private JLabel passwordLabel;
    @Getter
    private JPasswordField passwordTextField;
    private JLabel birthdayLabel;
    @Getter
    private JXDatePicker birthdayDatePicker;
    private JLabel specialityLabel;
    @Getter
    private JTextField specialityTextField;
    private JLabel entryYearLabel;
    @Getter
    private JTextField entryYearTextField;
    private JLabel provinceCityLabel;
    @Getter
    private ProvinceCitySelector provinceCitySelector;
    private JButton updateButton;
    private JButton cancelButton;


    public UpdateStuJFrame(int closeWay, JFrame parentComponent, Stu stu) {
        super(closeWay, parentComponent);
        this.parentComponent=(StuInfoListJFrame)parentComponent;
        this.stu=stu;
    }


    @Override
    public void init() {
        if(stu==null){
            this.setVisible(false);
            JOptionPane.showMessageDialog(this,"未选择学生");
            return;
        }
        this.add(gridBagPanel=new GridBagPanel(), BorderLayout.CENTER);
        this.add(buttonPanel=new JPanel(), BorderLayout.SOUTH);
        gridBagPanel.add(0,0,0.2,idLabel=new JLabel("学号:"));
        gridBagPanel.add(1,0,1,idTextField=new JTextField(stu.getId()));
        idTextField.setEditable(false);
        gridBagPanel.add(0,1,0.2,nameLabel=new JLabel("姓名:"));
        gridBagPanel.add(1,1,1,nameTextField=new JTextField(stu.getName()));
        gridBagPanel.add(0,2,0.2,passwordLabel=new JLabel("密码:"));
        gridBagPanel.add(1,2,1,passwordTextField=new JPasswordField(stu.getPassword()));
        gridBagPanel.add(0,3,0.2,birthdayLabel=new JLabel("出生日期:"));
        gridBagPanel.add(1,3,1,birthdayDatePicker=new JXDatePicker(DateUtil.LocalDateToDate(stu.getBirthday())));
        gridBagPanel.add(0,4,0.2,specialityLabel=new JLabel("专业:"));
        gridBagPanel.add(1,4,1,specialityTextField=new JTextField(stu.getSpeciality()));
        gridBagPanel.add(0,5,0.2,entryYearLabel=new JLabel("入学年份:"));
        gridBagPanel.add(1,5,1,entryYearTextField=new JTextField(stu.getEntryYear()+""));
        gridBagPanel.add(0,6,0.2,provinceCityLabel=new JLabel("籍贯:"));
        gridBagPanel.add(1,6,1,provinceCitySelector=new ProvinceCitySelector(stu.getProvince(),stu.getCity()));
        buttonPanel.add(updateButton=new JButton("修改"));
        buttonPanel.add(cancelButton=new JButton("取消"));
        updateButton.addActionListener(e-> MouseClickFunction.updateStu(this,stuService,stu));
        cancelButton.addActionListener(e->MouseClickFunction.closeJFrame(this));
    }

}
