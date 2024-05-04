package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.function.MouseClickFunction;

import javax.swing.*;
import java.awt.*;

public class StuToolsJFrame extends ConfigJFrame{
    private JButton picToPdfButton;
    private JButton backButton;
    public StuToolsJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.setLayout(new GridLayout(5,1));
        this.add(picToPdfButton=new JButton("图片转PDF"));
        picToPdfButton.addActionListener(e-> MouseClickFunction.openJFrame("PicToPdfJFrame",JFrame.DISPOSE_ON_CLOSE,this));
        this.add(backButton=new JButton("返回"));
        backButton.addActionListener(e-> MouseClickFunction.closeJFrame(this));
    }
}
