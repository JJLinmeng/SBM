package com.mumu.studentbankmanagement.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ProvinceCitySelector extends JPanel {

    private JLabel provinceLabel;
    @Getter
    private JComboBox<String> provinceComboBox;
    private JLabel cityLabel;
    @Getter
    private JComboBox<String> cityComboBox;

    public ProvinceCitySelector() {

        setLayout(new GridBagLayout());
        provinceLabel=new JLabel("省份：");
        provinceComboBox = new JComboBox<>();

        cityLabel=new JLabel("城市：");
        cityComboBox = new JComboBox<>();
        provinceComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // 在这里可以设置字体、背景等，下面的例子仅设置了固定宽度
                setPreferredSize(new Dimension(200, getPreferredSize().height));
                return this;
            }
        });

        cityComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // 在这里可以设置字体、背景等，下面的例子仅设置了固定宽度
                setPreferredSize(new Dimension(200, getPreferredSize().height));
                return this;
            }
        });
        JPanel provincePanel = new JPanel();
        provincePanel.add(provinceLabel);
        provincePanel.add(provinceComboBox);

        JPanel cityPanel = new JPanel();
        cityPanel.add(cityLabel);
        cityPanel.add(cityComboBox);
        // 设置默认值
        provinceComboBox.addItem("请选择省份");
        cityComboBox.addItem("请选择城市");
        for (String s : getMyStaticVariable().keySet()) {
            provinceComboBox.addItem(s);
        }
        add(provincePanel);
        add(cityPanel);
        provinceComboBox.addActionListener(e->{
            String selectedProvince = (String) provinceComboBox.getSelectedItem();
            cityComboBox.removeAllItems();
            if(selectedProvince.equals("请选择省份")) {
                cityComboBox.addItem("请选择城市");
                cityComboBox.setSelectedIndex(0);
            }
            else{
                String[] cities = getMyStaticVariable().get(selectedProvince);
                cityComboBox.removeAllItems();
                for (String city : cities) {
                    cityComboBox.addItem(city);
                }
            }
        });
    }

    private static class Holder {
        static final Map<String,String[]> myStaticVariable = initializeStaticVariable();
    }

    // 外部类提供一个静态方法来访问myStaticVariable
    public static Map<String,String[]> getMyStaticVariable() {
        return Holder.myStaticVariable;
    }


    private static Map<String,String[]> initializeStaticVariable() {

        InputStream inputStream = ProvinceCitySelector.class.getClassLoader().getResourceAsStream("ProvinceCity.json");
        String jsonData=null;
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder resultStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
                 jsonData = resultStringBuilder.toString();
            } catch (IOException e) {
                // 处理异常
                e.printStackTrace();
            }
        } else {
            // Handle the case where the resource was not found

        }
        return computeInitialValue(jsonData);
    }

    private static Map<String,String[]> computeInitialValue(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<>() {});
    }
}
