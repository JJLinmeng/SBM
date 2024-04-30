package com.mumu.studentbankmanagement.component;

import com.mumu.studentbankmanagement.Util.Loader;
import com.mumu.studentbankmanagement.frame.StuLoginJFrame;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import org.springframework.boot.logging.java.JavaLoggingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuLabel extends JLabel {
    private ImageIcon icon;
    private JFrame parentComponent;

    public MenuLabel(String text, String iconPath, JFrame parentComponent) throws IOException {
        // 创建一个ImageIcon对象，并读取图片文件
        icon = new ImageIcon(Loader.readPicture(iconPath));
// 设置JLabel的图标
        this.setIcon(icon);
// 设置JLabel的文本
        this.setText(text);
// 设置JLabel的字体
        this.setFont(new Font("微软雅黑", Font.PLAIN, 20));
// 设置JLabel的文本对齐方式
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
// 设置JLabel的背景颜色
        this.setBackground(new Color(255, 255, 255));
// 设置JLabel的边框
        this.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
// 添加鼠标监听器
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MouseClickFunction.openJFrame("StuLoginJFrame",JFrame.EXIT_ON_CLOSE,parentComponent);
            }
        });
    }
}
