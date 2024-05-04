package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;
import java.awt.*;

public class StuPersonalCenterJFrame extends ConfigJFrame{


    private JPanel infoPanel;
    private JPanel buttonPanel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JPanel passwordPanel;
    private JButton passwordChangeButton;
    private JLabel entryYearLabel;
    private JLabel specialityLabel;
    private JLabel birthdayLabel;
    private JLabel provinceCityLabel;
    private JLabel roleLabel;
    private JButton toolsButton;
    private JButton backButton;

    public StuPersonalCenterJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }


    @Override
    public void init() {
        this.add(infoPanel=new JPanel(new GridLayout(8,1)),BorderLayout.CENTER);
        this.infoPanel.add(this.idLabel=new JLabel("学号:"+Loginer.user.getId()));
        this.infoPanel.add(this.nameLabel=new JLabel("姓名:"+ Loginer.user.getName()));
        this.infoPanel.add(this.entryYearLabel=new JLabel("入学年份:"+Loginer.user.getEntryYear()));
        this.infoPanel.add(this.specialityLabel=new JLabel("专业:"+Loginer.user.getSpeciality()));
        this.infoPanel.add(this.birthdayLabel=new JLabel("出生日期:"+Loginer.user.getBirthday()));
        this.infoPanel.add(this.provinceCityLabel=new JLabel("省份:"+Loginer.user.getProvince()+" 城市:"+Loginer.user.getCity()));
        this.infoPanel.add(this.roleLabel=new JLabel("角色:"+Loginer.user.getRole()));
        this.add(this.buttonPanel=new JPanel(),BorderLayout.SOUTH);
        this.buttonPanel.add(this.passwordChangeButton=new JButton("修改密码"));
        this.passwordChangeButton.addActionListener(e-> MouseClickFunction.openJFrame("StuPasswordChangeJFrame",JFrame.DISPOSE_ON_CLOSE,this));
        this.buttonPanel.add(this.toolsButton=new JButton("工具箱"),BorderLayout.EAST);
        this.toolsButton.addActionListener(e-> MouseClickFunction.openJFrame("StuToolsJFrame",JFrame.DISPOSE_ON_CLOSE,this));
        this.buttonPanel.add(this.backButton=new JButton("返回"),BorderLayout.SOUTH);
        this.backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }

}
