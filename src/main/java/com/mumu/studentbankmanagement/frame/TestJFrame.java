package com.mumu.studentbankmanagement.frame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestJFrame extends ConfigJFrame {
    private JButton testButton;

    public TestJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    @Override
    public void init() {
        this.add(testButton=new JButton("测试"));
        testButton.addActionListener(e -> {
            //读取Resource中的speciality.txt文件
            InputStream inputStream = null;
            BufferedReader reader=null;
            try {
                inputStream = this.getClass().getClassLoader().getResourceAsStream("specialityId.txt");
                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] s = line.split(" ");
                        if(s!=null&&!s.equals("")){
                            stuService.addSpeciality(Integer.parseInt(s[1]), s[0]);
                        }

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    inputStream.close(); // 关闭输入流
                    reader.close(); // 关闭缓冲读取器
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
