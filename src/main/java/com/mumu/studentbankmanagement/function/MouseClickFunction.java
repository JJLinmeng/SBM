package com.mumu.studentbankmanagement.function;

import com.mumu.studentbankmanagement.JFrameFactory;
import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.Util.DateUtil;
import com.mumu.studentbankmanagement.frame.*;
import com.mumu.studentbankmanagement.model.Stu;
import com.mumu.studentbankmanagement.service.StuService;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class MouseClickFunction {

    public static void addStudent(AddStuJFrame parent, StuService stuService) {
        String name = parent.getNameTextField().getText();
        String password = new String(parent.getPasswordField().getPassword());
        LocalDate birthday = parent.getBirthdayDatePicker().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String speciality = parent.getSpecialityTextField().getText();
        int entryYear = Integer.parseInt(parent.getEntryYearTextField().getText());
        //这里要处理异常,甚至要考虑不填的情况,后续补充
        String province = parent.getProvinceCitySelector().getProvinceComboBox().getSelectedItem().toString();
        String city = parent.getProvinceCitySelector().getCityComboBox().getSelectedItem().toString();
        if (name.equals("") || password.equals("") || speciality.equals("")) {
            JOptionPane.showMessageDialog(parent, "请将信息填写完整,名字,密码,专业,入学年份不能为空", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            Stu stu = new Stu(name, password, birthday, speciality, entryYear, province, city, Stu.STUDENT);
            if(stuService.checkIsExist(stu.getId())==null) {
                stuService.addStudent(stu);
                if(parent.parentComponent instanceof StuInfoListJFrame) {
                    ((StuInfoListJFrame) parent.parentComponent).updateTable(StuInfoListJFrame.ADD, stu,null);
                }
                int result = JOptionPane.showConfirmDialog(parent, "成功添加"+stu+" "+"是否继续添加？","提示", JOptionPane.YES_NO_OPTION);
                // 检查用户的选择
                if (result == JOptionPane.YES_OPTION) {
                    resetStuInfo(parent);
                } else {
                    closeJFrame(parent);
                }
            }else{
                JOptionPane.showMessageDialog(parent, "该学号已存在", "提示", JOptionPane.WARNING_MESSAGE);
            }


        }
    }

    public static void resetStuInfo(AddStuJFrame parent) {
        parent.getNameTextField().setText("");
        parent.getPasswordField().setText("");
        parent.getBirthdayDatePicker().setDate(null);
        parent.getSpecialityTextField().setText("");
        parent.getEntryYearTextField().setText("");
        parent.getProvinceCitySelector().getProvinceComboBox().setSelectedIndex(0);
    }

    public static void closeJFrame(JFrame parent) {
        parent.setVisible(false);
    }

    public static void checkIsLogin(StuLoginJFrame parent, StuService userService) {
        String stuId = parent.getStuIdTextField().getText();
        String stuPwd = new String(parent.getStuPasswordTextField().getPassword());
        try {
            Stu user = userService.login(stuId, stuPwd);
            if (user != null) {
                //登录成功
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.user=user;
                MouseClickFunction.openJFrame("StuMainJFrame",JFrame.EXIT_ON_CLOSE, parent);
            } else {
                //登录失败
                JOptionPane.showMessageDialog(parent, "登录失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteStudent(DeleteStuJFrame deleteStuFrame, StuService stuService) {

        String stuId = deleteStuFrame.getStuIdTextField().getText();
        if (stuId.equals("")) {
            JOptionPane.showMessageDialog(deleteStuFrame, "请输入学号", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            Stu stu = stuService.checkIsExist(stuId);
            if (stu == null) {
                JOptionPane.showMessageDialog(deleteStuFrame, "请输入正确的学号", "提示", JOptionPane.WARNING_MESSAGE);
            } else if (stu.getRole() == Stu.ADMIN) { //管理员不能删除管理员
                JOptionPane.showMessageDialog(deleteStuFrame, "管理员不能删除管理员", "提示", JOptionPane.WARNING_MESSAGE);
            } else {
                // 弹出确认对话框
                int result = JOptionPane.showConfirmDialog(deleteStuFrame, "确定要删除" + stu + "吗?", "删除确认", JOptionPane.YES_NO_OPTION);

                // 检查用户的选择
                if (result == JOptionPane.YES_OPTION) {
                    // 用户点击了“是”，执行删除操作
                    stuService.deleteStudent(stuId);
                    JOptionPane.showMessageDialog(deleteStuFrame, "删除成功");
                    deleteStuFrame.setVisible(false); // 如果需要，隐藏或者关闭当前窗口

                } else {
                    JOptionPane.showMessageDialog(deleteStuFrame, "已取消删除");
                }
            }
        }


    }

    public static void searchStu(SearchConfirmJFrame searchConfirmFrame, StuService stuService, Stu stu) {
        List<Stu> stuList = stuService.selectStuByCondition(stu);
        if(stuList.isEmpty()){
            JOptionPane.showMessageDialog(searchConfirmFrame, "未找到符合条件的用户", "提示", JOptionPane.WARNING_MESSAGE);
            MouseClickFunction.closeJFrame(searchConfirmFrame);
        }else{
            searchConfirmFrame.getParentComponent().updateTable(StuInfoListJFrame.SEARCH,null,stuList);
        }
        MouseClickFunction.closeJFrame(searchConfirmFrame);
    }

    public static void openJFrame(String frame,int closeway,JFrame parent,Stu stu) {
        JFrameFactory.create(frame,closeway, parent,stu);
    }
    public static void openJFrame(String frame,int closeway,JFrame parent) {
        JFrameFactory.create(frame,closeway, parent);
    }

    public static void updateStu(UpdateStuJFrame updateStuJFrame, StuService stuService,Stu stu) {
        updateStuJFrame.getParentComponent().updateTable(StuInfoListJFrame.DELETE,stu,null);
        String name = updateStuJFrame.getNameTextField().getText();
        String password = new String(updateStuJFrame.getPasswordTextField().getPassword());
        LocalDate birthday = DateUtil.DateToLocalDate(updateStuJFrame.getBirthdayDatePicker().getDate());
        String speciality = updateStuJFrame.getSpecialityTextField().getText();
        int entryYear = Integer.parseInt(updateStuJFrame.getEntryYearTextField().getText());
        String province=updateStuJFrame.getProvinceCitySelector().getProvinceComboBox().getSelectedItem().toString();
        String city=updateStuJFrame.getProvinceCitySelector().getCityComboBox().getSelectedItem().toString();
        stu.setName(name);
        stu.setPassword(password);
        stu.setBirthday(birthday);
        stu.setSpeciality(speciality);
        stu.setEntryYear(entryYear);
        stu.setProvince(province);
        stu.setCity(city);
        stuService.updateStu(stu);
        JOptionPane.showMessageDialog(updateStuJFrame, "修改成功");
        MouseClickFunction.closeJFrame(updateStuJFrame);
        updateStuJFrame.getParentComponent().updateTable(StuInfoListJFrame.ADD,stu,null);
    }

    public static void modifyStuPassword(StuPasswordChangeJFrame stuPasswordChangeJFrame, StuService stuService) {
        String oldPassword = new String(stuPasswordChangeJFrame.getOldPasswordField().getPassword());
        String newPassword = new String(stuPasswordChangeJFrame.getNewPasswordField().getPassword());
        String confirmPassword = new String(stuPasswordChangeJFrame.getConfirmPasswordField().getPassword());
        if(!oldPassword.equals(Loginer.user.getPassword())){
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "原密码错误", "提示", JOptionPane.WARNING_MESSAGE);
        }
        else if(newPassword.equals(oldPassword)){
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "新密码不能与原密码相同", "提示", JOptionPane.WARNING_MESSAGE);
        }
        else if(!newPassword.equals(confirmPassword)){
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
        }
        else{
            Loginer.user.setPassword(newPassword);
            stuService.updateStu(Loginer.user);
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "修改成功");
            MouseClickFunction.closeJFrame(stuPasswordChangeJFrame);
        }
    }
}

