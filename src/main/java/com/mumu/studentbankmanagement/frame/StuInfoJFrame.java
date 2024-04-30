package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.Stu;

import javax.swing.*;
import java.awt.*;

public class StuInfoJFrame extends ConfigJFrame{
    private Stu stu;
    private JPanel infoPanel;
    private JPanel buttonPanel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel entryYearLabel;
    private JLabel specialityLabel;
    private JLabel birthdayLabel;
    private JLabel provinceCityLabel;
    private JLabel roleLabel;
    private JButton backButton;

    public StuInfoJFrame(int closeWay, JFrame parentComponent, Stu stu) {
        super(closeWay, parentComponent);
        this.stu=stu;
    }

    @Override
    public void init() {
        if(this.stu==null){
            this.setVisible(false);
            JOptionPane.showMessageDialog(parentComponent,"请选择要查看的学生");
            return;
        }
        this.add(infoPanel=new JPanel(new GridLayout(7,1)),BorderLayout.CENTER);
        this.infoPanel.add(this.idLabel=new JLabel("学号:"+this.stu.getId()));
        this.infoPanel.add(this.nameLabel=new JLabel("姓名:"+this.stu.getName()));
        this.infoPanel.add(this.entryYearLabel=new JLabel("入学年份:"+this.stu.getEntryYear()));
        this.infoPanel.add(this.specialityLabel=new JLabel("专业:"+this.stu.getSpeciality()));
        this.infoPanel.add(this.birthdayLabel=new JLabel("出生日期:"+this.stu.getBirthday()));
        this.infoPanel.add(this.provinceCityLabel=new JLabel("省份:"+this.stu.getProvince()+" 城市:"+this.stu.getCity()));
        this.infoPanel.add(this.roleLabel=new JLabel("角色:"+this.stu.getRole()));
        this.add(this.buttonPanel=new JPanel(),BorderLayout.SOUTH);
        this.buttonPanel.add(this.backButton=new JButton("返回"),BorderLayout.SOUTH);
        this.backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
