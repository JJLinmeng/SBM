package com.mumu.studentbankmanagement.frame;

import com.mumu.studentbankmanagement.component.GridBagPanel;
import com.mumu.studentbankmanagement.function.MouseClickFunction;
import com.mumu.studentbankmanagement.model.Stu;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class DeleteStuJFrame extends ConfigJFrame {
    JLabel stuIdLabel;
    JTextField stuIdTextField;
    GridBagPanel gridBagPanel;
    JButton deleteButton;
    private Stu stu;

    public DeleteStuJFrame(int closeWay, JFrame parentComponent) {
        super(closeWay, parentComponent);
    }

    public DeleteStuJFrame(int closeWay, JFrame parentComponent, Stu stu) {
        super(closeWay, parentComponent);
        if (stu == null) {
            this.setVisible(false);
            JOptionPane.showMessageDialog(parentComponent, "请选择要删除的学生");
            return;
        }
        this.stu = stu;
    }

    @Override
    public void init() {
        this.add(this.gridBagPanel = new GridBagPanel(), BorderLayout.CENTER);
        this.gridBagPanel.add(0, 0, 0.5, this.stuIdLabel = new JLabel("学号："));
        this.gridBagPanel.add(1, 0, 1, this.stuIdTextField = new JTextField(10));
        this.add(deleteButton = new JButton("删除"), BorderLayout.SOUTH);
        this.deleteButton.addActionListener(e -> MouseClickFunction.deleteStudent(this, stuService));
        if (stu != null) {
            this.stuIdTextField.setText(stu.getId());
            this.stuIdTextField.setEditable(false);
        }
    }

}
