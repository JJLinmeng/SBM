package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.JFrameFactory;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.Stu;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StuInfoListJFrame extends ConfigJFrame {
    public static int ADD = 0;
    public static int DELETE = 1;
    public static final int SEARCH = 2;
    public static final int UPDATE = 3;
    DefaultListModel<Stu> stuListModel;
    private JList<Stu> stuList;
    private List<Stu> allStudent;
    private JPanel toolPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JLabel searchKeyLabel;
    @Getter
    private JComboBox<String> searchKeyComboBox;
    private JLabel searchTextLabel;
    @Getter
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton updateButton;
    private JButton moreInfoButton;
    private JButton backButton;
    List<Stu> needStudent;
    JScrollPane jScrollPane;

    public StuInfoListJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
        this.setTitle("学生信息列表");
        allStudent = stuService.getAllStudent();
    }

    @Override
    public void init() {
        this.add(this.jScrollPane = new JScrollPane(this.stuList=new JList<>(this.stuListModel=new DefaultListModel<>())), BorderLayout.CENTER);
        this.add(this.toolPanel = new JPanel(), BorderLayout.SOUTH);

        for (Stu stu : this.allStudent) {
            this.stuListModel.addElement(stu);
        }
        this.stuList.setVisibleRowCount(10);

        this.toolPanel.add(this.addButton = new JButton("添加"));
        this.toolPanel.add(this.deleteButton = new JButton("删除"));
        this.toolPanel.add(this.searchKeyLabel = new JLabel("查询关键词"));
        this.toolPanel.add(this.searchKeyComboBox = new JComboBox<>(new String[]{"默认", "学号", "姓名", "专业", "入学年份"}));
        this.toolPanel.add(this.searchTextLabel = new JLabel("查询内容"));
        this.toolPanel.add(this.searchTextField = new JTextField(10));
        this.toolPanel.add(this.searchButton = new JButton("查询"));
        this.toolPanel.add(this.updateButton = new JButton("修改"));
        this.toolPanel.add(this.moreInfoButton = new JButton("详细信息"));
        this.toolPanel.add(this.backButton = new JButton("返回"));

       this.addButton.addActionListener(e -> MouseClickFunction.openJFrame("AddStuJFrame",JFrame.DISPOSE_ON_CLOSE,this));
       this.deleteButton.addActionListener(e -> MouseClickFunction.openJFrame("DeleteStuJFrame",JFrame.DISPOSE_ON_CLOSE,this,stuList.getSelectedValue()));
       this.searchButton.addActionListener(e -> MouseClickFunction.openJFrame("SearchConfirmJFrame",JFrame.DISPOSE_ON_CLOSE,this));
       this.updateButton.addActionListener(e->MouseClickFunction.openJFrame("UpdateStuJFrame",JFrame.DISPOSE_ON_CLOSE,this,stuList.getSelectedValue()));
       this.moreInfoButton.addActionListener(e -> MouseClickFunction.openJFrame("StuInfoJFrame",JFrame.DISPOSE_ON_CLOSE,this,stuList.getSelectedValue()));
       this.backButton.addActionListener(e->{MouseClickFunction.closeJFrame(this);});
    }

    public void updateTable(int way, Stu stu, List<Stu> stuList) {
        if (needStudent != null) {
            needStudent.clear();
        } else {
            needStudent = new ArrayList<>();
        }
        if (way == ADD) {
            stuListModel.addElement(stu);
        } else if (way == DELETE) {

        } else if (way == SEARCH) {
            stuListModel.clear();
            for (Stu stu1 : stuList) {
                stuListModel.addElement(stu1);
            }
        } else if (way == UPDATE) {
        }
    }

}
