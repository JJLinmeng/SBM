package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.function.MouseClickFunction;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class StuMainJFrame extends ConfigJFrame {
    private JPanel navbarPanel;
    private JPanel sidePanel;
    private JPanel mainPanel;

    private JLabel addLabel;
    private JLabel deleteLabel;
    private JLabel searchLabel;
    private JLabel personLabel;
    private JLabel logLabel;
    private JLabel backLabel;

    public StuMainJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(this.navbarPanel = new JPanel(), BorderLayout.NORTH);
        this.add(this.sidePanel = new JPanel(new GridLayout(6, 1)), BorderLayout.WEST);

        this.navbarPanel.add(new JLabel("欢迎您：" + Loginer.user.getName()));
        this.sidePanel.add(this.addLabel = new JLabel("添加学生"));
        this.sidePanel.add(this.deleteLabel = new JLabel("删除学生"));
        this.sidePanel.add(this.searchLabel = new JLabel("查询学生"));
        this.sidePanel.add(this.personLabel = new JLabel("个人信息"));
        this.sidePanel.add(this.logLabel = new JLabel("查看日志"));
        this.sidePanel.add(this.backLabel = new JLabel("退出登录"));

        this.addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("AddStuJFrame", JFrame.DISPOSE_ON_CLOSE, StuMainJFrame.this);
            }
        });
        this.deleteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("DeleteStuJFrame", JFrame.DISPOSE_ON_CLOSE, StuMainJFrame.this);
            }
        });
        this.searchLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("StuInfoListJFrame", JFrame.DISPOSE_ON_CLOSE, StuMainJFrame.this);
            }
        });
        this.personLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MouseClickFunction.openJFrame("StuPersonalCenterJFrame", JFrame.DISPOSE_ON_CLOSE, StuMainJFrame.this);
            }
        });
        this.logLabel.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(java.awt.event.MouseEvent e) {

            }
        });

        this.backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

            }
        });
    }
}
