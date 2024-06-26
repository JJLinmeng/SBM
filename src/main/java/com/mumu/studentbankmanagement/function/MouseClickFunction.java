package com.mumu.studentbankmanagement.function;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.mumu.studentbankmanagement.Encryption;
import com.mumu.studentbankmanagement.JFrameFactory;
import com.mumu.studentbankmanagement.Loginer;
import com.mumu.studentbankmanagement.Mail;
import com.mumu.studentbankmanagement.Util.DateUtil;
import com.mumu.studentbankmanagement.frame.*;
import com.mumu.studentbankmanagement.model.BankInfo;
import com.mumu.studentbankmanagement.model.BankManager;
import com.mumu.studentbankmanagement.model.CardOwner;
import com.mumu.studentbankmanagement.model.Stu;
import com.mumu.studentbankmanagement.service.BankService;
import com.mumu.studentbankmanagement.service.StuService;

import com.itextpdf.text.Image;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public class MouseClickFunction {

    public static void addStudent(AddStuJFrame parent, StuService stuService) {

        String password = null;
        int entryYear = 0;
        try {
            password = Encryption.encrypt(new String(parent.getPasswordField().getPassword()));
            entryYear = Integer.parseInt(parent.getEntryYearTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "入学年份格式错误", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = parent.getNameTextField().getText();
        LocalDate birthday = parent.getBirthdayDatePicker().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String speciality = parent.getSpecialityTextField().getText();

        String identityNumber = parent.getIdentityNumberTextField().getText();
        String province = parent.getProvinceCitySelector().getProvinceComboBox().getSelectedItem().toString();
        String city = parent.getProvinceCitySelector().getCityComboBox().getSelectedItem().toString();
        if (name.equals("") || password.equals("") || speciality.equals("") || identityNumber.equals("")) {
            JOptionPane.showMessageDialog(parent, "请将信息填写完整,名字,密码,专业,入学年份,身份证号不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!identityNumber.matches("(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)")) {
            JOptionPane.showMessageDialog(parent, "身份证号格式错误", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Stu stu = new Stu(name, password, birthday, speciality, entryYear, province, city, Stu.STUDENT, identityNumber);
        stu.setId(stuService.getSpecId(speciality) + entryYear + StringUtils.leftPad(String.valueOf(stuService.getNumber(speciality, entryYear) + 1), 3, "0"));
        System.out.println(stu.getId());
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

    public static void checkIsLogin(StuLoginJFrame parent, StuService userService, BankService bankService) {
        String stuId = parent.getStuIdTextField().getText();
        String stuPwd = null;
        try {
            stuPwd = Encryption.encrypt(new String(parent.getStuPasswordTextField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ButtonGroup group = parent.getRoleGroup();
        String role = "";
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                role = button.getText();
            }
        }
        if (role.equals("")) {
            JOptionPane.showMessageDialog(parent, "请选择角色", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (role.equals("学生")) {
            Stu user = userService.login(stuId, stuPwd);
            if (user != null) {
                //登录成功
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.user = user;
                if (user.getRole() == Stu.STUDENT) {
                    MouseClickFunction.openJFrame("StuPersonalCenterJFrame", JFrame.EXIT_ON_CLOSE, parent);
                } else if (user.getRole() == Stu.ADMIN) {
                    MouseClickFunction.openJFrame("StuMainJFrame", JFrame.EXIT_ON_CLOSE, parent);
                } else {
                    //登录失败
                    JOptionPane.showMessageDialog(parent, "登录失败");
                }

            }
        } else if (role.equals("财务部")) {
            CardOwner cardOwner = new CardOwner(stuId, "南京工程学院财务部", stuPwd);
            if (bankService.loginByCardOwner(cardOwner) > 0) {
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.cardOwner = cardOwner;
                MouseClickFunction.openJFrame("FinancialDepartmentJFrame", JFrame.EXIT_ON_CLOSE, parent);
            } else {
                JOptionPane.showMessageDialog(parent, "登录失败");
            }

        }
    }

    public static void checkIsLogin(BankLoginJFrame parent, BankService userService) {
        String id = parent.getIdTextField().getText();
        String password = null;
        try {
            password = Encryption.encrypt(new String(parent.getPasswordTextField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (id.equals("810327")) {
            BankManager bankManager=userService.loginByBankManager(id,password);
            if(bankManager!=null){
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.bankManager=bankManager;
                MouseClickFunction.openJFrame("BankManagerJFrame", JFrame.EXIT_ON_CLOSE, parent);
            }
        }
        else {
            CardOwner cardOwner = userService.login(id, password);
            if (cardOwner != null) {
                JOptionPane.showMessageDialog(parent, "登录成功");
                parent.setVisible(false);
                parent.getParentComponent().setVisible(false);
                Loginer.cardOwner = cardOwner;
                MouseClickFunction.openJFrame("CardOwnerJFrame", JFrame.EXIT_ON_CLOSE, parent);
            } else {
                JOptionPane.showMessageDialog(parent, "登录失败");
            }
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
    public static void openJFrame(String frame, int closeway, JFrame parent,CardOwner cardOwner) {
        JFrameFactory.create(frame, closeway, parent, cardOwner);
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
        stu.setName(name);stu.setPassword(password);stu.setBirthday(birthday);stu.setSpeciality(speciality);stu.setEntryYear(entryYear);stu.setProvince(province);stu.setCity(city);
        stuService.updateStu(stu);
        JOptionPane.showMessageDialog(updateStuJFrame, "修改成功");
        MouseClickFunction.closeJFrame(updateStuJFrame);
        updateStuJFrame.getParentComponent().updateTable(StuInfoListJFrame.ADD, stu, null);

    }

    public static void modifyStuPassword(StuPasswordChangeJFrame stuPasswordChangeJFrame, StuService stuService) {
        String oldPassword = null;
        String newPassword = null;
        String confirmPassword = null;
        try {
            oldPassword = Encryption.encrypt(new String(stuPasswordChangeJFrame.getOldPasswordField().getPassword()));
            newPassword = Encryption.encrypt(new String(stuPasswordChangeJFrame.getNewPasswordField().getPassword()));
            confirmPassword = Encryption.encrypt(new String(stuPasswordChangeJFrame.getConfirmPasswordField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(stuPasswordChangeJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
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
        String password = null;
        String confirmPassword = null;
        try {
            password = Encryption.encrypt(new String(bankRegisterJFrame.getPasswordField().getPassword()));
            confirmPassword = Encryption.encrypt(new String(bankRegisterJFrame.getPasswordConfirmField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = bankRegisterJFrame.getNameTextField().getText();
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (id.isEmpty()) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "请输入身份证号", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(bankRegisterJFrame, "请输入姓名", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            CardOwner cardOwner = new CardOwner(id, name, password);
            if (!bankService.isRegister(id)) {
                JOptionPane.showMessageDialog(bankRegisterJFrame, "该身份证号已注册", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int result = bankService.registerCardOwner(cardOwner);
            if (result > 0) {
                JOptionPane.showMessageDialog(bankRegisterJFrame, "注册成功");
                MouseClickFunction.closeJFrame(bankRegisterJFrame);
                BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "注册", new BigDecimal("0"), id, "null");
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
        ButtonGroup wayGroup = parent.getWayGroup();
        String way = null;
        for (Enumeration<AbstractButton> buttons = wayGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                way = button.getText();
                break;
            }
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
            if(way.equals("网络图片地址转换")){
                for (String imageUrl : picArray) {
                    Image image=null;
                   try{
                       image = Image.getInstance(new URL(imageUrl));
                   }catch (Exception e){
                           JOptionPane.showMessageDialog(parent, "转换失败,可能由于地址并非网络图片地址原因", "提示", JOptionPane.WARNING_MESSAGE);
                   }
                    Rectangle pageSize = document.getPageSize();

                   image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());

                    document.newPage();
                    image.setAbsolutePosition(0, 0);
                    document.add(image);
                }
            }
            else if(way.equals("本地图片地址转换")){
                for (String imageUrl : picArray) {
                    Image image = Image.getInstance(imageUrl);
                    Rectangle pageSize = document.getPageSize();
                    image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());

                    document.newPage();
                    image.setAbsolutePosition(0, 0);
                    document.add(image);
                }
            }
            document.close();
            JOptionPane.showMessageDialog(parent, "转换成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "转换失败,可能由于文件内容并非图片地址", "提示", JOptionPane.WARNING_MESSAGE);
        }

    }

    public static void deposit(DepositJFrame depositJFrame, BankService bankService) {
        String cardNumber = depositJFrame.getCardNumberTextField().getText();
        String password = null;
        try {
            password = Encryption.encrypt(new String(depositJFrame.getPasswordTextField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(depositJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String amount = depositJFrame.getDepositAmountTextField().getText();
        if (cardNumber.isEmpty() || password.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(depositJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(depositJFrame, "请输入正确的金额", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cardOwnerId = bankService.getCardOwnerByCardNumber(cardNumber);
        if (cardOwnerId == null) {
            JOptionPane.showMessageDialog(depositJFrame, "该卡号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cardOwnerId.equals(Loginer.cardOwner.getId())) {
            JOptionPane.showMessageDialog(depositJFrame, "该卡号不属于您", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.equals(bankService.getCardPassword(cardNumber))) {
            bankService.deposit(cardNumber, amount);
            JOptionPane.showMessageDialog(depositJFrame, "存款成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "存款", new BigDecimal(amount), Loginer.cardOwner.getId(), cardNumber,bankService.getCardBalance(cardNumber));
            bankService.addBankInfo(bankInfo);
            String email = bankService.getEmail(cardOwnerId);
            if (email != null) {
                Mail.sendMail(email, "存款成功", "您卡号" + cardNumber + "已成功存款" + amount + "元");
            }
        }
    }

    public static String generatePin(BankService bankService) {
        String pin;
        BigInteger add = new BigInteger(String.valueOf(bankService.getAllCardsCount())).add(new BigInteger("456324896414")).remainder(new BigInteger("1000000000001"));
        pin = add.toString();
        return pin;
    }

    public static String generateBin() {
        return "650327";
    }

    public static String generateType(String type) {
        Random random = new Random();
        ArrayList<Integer> arrayList=new ArrayList<>();
        int randomNumber;
        if ("借记卡".equals(type)) {
            do {
                randomNumber = random.nextInt(10);
            } while (randomNumber % 2 == 0);
        } else {
            do {
                randomNumber = random.nextInt(10);
            } while (randomNumber % 2 != 0);
        }
        return String.valueOf(randomNumber);
    }

    public static String generateCheckNumber(String bin, String pin, String generateType) {
        int num = 0;
        for (int i = 0; i < bin.length(); i++) {
            num += bin.charAt(i) - '0';
        }
        for (int i = 0; i < pin.length(); i++) {
            num += pin.charAt(i) - '0';
        }
        for (int i = 0; i < generateType.length(); i++) {
            num += generateType.charAt(i) - '0';
        }
        return String.valueOf(num % 10);
    }

    public static String generateCardNumber(BankService bankService, String type) {
        String bin = generateBin();
        String pin = generatePin(bankService);
        String generateType = generateType(type);
        String checkNumber = generateCheckNumber(bin, pin, generateType);
        return bin + pin + generateType + checkNumber;
    }

    public static void openAccount(OpenAccountJFrame openAccountJFrame, BankService bankService) {
        String pin = generatePin(bankService);
        String type = openAccountJFrame.getCardTypeComboBox().getSelectedItem().toString();
        String cardNumber = generateCardNumber(bankService, type);
        String id = openAccountJFrame.getOwnerIdTextField().getText();
        String password = null;
        String confirmPassword = null;
        try {
            password = Encryption.encrypt(new String(openAccountJFrame.getPasswordTextField().getPassword()));
            confirmPassword = Encryption.encrypt(new String(openAccountJFrame.getConfirmPasswordTextField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(openAccountJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(openAccountJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(openAccountJFrame, "两次输入的密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!bankService.isRegister(id)) {
            JOptionPane.showMessageDialog(openAccountJFrame, "该用户未注册", "提示", JOptionPane.WARNING_MESSAGE);
        }
        if (!id.equals(Loginer.cardOwner.getId())) {
            JOptionPane.showMessageDialog(openAccountJFrame, "该身份证号不属于您,请本人来注册", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int res = 0;
        if (type.equals("借记卡")) {
            res = bankService.openAccount(cardNumber, id, password, type, new BigDecimal("0"));
        } else {
            res = bankService.openAccount(cardNumber, id, password, type, new BigDecimal("10000"));
        }
        if (res == 1) {
            JOptionPane.showMessageDialog(openAccountJFrame, "开户成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "开户", new BigDecimal(0), id, cardNumber);
            bankService.addBankInfo(bankInfo);
            String email = bankService.getEmail(id);
            if (email != null) {
                Mail.sendMail(email, "开卡成功", "您卡号" + cardNumber + "已成功开卡");
            }
        } else {
            JOptionPane.showMessageDialog(openAccountJFrame, "开户失败", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void withdraw(WithdrawJFrame withdrawJFrame, BankService bankService) {
        String cardNumber = withdrawJFrame.getCardNumberTextField().getText();

        String password = null;
        try {
            password = Encryption.encrypt(new String(withdrawJFrame.getPasswordField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(withdrawJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String amount = withdrawJFrame.getMoneyTextField().getText();
        String type = bankService.getCardTypeByCardNumber(cardNumber);
        if (cardNumber.isEmpty() || password.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(withdrawJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(withdrawJFrame, "请输入正确的金额", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cardOwnerId = bankService.getCardOwnerByCardNumber(cardNumber);
        if (cardOwnerId == null) {
            JOptionPane.showMessageDialog(withdrawJFrame, "该卡号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cardOwnerId.equals(Loginer.cardOwner.getId())) {
            JOptionPane.showMessageDialog(withdrawJFrame, "该卡号不属于您", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BigDecimal cardBalance = bankService.getCardBalance(cardNumber);
        if ((type.equals("借记卡") && new BigDecimal(amount).compareTo(cardBalance) > 0) || (type.equals("信用卡") && new BigDecimal(amount).compareTo(cardBalance.add(bankService.getCardLimitByCardNumber(cardNumber))) > 0)) {
            JOptionPane.showMessageDialog(withdrawJFrame, "余额不足", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.equals(bankService.getCardPassword(cardNumber))) {
            bankService.withdraw(cardNumber, amount);
            JOptionPane.showMessageDialog(withdrawJFrame, "取款成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "取款", new BigDecimal(amount), Loginer.cardOwner.getId(), cardNumber);
            bankService.addBankInfo(bankInfo);
            String email = bankService.getEmail(cardOwnerId);
            if (email != null) {
                Mail.sendMail(email, "取款成功", "您卡号" + cardNumber + "已成功取款" + amount + "元");
            }
        }
    }

    public static void transfer(TransferJFrame transferJFrame, BankService bankService) {
        String payerCardNumber = transferJFrame.getPayerCardNumberTextField().getText();
        String payeeCardNumber = transferJFrame.getPayeeCardNumberTextField().getText();
        String payerPassword = null;
        try {
            payerPassword = Encryption.encrypt(new String(transferJFrame.getPayerPasswordField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(transferJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String amount = transferJFrame.getAmountTextField().getText();
        String type = bankService.getCardTypeByCardNumber(payerCardNumber);
        if (payerPassword.isEmpty() || amount.isEmpty() || payerCardNumber.isEmpty() || payeeCardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(transferJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(transferJFrame, "请输入正确的金额", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BigDecimal cardBalance = bankService.getCardBalance(payerCardNumber);
        if ((type.equals("借记卡") && new BigDecimal(amount).compareTo(cardBalance) > 0) || (type.equals("信用卡") && new BigDecimal(amount).compareTo(cardBalance.add(bankService.getCardLimitByCardNumber(payerCardNumber))) > 0)) {
            JOptionPane.showMessageDialog(transferJFrame, "余额不足", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (bankService.getCardOwnerByCardNumber(payerCardNumber) == null) {
            JOptionPane.showMessageDialog(transferJFrame, "付款卡号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (bankService.getCardOwnerByCardNumber(payeeCardNumber) == null) {
            JOptionPane.showMessageDialog(transferJFrame, "收款卡号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!bankService.getCardOwnerByCardNumber(payerCardNumber).equals(Loginer.cardOwner.getId())) {
            JOptionPane.showMessageDialog(transferJFrame, "付款卡号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!payerPassword.equals(bankService.getCardPassword(payerCardNumber))) {
            JOptionPane.showMessageDialog(transferJFrame, "付款密码错误", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        bankService.transfer(payerCardNumber, payeeCardNumber, amount);
        JOptionPane.showMessageDialog(transferJFrame, "转账成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "转账", new BigDecimal(amount), Loginer.cardOwner.getId(), payerCardNumber);
        bankService.addBankInfo(bankInfo);
        bankInfo = new BankInfo(LocalDateTime.now(), "收账", new BigDecimal(amount), bankService.getCardOwnerByCardNumber(payeeCardNumber), payeeCardNumber);
        bankService.addBankInfo(bankInfo);
        String payerEmail = bankService.getEmail(bankService.getCardOwnerByCardNumber(payerCardNumber));
        if (payerEmail != null) {
            Mail.sendMail(payerEmail, "转账成功", "您卡号" + payerCardNumber + "已成功转账" + amount + "元");
        }
        String payeeEmail = bankService.getEmail(bankService.getCardOwnerByCardNumber(payeeCardNumber));
        if (payeeEmail != null) {
            Mail.sendMail(payeeEmail, "收账成功", "您卡号" + payeeCardNumber + "已成功收账" + amount + "元");
        }
    }

    public static void cancelAccount(CancelAccountJFrame cancelAccountJFrame, BankService bankService) {

        String cardNumber = cancelAccountJFrame.getCardNumberTextField().getText();
        String id = cancelAccountJFrame.getOwnerIdTextField().getText();
        String password = null;
        try {
            password = Encryption.encrypt(new String(cancelAccountJFrame.getPasswordTextField().getPassword()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "请正确输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BigDecimal cardBalance = bankService.getCardBalance(cardNumber);
        if (cardNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!id.equals(Loginer.cardOwner.getId())) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "这不是你的卡,如确定要销户,请联系管理员", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(bankService.getCardPassword(cardNumber))) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "密码错误", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cardBalance.compareTo(new BigDecimal(0)) > 0) {
            JOptionPane.showMessageDialog(cancelAccountJFrame, "请先将余额清零", "提示", JOptionPane.WARNING_MESSAGE);
            int result = JOptionPane.showConfirmDialog(cancelAccountJFrame, "是否以现金方式全部转出,共" + cardBalance);
            if (result == JOptionPane.YES_OPTION) {
                bankService.withdraw(cardNumber, cardBalance.toString());
                BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "取款", cardBalance, id, cardNumber);
                bankService.addBankInfo(bankInfo);
                JOptionPane.showMessageDialog(cancelAccountJFrame, "取款成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else return;
        }
        int choice = JOptionPane.showConfirmDialog(cancelAccountJFrame, "请再次确认是否删除该卡号" + cardNumber);
        if (choice == JOptionPane.YES_OPTION) {
            bankService.cancelAccount(cardNumber);
            JOptionPane.showMessageDialog(cancelAccountJFrame, "销户成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            BankInfo bankInfo = new BankInfo(LocalDateTime.now(), "销户", new BigDecimal("0"), Loginer.cardOwner.getId(), cardNumber);
            bankService.addBankInfo(bankInfo);
            String email = bankService.getEmail(id);
            if (email != null) {
                Mail.sendMail(email, "销卡成功", "您卡号" + cardNumber + "已成功销卡");
            }
        }


    }

    public static void setPayToOne(AddPayToOneJFrame addPayToOneJFrame, StuService stuService) {
        String stuId = addPayToOneJFrame.getIdTextField().getText();
        String amount = addPayToOneJFrame.getMoneyTextField().getText();
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(addPayToOneJFrame, "请输入正确的金额", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (stuId.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(addPayToOneJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (stuService.checkIsExist(stuId) == null) {
            JOptionPane.showMessageDialog(addPayToOneJFrame, "该学生不存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        stuService.setToBePaidToOne(stuId, amount);
        JOptionPane.showMessageDialog(addPayToOneJFrame, "设置成功", "提示", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void setPayToAll(AddPayToAllJFrame addPayToAllJFrame, StuService stuService) {
        String amount = addPayToAllJFrame.getMoneyTextField().getText();
        if (amount.isEmpty()) {
            JOptionPane.showMessageDialog(addPayToAllJFrame, "请输入正确的信息", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(addPayToAllJFrame, "请输入正确的金额", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        stuService.setToBePaidToAll(amount);
        JOptionPane.showMessageDialog(addPayToAllJFrame, "设置成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}


