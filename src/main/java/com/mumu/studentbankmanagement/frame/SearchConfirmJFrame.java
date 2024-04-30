package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.Stu;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class SearchConfirmJFrame extends ConfigJFrame {
    private Stu stu;
    private JLabel infoLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    @Getter
    private StuInfoListJFrame parentComponent;

    public SearchConfirmJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.parentComponent=(StuInfoListJFrame) parentComponent;
    }

    @Override
    public void init() {

        String searchKey = this.parentComponent.getSearchKeyComboBox().getSelectedItem().toString();
        String searchText = this.parentComponent.getSearchTextField().getText();
        this.stu = new Stu();
        if (searchKey.equals("默认")) {
            JOptionPane.showMessageDialog(this.parentComponent, "请选择搜索条件！");
            this.setVisible(false);
        } else if (searchKey.equals("姓名")) {
            this.stu.setName(searchText);
        } else if (searchKey.equals("学号")) {
            this.stu.setId(searchText);
        } else if (searchKey.equals("专业")) {
            this.stu.setSpeciality(searchText);
        } else if (searchKey.equals("入学年份")) {
           try {
               this.stu.setEntryYear(Integer.parseInt(this.parentComponent.getSearchTextField().getText()));
           }
           catch (NumberFormatException e){
               JOptionPane.showMessageDialog(this.parentComponent, "入学年份输入错误！");
               this.setVisible(false);
           }
        }

        this.add(this.infoLabel = new JLabel("你确定要查找符合" + searchKey + "为" + searchText + "的所有学生吗？"),BorderLayout.CENTER);
        this.add(this.buttonPanel=new JPanel(),BorderLayout.SOUTH);
        this.buttonPanel.add(this.confirmButton = new JButton("确定"));
        this.buttonPanel.add(this.cancelButton = new JButton("取消"));
        this.cancelButton.addActionListener(e -> MouseClickFunction.closeJFrame(this));
        this.confirmButton.addActionListener(e -> MouseClickFunction.searchStu( this, stuService,stu));
    }
}
