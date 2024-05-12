package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import lombok.Getter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class PicToPdfJFrame extends ConfigJFrame {
    private JButton sourceFileButton;
    @Getter
    private JTextField sourceFileTextField;
    private GridBagPanel gridBagPanel;
    private JButton destFileButton;
    @Getter
    private JTextField destFileTextField;
    private JLabel fileNameLabel;
    @Getter
    private JTextField fileNameTextField;
    private JLabel wayLabel;
    private JPanel wayPanel;
    @Getter
    private ButtonGroup wayGroup;
    private JRadioButton localRadioButton;
    private JRadioButton networkRadioButton;
    JFileChooser txtChooser;
    JFileChooser directoryChooser;
    private JPanel buttonPanel;
    private JButton startButton;
    private JButton cancelButton;

    public PicToPdfJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        txtChooser = new JFileChooser();
        txtChooser.setFileFilter(new FileNameExtensionFilter("TXT文档", "txt"));
        this.add(gridBagPanel = new GridBagPanel(), BorderLayout.CENTER);
        gridBagPanel.add(0, 0, 1, sourceFileTextField = new JTextField(20));
        gridBagPanel.add(1, 0, 0.2, sourceFileButton = new JButton("选择文件"));
        sourceFileButton.addActionListener(e -> {
            int returnValue = txtChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = txtChooser.getSelectedFile();
                this.sourceFileTextField.setText(selectedFile.getAbsolutePath());
            }
        });
        directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        gridBagPanel.add(0, 1, 1, destFileTextField = new JTextField(20));
        gridBagPanel.add(1, 1, 0.2, destFileButton = new JButton("选择目标文件夹"));
        gridBagPanel.add(0, 3,1 , wayPanel=new JPanel());
        gridBagPanel.add(1, 3,0.2 , wayLabel = new JLabel("转换方式"));

        wayGroup = new ButtonGroup();

        wayGroup.add(localRadioButton = new JRadioButton("本地图片地址转换"));
        wayGroup.add( networkRadioButton = new JRadioButton("网络图片地址转换",true));

        wayPanel.add(localRadioButton);
        wayPanel.add(networkRadioButton);

        destFileButton.addActionListener(e -> {
            int returnValue = directoryChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = directoryChooser.getSelectedFile();
                this.destFileTextField.setText(selectedFile.getAbsolutePath());
            }
        });
        gridBagPanel.add(0, 2, 1, fileNameTextField = new JTextField(20));
        gridBagPanel.add(1, 2, 0.2, fileNameLabel = new JLabel("文件名"));
        this.add(buttonPanel = new JPanel(), BorderLayout.SOUTH);
        buttonPanel.add(startButton = new JButton("开始转换"));
        startButton.addActionListener(e -> {
            MouseClickFunction.picToPdf(this);
        });
        buttonPanel.add(cancelButton = new JButton("取消"));
        cancelButton.addActionListener(e -> {
            MouseClickFunction.closeJFrame(this);
        });
    }
}

