package com.mumu.studentbankmanagement;

import com.mumu.studentbankmanagement.frame.*;
import com.mumu.studentbankmanagement.model.Stu;
import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.javassist.compiler.ast.NewExpr;

import javax.swing.*;

public class JFrameFactory {
    public static void create(String frameName, int closeWay, JFrame parentComponent, Stu stu) {
        switch (frameName){
            case "DeleteStuJFrame"-> new DeleteStuJFrame(closeWay,parentComponent,stu).handle();
            case "StuInfoJFrame" ->new StuInfoJFrame(closeWay,parentComponent,stu).handle();
            case "UpdateStuJFrame" -> new UpdateStuJFrame(closeWay,parentComponent,stu).handle();
            default -> System.out.println("Frame not found");
        }
    }
    public static void create(String frameName, int closeWay, JFrame parentComponent) {
        switch (frameName){
            case "AddStuJFrame"-> new AddStuJFrame(closeWay,parentComponent).handle();
            case "MenuJFrame"-> new MenuJFrame(closeWay).handle();
            case "DeleteStuJFrame"-> new DeleteStuJFrame(closeWay,parentComponent).handle();
            case "SearchConfirmJFrame" -> new SearchConfirmJFrame(closeWay,parentComponent).handle();
            case "StuInfoListJFrame" ->new StuInfoListJFrame(closeWay,parentComponent).handle();
            case "StuLoginJFrame" ->new StuLoginJFrame(closeWay,parentComponent).handle();
            case "StuMainJFrame" ->new StuMainJFrame(closeWay,parentComponent).handle();
            case "StuPersonalCenterJFrame"-> new StuPersonalCenterJFrame(closeWay,parentComponent).handle();
            case "StuPasswordChangeJFrame"->new StuPasswordChangeJFrame(closeWay,parentComponent).handle();
            case "BankLoginJFrame"-> new BankLoginJFrame(closeWay,parentComponent).handle();
            case "BankRegisterJFrame" ->new BankRegisterJFrame(closeWay,parentComponent).handle();
            case "CardOwnerJFrame" ->new CardOwnerJFrame(closeWay,parentComponent).handle();
            case "CardOwnerInfoJFrame" ->new CardOwnerInfoJFrame(closeWay,parentComponent).handle();
            case "CardsJFrame" -> new CardsJFrame(closeWay,parentComponent).handle();
            case "StuToolsJFrame" -> new StuToolsJFrame(closeWay,parentComponent).handle();
            case "PicToPdfJFrame" -> new PicToPdfJFrame(closeWay,parentComponent).handle();
            case "DepositJFrame"->new DepositJFrame(closeWay,parentComponent).handle();
            case "OpenAccountJFrame" ->new OpenAccountJFrame(closeWay,parentComponent).handle();
            default -> System.out.println("Frame not found");
        }
    }

}
