package com.mumu.studentbankmanagement.component;

import javax.swing.*;
import java.awt.*;

public class GridBagPanel extends JPanel {
    public GridBagPanel() {
        // 将此JPanel的布局设置为GridBagLayout
        this.setLayout(new GridBagLayout());
    }
    public void add(int x, int y, double proportion,Component c){
        // 创建一个GridBagConstraints对象
        GridBagConstraints gbc = new GridBagConstraints();
        // x列
        gbc.gridx = x;
        // y行
        gbc.gridy = y;
        // 该列所占比
        gbc.weightx=proportion;
        // 将Component对象c添加到此JPanel中
        this.add(c, gbc);
    }
}
