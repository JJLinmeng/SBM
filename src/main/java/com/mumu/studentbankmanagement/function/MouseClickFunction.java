package com.mumu.studentbankmanagement.function;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.mumu.studentbankmanagement.JFrameFactory;
import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.Util.DateUtil;
import com.mumu.studentbankmanagement.frame.*;
import com.mumu.studentbankmanagement.model.BankInfo;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.Stu;
import com.mumu.studentbankmanagement.service.BankService;
import com.mumu.studentbankmanagement.service.StuService;

import com.itextpdf.text.Image;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
            if (stuService.checkIsExist(stu.getId()) == null) {
                stuService.addStudent(stu);
                if (parent.parentComponent instanceof StuInfoListJFrame) {
                    ((StuInfoListJFrame) parent.parentComponent).updateTable(StuInfoListJFrame.ADD, stu, null);
                }
                int result = JOptionPane.showConfirmDialog(parent, "成功添加" + stu + " " + "是否继续添加？", "提示", JOptionPane.YES_NO_OPTION);
                // 检查用户的选择
                if (result == JOptionPane.YES_OPTION) {
                    resetStuInfo(parent);
                } else {
                    closeJFrame(parent);
                }
            } else {
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
                Loginer.user = user;
                MouseClickFunction.openJFrame("StuMainJFrame", JFrame.EXIT_ON_CLOSE, parent);
            } else {
                //登录失败
                JOptionPane.showMessageDialog(parent, "登录失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void checkIsLogin(BankLoginJFrame parent, BankService userService) {
        String id = parent.getIdTextField().getText();
        String password = new String(parent.getPasswordTextField().getPassword());
        try {
            CardOwner cardOwner = userService.login(id, password);
            if (cardOwner != null) {
                //登录成功
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.cardOwner = cardOwner;
                MouseClickFunction.openJFrame("CardOwnerJFrame", JFrame.EXIT_ON_CLOSE, parent);
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
        if (stuList.isEmpty()) {
            JOptionPane.showMessageDialog(searchConfirmFrame, "未找到符合条件的用户", "提示", JOptionPane.WARNING_MESSAGE);
            MouseClickFunction.closeJFrame(searchConfirmFrame);
        } else {
            searchConfirmFrame.getParentComponent().updateTable(StuInfoListJFrame.SEARCH, null, stuList);
        }
        MouseClickFunction.closeJFrame(searchConfirmFrame);
    }

    public static void openJFrame(String frame, int closeway, JFrame parent, Stu stu) {
        JFrameFactory.create(frame, closeway, parent, stu);
    }

    public static void openJFrame(String frame, int closeway, JFrame parent) {
        JFrameFactory.create(frame, closeway, parent);
    }

    public static void updateStu(UpdateStuJFrame updateStuJFrame, StuService stuService, Stu stu) {
        updateStuJFrame.getParentComponent().updateTable(StuInfoListJFrame.DELETE, stu, null);
        String name = updateStuJFrame.getNameTextField().getText();
        String password = new String(updateStuJFrame.getPasswordTextField().getPassword());
        LocalDate birthday = DateUtil.DateToLocalDate(updateStuJFrame.getBirthdayDatePicker().getDate());
        String speciality = updateStuJFrame.getSpecialityTextField().getText();
        int entryYear = Integer.parseInt(updateStuJFrame.getEntryYearTextField().getText());
        String province = updateStuJFrame.getProvinceCitySelector().getProvinceComboBox().getSelectedItem().toString();
        String city = updateStuJFrame.getProvinceCitySelector().getCityComboBox().getSelectedItem().toString();
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
        updateStuJFrame.getParentComponent().updateTable(StuInfoListJFrame.ADD, stu, null);

    }

    public static void modifyStuPassword(StuPasswordChangeJFrame stuPasswordChangeJFrame, StuService stuService) {
        String oldPassword = new String(stuPasswordChangeJFrame.getOldPasswordField().getPassword());
        String newPassword = new String(stuPasswordChangeJFrame.getNewPasswordField().getPassword());
        String confirmPassword = new String(stuPasswordChangeJFrame.getConfirmPasswordField().getPassword());
        if (!oldPassword.equals(Loginer.user.getPassword())) {
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "原密码错误", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (newPassword.equals(oldPassword)) {
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "新密码不能与原密码相同", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            Loginer.user.setPassword(newPassword);
            stuService.updateStu(Loginer.user);
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "修改成功");
            MouseClickFunction.closeJFrame(stuPasswordChangeJFrame);
        }
    }


    public static void registerCardOwner(BankRegisterJFrame bankRegisterJFrame, BankService bankService) {
        String id = bankRegisterJFrame.getIdTextField().getText();
        String password = new String(bankRegisterJFrame.getPasswordField().getPassword());
        String name = bankRegisterJFrame.getNameTextField().getText();
        String confirmPassword = new String(bankRegisterJFrame.getPasswordConfirmField().getPassword());
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (id.isEmpty()) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "请输入身份证号", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "请输入姓名", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            CardOwner cardOwner = new CardOwner(id, name, password);
            int result = bankService.registerCardOwner(cardOwner);
            if (result > 0) {
                JOptionPane.showMessageDialog(bankRegisterJFrame, "注册成功");
                MouseClickFunction.closeJFrame(bankRegisterJFrame);
                BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"注册",new BigDecimal("0"),id,"null");
                bankService.addBankInfo(bankInfo);
            } else {
                JOptionPane.showMessageDialog(bankRegisterJFrame, "注册失败", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    public static void picToPdf(PicToPdfJFrame parent) {
        List<String> picArray = new ArrayList<>();
        if (parent.getDestFileTextField().getText().isEmpty() || parent.getSourceFileTextField().getText().isEmpty() || parent.getDestFileTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "请输入正确的文件路径", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String destFilePath = parent.getDestFileTextField().getText() + "/" + parent.getFileNameTextField().getText() + ".pdf";
        String sourceFilePath = parent.getSourceFileTextField().getText();
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                picArray.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (picArray.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "请输入正确的文件路径", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(destFilePath));
            document.open();
            for (String imageUrl : picArray) {
                Image image = Image.getInstance(new URL(imageUrl));
                Rectangle pageSize = document.getPageSize();
                if (image.getWidth() > pageSize.getWidth() || image.getHeight() > pageSize.getHeight()) {
                    image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
                }
                document.newPage();
                image.setAbsolutePosition(0, 0);
                document.add(image);
            }
            document.close();
            JOptionPane.showMessageDialog(parent, "转换成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "转换失败,可能由于文件内内容并非图片地址", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void deposit(DepositJFrame depositJFrame, BankService bankService) {
        String cardNumber=depositJFrame.getCardNumberTextField().getText();
        String password=new String(depositJFrame.getPasswordTextField().getPassword());
        String amount=depositJFrame.getDepositAmountTextField().getText();
        if(cardNumber.isEmpty()||password.isEmpty()||amount.isEmpty()){
            JOptionPane.showMessageDialog(depositJFrame,"请输入正确的信息","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!amount.matches("\\d+(\\.\\d{1,2})?")){
            JOptionPane.showMessageDialog(depositJFrame,"请输入正确的金额","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cardOwnerId=bankService.getCardOwnerByCardNumber(cardNumber);
        if (cardOwnerId==null){
            JOptionPane.showMessageDialog(depositJFrame,"该卡号不存在","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!cardOwnerId.equals(Loginer.cardOwner.getId())){
            JOptionPane.showMessageDialog(depositJFrame,"该卡号不属于您","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(password.equals(bankService.getCardPassword(cardNumber))){
            bankService.deposit(cardNumber,amount);
            JOptionPane.showMessageDialog(depositJFrame,"存款成功","提示",JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"存款",new BigDecimal(amount),Loginer.cardOwner.getId(),cardNumber);
            bankService.addBankInfo(bankInfo);
        }
    }

    public static void openAccount(OpenAccountJFrame openAccountJFrame, BankService bankService) {
        String cardNumber="5463455545";//后续通过算法生成
        String id = openAccountJFrame.getOwnerIdTextField().getText();
        String password = new String(openAccountJFrame.getPasswordTextField().getPassword());
        String confirmPassword = new String(openAccountJFrame.getConfirmPasswordTextField().getPassword());
        if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(openAccountJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(openAccountJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!bankService.isRegister(id)) {
            JOptionPane.showMessageDialog(openAccountJFrame, "该用户未注册", "提示", JOptionPane.WARNING_MESSAGE);
        }
        if(!id.equals(Loginer.cardOwner.getId())){
            JOptionPane.showMessageDialog(openAccountJFrame,"该身份证号不属于您,请本人来注册","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(bankService.openAccount(cardNumber,id,password)==1){{
            JOptionPane.showMessageDialog(openAccountJFrame,"开户成功","提示",JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"开户",new BigDecimal(0),id,cardNumber);
            bankService.addBankInfo(bankInfo);
        }}
        else{
            JOptionPane.showMessageDialog(openAccountJFrame,"开户失败","提示",JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void withdraw(WithdrawJFrame withdrawJFrame, BankService bankService) {
        String cardNumber=withdrawJFrame.getCardNumberTextField().getText();
        String password=new String(withdrawJFrame.getPasswordField().getPassword());
        String amount=withdrawJFrame.getMoneyTextField().getText();
        if(cardNumber.isEmpty()||password.isEmpty()||amount.isEmpty()){
            JOptionPane.showMessageDialog(withdrawJFrame,"请输入正确的信息","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!amount.matches("\\d+(\\.\\d{1,2})?")){
            JOptionPane.showMessageDialog(withdrawJFrame,"请输入正确的金额","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cardOwnerId=bankService.getCardOwnerByCardNumber(cardNumber);
        if (cardOwnerId==null){
            JOptionPane.showMessageDialog(withdrawJFrame,"该卡号不存在","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!cardOwnerId.equals(Loginer.cardOwner.getId())){
            JOptionPane.showMessageDialog(withdrawJFrame,"该卡号不属于您","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(new BigDecimal(amount).compareTo(bankService.getCardBalance(cardNumber))>0 ) {
            JOptionPane.showMessageDialog(withdrawJFrame,"余额不足","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(password.equals(bankService.getCardPassword(cardNumber))){
            bankService.withdraw(cardNumber,amount);
            JOptionPane.showMessageDialog(withdrawJFrame,"取款成功","提示",JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"取款",new BigDecimal(amount),Loginer.cardOwner.getId(),cardNumber);
            bankService.addBankInfo(bankInfo);
        }
    }

    public static void transfer(TransferJFrame transferJFrame, BankService bankService) {
        String payerCardNumber=transferJFrame.getPayerCardNumberTextField().getText();
        String payeeCardNumber=transferJFrame.getPayeeCardNumberTextField().getText();
        String payerPassword=new String(transferJFrame.getPayerPasswordField().getPassword());
        String amount=transferJFrame.getAmountTextField().getText();
        if(payerPassword.isEmpty()||amount.isEmpty()||payerCardNumber.isEmpty()||payeeCardNumber.isEmpty()){
            JOptionPane.showMessageDialog(transferJFrame,"请输入正确的信息","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!amount.matches("\\d+(\\.\\d{1,2})?")){
            JOptionPane.showMessageDialog(transferJFrame,"请输入正确的金额","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(new BigDecimal(amount).compareTo(bankService.getCardBalance(payerCardNumber))>0){
            JOptionPane.showMessageDialog(transferJFrame,"余额不足","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(bankService.getCardOwnerByCardNumber(payerCardNumber)==null){
            JOptionPane.showMessageDialog(transferJFrame,"付款卡号不存在","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(bankService.getCardOwnerByCardNumber(payeeCardNumber)==null){
            JOptionPane.showMessageDialog(transferJFrame,"收款卡号不存在","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!bankService.getCardOwnerByCardNumber(payerCardNumber).equals(Loginer.cardOwner.getId())){
            JOptionPane.showMessageDialog(transferJFrame,"付款卡号不存在","提示",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!payerPassword.equals(bankService.getCardPassword(payerCardNumber))){
           JOptionPane.showMessageDialog(transferJFrame,"付款密码错误","提示",JOptionPane.WARNING_MESSAGE);
           return;
        }
        bankService.transfer(payerCardNumber,payeeCardNumber,amount);
        JOptionPane.showMessageDialog(transferJFrame,"转账成功","提示",JOptionPane.INFORMATION_MESSAGE);
        BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"转账",new BigDecimal(amount),Loginer.cardOwner.getId(),payerCardNumber);
        bankService.addBankInfo(bankInfo);
        bankInfo=new BankInfo(LocalDateTime.now(),"收账",new BigDecimal(amount),bankService.getCardOwnerByCardNumber(payeeCardNumber),payeeCardNumber);
        bankService.addBankInfo(bankInfo);
    }

    public static void cancelAccount(CancelAccountJFrame cancelAccountJFrame, BankService bankService) {

        String cardNumber = cancelAccountJFrame.getCardNumberTextField().getText();
        String id=cancelAccountJFrame.getOwnerIdTextField().getText();
        String password = new String(cancelAccountJFrame.getPasswordTextField().getPassword());
        BigDecimal cardBalance = bankService.getCardBalance(cardNumber);
        if (cardNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!id.equals(Loginer.cardOwner.getId())){
            JOptionPane.showMessageDialog(cancelAccountJFrame, "这不是你的卡,如确定要销户,请联系管理员", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!password.equals(bankService.getCardPassword(cardNumber))){
            JOptionPane.showMessageDialog(cancelAccountJFrame, "密码错误", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(cardBalance.compareTo(new BigDecimal(0))>0){
            JOptionPane.showMessageDialog(cancelAccountJFrame, "请先将余额清零", "提示", JOptionPane.WARNING_MESSAGE);
            int result = JOptionPane.showConfirmDialog(cancelAccountJFrame, "是否以现金方式全部转出,共"+cardBalance);
            if(result==JOptionPane.YES_OPTION){
                bankService.withdraw(cardNumber,cardBalance.toString());
                BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"取款",cardBalance,id,cardNumber);
                JOptionPane.showMessageDialog(cancelAccountJFrame,"取款成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }
            else return;
        }
        int choice = JOptionPane.showConfirmDialog(cancelAccountJFrame, "请再次确认是否删除该卡号" + cardNumber);
        if (choice == JOptionPane.YES_OPTION){
            bankService.cancelAccount(cardNumber);
            JOptionPane.showMessageDialog(cancelAccountJFrame, "销户成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"销户",new BigDecimal("0"),Loginer.cardOwner.getId(),cardNumber);
            bankService.addBankInfo(bankInfo);
        }


    }

}


