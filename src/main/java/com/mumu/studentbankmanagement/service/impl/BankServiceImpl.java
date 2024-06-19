package com.mumu.studentbankmanagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mumu.studentbankmanagement.Builder.CardBuilder;
import com.mumu.studentbankmanagement.Encryption;
import com.mumu.studentbankmanagement.Mail;
import com.mumu.studentbankmanagement.Util.CheckPasswordIsValidUtil;
import com.mumu.studentbankmanagement.Util.MapUtil;
import com.mumu.studentbankmanagement.Util.StringUtil;
import com.mumu.studentbankmanagement.Util.TokenUtil;
import com.mumu.studentbankmanagement.frame.ConfigJFrame;
import com.mumu.studentbankmanagement.mapper.BankMapper;
import com.mumu.studentbankmanagement.model.*;
import com.mumu.studentbankmanagement.service.BankService;
import jakarta.annotation.PostConstruct;
import net.sf.json.util.NewBeanInstanceStrategy;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankMapper bankMapper;
    public  String generatePin() {
        String pin;
        BigInteger add = new BigInteger(String.valueOf(bankMapper.getAllCardsCountFromCard())).add(new BigInteger("456324896414")).remainder(new BigInteger("1000000000001"));
        pin = add.toString();
        return pin;
    }

    public  String generateBin() {
        return "650327";
    }

    public  String generateType(String type) {
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

public  String generateCheckNumber(String bin, String pin, String generateType) {
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

public  String generateCardNumber( String type) {
    String bin = generateBin();
    String pin = generatePin();
    String generateType = generateType(type);
    String checkNumber = generateCheckNumber(bin, pin, generateType);
    return bin + pin + generateType + checkNumber;
}
@PostConstruct
public void init() {
    ConfigJFrame.set(this);
}

    @Override
    public JSONObject register(Map<String, Object> paramForm) {
        JSONObject res = new JSONObject();
        String msg = "";
        String id = (String) paramForm.get("id");
        String password = (String) paramForm.get("password");
        String email = (String) paramForm.get("email");
        String name = (String)paramForm.get("name") ;
        String encrypt = "";
        String verificationCode=(String)paramForm.get("verificationCode");

        if (!id.matches("(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}X)$)")) {

            msg = "请输入正确的身份证!";
            res.put("msg", msg);
            return res;
        }
        if(!verificationCode.equals(bankMapper.getVerificationCode(email))){
            msg = "验证码错误!";
            res.put("msg", msg);
            return res;
        }
        if(!CheckPasswordIsValidUtil.checkPasswordStringWithBitmap(password)){
            msg = "密码不符合要求!";
            res.put("msg", msg);
            return res;
        }

        try {
            encrypt = Encryption.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
            msg="出现异常";
            res.put("msg", msg);
            return res;
        }
        if (bankMapper.exist(id) == 1) {
            msg="注册失败,已存在该用户";
            res.put("msg", msg);
            return res;
        }
        bankMapper.register(id, name, encrypt, email,2);
        msg="注册成功";
        res.put("msg", msg);
        new Thread(()->Mail.sendMail(email, "注册成功", "恭喜您注册成功")).start();
        return res;
    }

    @Override
    public JSONObject login(LoginParams loginParams) {
        boolean isAdmin=false;
        String id = loginParams.getId();
        String password = loginParams.getPassword();
        JSONObject res = new JSONObject();
        try {
            password = Encryption.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "出现异常");
            return res;
        }
        CardOwner login = bankMapper.login(id, password);
        if(login==null){
            login = bankMapper.loginAdmin(id,password);
            isAdmin=true;
            if(login==null) {
                res.put("msg", "登录失败");
                return res;
            }
        }

            String email = login.getEmail();
            String content = "您的账号已";
            System.out.println(loginParams.getLongitude());
            System.out.println(loginParams.getLatitude());
            String[] provinceCity = MapUtil.reverseGeocode(Double.parseDouble(loginParams.getLongitude()), Double.parseDouble(loginParams.getLatitude()));
            content = !provinceCity[0].equals("[]") ? content + "在" + provinceCity[0] : content;
            content = !provinceCity[1].equals("[]") ? content + provinceCity[1] : content;
            content += "登录";
            String finalContent = content;

            new Thread(() ->{
                if (!email.isEmpty()) {
                    Mail.sendMail(email, "登陆成功", finalContent);
                } else {
                    System.out.println("邮箱为空");
                }
            }).start();

            CardOwner cardOwner = login;
            System.out.println(cardOwner.getId());
            String token = "";

            token = TokenUtil.createToken(cardOwner);
            res.put("token", token);
            res.put("msg", "登录成功");
            if(isAdmin){
                res.put("msg","管理员登录成功");
            }
            res.put("name", cardOwner.getName());
            return res;

    }

    @Override
    public JSONObject exit(String token) {
        JSONObject res=new JSONObject();
        int num=bankMapper.updateLastLoginTime(TokenUtil.getCardOwnerId(token), LocalDateTime.now());
        System.out.println(TokenUtil.getCardOwnerId(token));
        if(num>0){
            res.put("msg","成功");
        }
        else res.put("msg","失败");
        return res;
    }

    @Override
    public PageInfo<BankInfo> getCardDetails(String token, Map<String, Object> paramForm) {
        JSONArray res = new JSONArray();

        String start = (String) (paramForm.get("start"));
        String end=(String)(paramForm.get("end"));
        String registeredAccount=(String)(paramForm.get("registeredAccount"));
        String category=(String)(paramForm.get("category"));

        int pageNum=(int) (paramForm.get("pageNum"));
        int pageSize=(int) (paramForm.get("pageSize"));

        PageHelper.startPage(pageNum,pageSize);

        List<BankInfo> infos=bankMapper.getCardDetails(start,end,registeredAccount,category.equals("全部")?null:category);

        return new PageInfo<>(infos);
//        for (int i = 0; i < infos.size(); i++) {
//            JSONObject info=new JSONObject();
//            info.put("cardNumber",infos.get(i).getCardNumber());
//            info.put("time",infos.get(i).getTime());
//            info.put("type",infos.get(i).getType());
//            info.put("amount",infos.get(i).getAmount());
//            info.put("balance",infos.get(i).getBalance());
//            res.add(info);
//        }
//        return res;

    }

    @Override
    public ResponseEntity<byte[]> downloadCSV(Map<String, Object> paramForm, String token) {

        System.out.println(paramForm);
        List<String> header = Arrays.asList("time", "type", "amount","card_number","balance");
        List<BankInfo> cardDetails = bankMapper.getCardDetails((String) paramForm.get("start"), (String) paramForm.get("end"), (String) paramForm.get("registeredAccount"), (String) paramForm.get("category"));

        List<List<String>> data = cardDetails.stream()
                .map(cardDetail -> Arrays.asList(
                        cardDetail.getTime(),
                        cardDetail.getType(),
                        cardDetail.getAmount(),
                        StringUtil.markString(cardDetail.getCardNumber(),7,8),
                        cardDetail.getBalance()
                        )).map(list->list.stream().map(Object::toString).collect(Collectors.toList())).toList();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(String.join(",", header).getBytes(StandardCharsets.UTF_8));
            outputStream.write("\n".getBytes(StandardCharsets.UTF_8));

            for (List<String> row : data) {
                outputStream.write(String.join(",", row).getBytes(StandardCharsets.UTF_8));
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }

        }catch (IOException e){
            return null;
        }
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @Override

//    @Transactional
    public JSONObject transfer(Map<String, Object> paramForm, String token) {
        JSONObject res = new JSONObject();
        String payerCardNumber = (String) paramForm.get("payerCardNumber");
        String payeeCardNumber=(String)paramForm.get("payeeCardNumber");
        BigDecimal amount = new BigDecimal((String) paramForm.get("amount")) ;
        String name=(String) paramForm.get("name");
        String password=(String) paramForm.get("password");
       try{
           password=Encryption.encrypt(password);
       }catch (Exception e){
             res.put("code",400);
               res.put("msg","密码异常");
               return res;
       }
        String exchangeMethod=(String) paramForm.get("exchangeMethod");

        String payeeName=bankMapper.getCardName(payeeCardNumber);
        System.out.println(payeeName);


        System.out.println(payeeName);
        if(payeeName==null){
            res.put("code",400);
            res.put("msg","收款人卡号不存在");
            return res;
        }
        else if(!payeeName.equals(name)){
            res.put("code",400);
            res.put("msg","收款人姓名卡号不匹配");
            return res;
        }
        else if(amount.compareTo(bankMapper.getCardBalanceByCardNumber(payerCardNumber))>0){
            res.put("code",400);
            res.put("msg","余额不足");
            return res;
        }
        else if(!password.equals(bankMapper.getCardPasswordByCardNumber(payerCardNumber))){
            res.put("code",400);
            res.put("msg","密码错误");
            return res;
        }
        else{
            System.out.println(amount);
            System.out.println(bankMapper.getCardBalance(payerCardNumber));
            System.out.println(amount.compareTo(bankMapper.getCardBalance(payerCardNumber)));
            bankMapper.withdrawCard(payerCardNumber,amount);
            bankMapper.depositCard(payeeCardNumber,amount);
           new Thread(()->{
               Mail.sendMail(bankMapper.getEmailByCardNumber(payerCardNumber),"转账成功","尊敬的"+bankMapper.getCardName(payerCardNumber)+"您好，您卡号尾号为"+payerCardNumber.substring(payerCardNumber.length()-4)+"已成功转账"+amount+"元到"+bankMapper.getCardName(payeeCardNumber)+"的账户，请注意查收");
               Mail.sendMail(bankMapper.getEmailByCardNumber(payeeCardNumber),"转账成功","尊敬的"+bankMapper.getCardName(payeeCardNumber)+"您好，您卡号尾号为"+payeeCardNumber.substring(payeeCardNumber.length()-4)+"已成功收到来自卡号尾号为"+payerCardNumber.substring(payerCardNumber.length()-4)+"的"+amount+"元转账，请注意查收");

           }).start();
           new Thread(()->{
               BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"转账",amount.negate(),bankMapper.getCardOwnerIdByCardNumber(payerCardNumber),payerCardNumber,bankMapper.getCardBalanceByCardNumber(payerCardNumber));
               bankMapper.addBankInfo(bankInfo);
               bankInfo=new BankInfo(LocalDateTime.now(),"收款",amount,bankMapper.getCardOwnerIdByCardNumber(payeeCardNumber),payeeCardNumber,bankMapper.getCardBalanceByCardNumber(payeeCardNumber));
               bankMapper.addBankInfo(bankInfo);
           }).start();
            res.put("code",200);
            res.put("msg","转账成功");
            return res;
        }

    }

    @Override
    public JSONObject markDeposit(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        String number=(String) paramForm.get("number");
        BigDecimal amount=new BigDecimal((String) paramForm.get("amount"));
        String password=(String) paramForm.get("password");
        String name=(String) paramForm.get("name");
        try {
            password=Encryption.encrypt(password);
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","发生错误");
            return res;
        }
        String checkName=bankMapper.getCardName(number);
        if(checkName==null){
            res.put("code",400);
            res.put("msg","卡号不存在");
            return res;
        }
        if(!name.equals(checkName)){
            res.put("code",400);
            res.put("msg","卡号与姓名不匹配");
            return res;
        }
        if(!password.equals(bankMapper.getCardPasswordByCardNumber(number))){
            res.put("code",400);
            res.put("msg","密码错误");
            return res;
        }
        bankMapper.markDeposit(number,amount);
        new Thread(()->{
            Mail.sendMail(bankMapper.getEmailByCardNumber(number),"存款成功","尊敬的"+bankMapper.getCardName(number)+"您好，您卡号尾号为"+number.substring(number.length()-4)+"已成功存款"+amount+"元，请注意查收");
        }).start();
        new Thread(()->{
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"存款",amount,bankMapper.getCardOwnerIdByCardNumber(number),number,bankMapper.getCardBalanceByCardNumber(number));
            bankMapper.addBankInfo(bankInfo);
        }).start();
        res.put("code",200);
        res.put("msg","存款成功");
        return res;
    }

    @Override
    public JSONObject markDraw(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        String number=(String) paramForm.get("number");
        BigDecimal amount=new BigDecimal((String) paramForm.get("amount"));
        String password=(String) paramForm.get("password");
        String name=(String) paramForm.get("name");
        try {
            password=Encryption.encrypt(password);
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","发生错误");
            return res;
        }
        String checkName=bankMapper.getCardName(number);
        if(checkName==null){
            res.put("code",400);
            res.put("msg","卡号不存在");
            return res;
        }
        if(!name.equals(checkName)){
            res.put("code",400);
            res.put("msg","卡号与姓名不匹配");
            return res;
        }
        if(!password.equals(bankMapper.getCardPasswordByCardNumber(number))){
            res.put("code",400);
            res.put("msg","密码错误");
            return res;
        }
        if(amount.compareTo(bankMapper.getCardBalanceByCardNumber(number))>0){
            res.put("code",400);
            res.put("msg","余额不足");
            return res;
        }
        bankMapper.markDraw(number,amount);
        new Thread(()->{
            Mail.sendMail(bankMapper.getEmailByCardNumber(number),"取款成功","尊敬的"+bankMapper.getCardName(number)+"您好，您卡号尾号为"+number.substring(number.length()-4)+"已成功取款"+amount+"元，请注意查收");
        }).start();
        new Thread(()->{
            BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"取款",amount.negate(),bankMapper.getCardOwnerIdByCardNumber(number),number,bankMapper.getCardBalanceByCardNumber(number));
            bankMapper.addBankInfo(bankInfo);
        }).start();
        res.put("code",200);
        res.put("msg","取款成功");
        return res;
    }

    @Override
    public JSONObject openAccount(Map<String, Object> paramForm, String token) {
        JSONObject res = new JSONObject();
        String name = (String) paramForm.get("name");
        String password = (String) paramForm.get("password");
        String id = (String) paramForm.get("id");
        String checkPassword = (String) paramForm.get("checkPassword");
        String cardOwnerPassword= (String) paramForm.get("cardOwnerPassword");
        String level= (String) paramForm.get("type");

        try {
            cardOwnerPassword=Encryption.encrypt(cardOwnerPassword);
//            System.out.println(cardOwnerPassword);
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","出现异常");
            return res;
        }
        System.out.println(cardOwnerPassword+"...2");
        System.out.println(bankMapper.getCardOwnerPasswordByCardOwnerId(id)+"...3");
        System.out.println(id);
        if(!bankMapper.getCardOwnerPasswordByCardOwnerId(id).equals(cardOwnerPassword)){
            res.put("code",400);
            res.put("msg","登录密码错误");
            return res;
        }
        if(!password.equals(checkPassword)){
            res.put("code",400);
            res.put("msg","两次密码不一致");
            return res;
        }
       if(!bankMapper.isCardOwnerExist(id,name)){
            res.put("code",400);
            res.put("msg","身份证号或姓名不存在");
            return res;
       }
       int cardDetailLevelNumber=bankMapper.getDetailCardLevelNumber(id,level);
       if(level.equals("1")&&cardDetailLevelNumber>0){
            res.put("code",400);
            res.put("msg","I类卡最多一张");
            return res;
       }
       if(level.equals("2")&&cardDetailLevelNumber>=5){
           res.put("code",400);
           res.put("msg","II类卡最多5张");
           return res;
       }

       if(level.equals("3")&&cardDetailLevelNumber>=5){
           res.put("code",400);
           res.put("msg","III类卡最多5张");
           return res;
       }
       try {
           password=Encryption.encrypt(password);
       }catch (Exception e){
             res.put("code",400);
           res.put("msg","出现异常");
           return res;
       }
        System.out.println(level);
       Card card=new CardBuilder().setOwnerId(id).setFavorite(false).setImage("1").setBank("工商银行").setType("借记卡").setBalance(new BigDecimal("0")).setPassword(password).setLevel(level).build();
       card.setNumber(generateCardNumber(card.getType()));
       bankMapper.addCard(card);

       new Thread(()->{
           BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"开户",new BigDecimal("0"),id,card.getNumber(),new BigDecimal("0"));
           bankMapper.addBankInfo(bankInfo);
       }).start();
       new Thread(()->{
           Mail.sendMail(bankMapper.getEmail(id),"开户成功","恭喜您开户成功，您的卡号为："+card.getNumber());
       }).start();
       res.put("code",200);
       res.put("msg","开户成功");
       return res;
    }

    @Override
    public JSONObject getTypeNumber(Map<String, Object> paramForm, String token) {
        int number = bankMapper.getDetailCardLevelNumber(TokenUtil.getCardOwnerId(token), (String) paramForm.get("type"));
        JSONObject res=new JSONObject();
        res.put("code",200);
        res.put("number",number);
        return res;
    }

    @Override
    public JSONObject getVerificationCode(Map<String, Object> paramForm,String token) {
        JSONObject res=new JSONObject();
        String email=(String) paramForm.get("email");
        String msg=(String) paramForm.get("msg");
        if(!(msg==null)){
            if(msg.equals("信用卡申请验证码")||msg.equals("信用卡贷款申请验证码")){

                if(!bankMapper.getEmail(TokenUtil.getCardOwnerId(token)).equals(email)){
                    res.put("code",400);
                    res.put("msg","邮箱不匹配");
                    return res;
                }
            }
        }

        String code=StringUtil.generateRandomCode(6);
        new Thread(()-> Mail.sendMail(email,"验证码","您的验证码为："+code)).start();
        System.out.println(email);
        System.out.println("发送成功");
        if(bankMapper.isEmailCodeExistEmail(email)){
            bankMapper.updateVerificationCode(email,code);
        }
       else{
            bankMapper.insertVerificationCode(email,code);
        }
        res.put("code",200);
        res.put("msg","发送成功");
        return res;
    }

    @Override
    public JSONObject getBalance(Map<String, Object> paramForm, String token) {
        String cardNumber=(String) paramForm.get("cardNumber");
        JSONObject res=new JSONObject();
        if(!bankMapper.isCardNumberExist(cardNumber)){
            res.put("code",400);
            res.put("msg","卡号不存在");
            return res;
        }
        else{
            res.put("code",200);
            res.put("balance",bankMapper.getCardBalanceByCardNumber(cardNumber));
            return res;
        }
    }

    @Override
    public JSONObject uploadAvatar(Map<String, Object> paramForm, String token) {
        String image=(String) paramForm.get("image");
        String id=TokenUtil.getCardOwnerId(token);
        JSONObject res=new JSONObject();
        bankMapper.uploadAvatar(image,id);
        res.put("code",200);
        res.put("msg","上传成功");
        return res;
    }

    @Override
    public JSONObject applyCreditCard(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
       String id=(String) paramForm.get("id");
       String name=(String) paramForm.get("name");
       String password=(String) paramForm.get("password");
       String checkPassword=(String) paramForm.get("checkPassword");
       String cardOwnerPassword=(String) paramForm.get("cardOwnerPassword");
        String verificationCode=(String) paramForm.get("verificationCode");

       if(!id.equals(TokenUtil.getCardOwnerId(token))){
             res.put("code",400);
               res.put("msg","身份证错误");
               return res;
       }
       if(!password.equals(checkPassword)){
               res.put("code",400);
               res.put("msg","两次密码不一致");
               return res;
       }
       if(!name.equals(TokenUtil.getUserName(token))){
           res.put("code",400);
             res.put("msg","姓名错误");
             return res;
       }
       if(!verificationCode.equals(bankMapper.getVerificationCode(bankMapper.getEmail(id)))){
             res.put("code",400);
             res.put("msg","验证码错误");
             return res;
       }
       try{
            cardOwnerPassword = Encryption.encrypt(cardOwnerPassword);
            password = Encryption.encrypt(password);
       }catch(Exception e){
           res.put("code",400);
           res.put("msg","发生异常");
           return res;
        }
       if(!cardOwnerPassword.equals(bankMapper.getCardOwnerPasswordByCardOwnerId(id))){
             res.put("code",400);
               res.put("msg","密码错误");
               return res;
       }
       if(bankMapper.getCreditCardCount(id)!=0){
             res.put("code",400);
             res.put("msg","您已申请过信用卡");
             return res;
       }
       if(bankMapper.getCreditCardApplyCount(id)!=0){
           if (bankMapper.getCreditCardApplicationProgress(id).equals("审核中")) {
               res.put("code",400);
               res.put("msg","您正在申请信用卡");
               return res;
           }
           else{
               bankMapper.deleteCreditCardApplyInfo(id);
           }

       }

       bankMapper.applyCreditCard(id,name,password,LocalDateTime.now(),"审核中");

       new Thread(()-> Mail.sendMail(bankMapper.getEmail(id),"信用卡申请","您的信用卡已提交申请"));

       res.put("code",200);
       res.put("msg","已提交申请");
       return res;
    }

    @Override
    public PageInfo<CreditCardApplyInfo> getApplyingCardInfo(Map<String, Object> paramForm, String token) {





        int pageNum=(int) (paramForm.get("pageNum"));
        int pageSize=(int) (paramForm.get("pageSize"));

        PageHelper.startPage(pageNum,pageSize);

        List<CreditCardApplyInfo> infos=bankMapper.getApplyingCardInfo();

        return new PageInfo<>(infos);
    }

    @Override
    public JSONObject agreeApplyCard(Map<String, Object> paramForm, String token) {

        JSONObject res=new JSONObject();
        String cardOwnerId=(String) paramForm.get("cardOwnerId");
        String cardPassword=(String) paramForm.get("cardPassword");

        String cardNumber=generateCardNumber("信用卡");

        Card card=new CardBuilder().setNumber(cardNumber).setOwnerId(cardOwnerId).setType("信用卡").setPassword(cardPassword).setLevel("0").build();
        bankMapper.addCard(card);
        BankInfo bankInfo=new BankInfo(LocalDateTime.now(),"开户",new BigDecimal("0"),cardOwnerId,cardNumber);
        bankMapper.addBankInfo(bankInfo);
        new Thread(()-> Mail.sendMail(bankMapper.getEmail(cardOwnerId),"信用卡申请","您的信用卡已通过申请")).start();
        bankMapper.modifyApplyCreditInfo(cardOwnerId,"已通过申请");
        res.put("code",200);
        res.put("msg","已通过申请");
        return res;
    }

    @Override
    public JSONObject refuseApplyCard(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        String cardOwnerId=(String) paramForm.get("cardOwnerId");
        bankMapper.modifyApplyCreditInfo(cardOwnerId,"未通过申请");
        new Thread(()-> Mail.sendMail(bankMapper.getEmail(cardOwnerId),"信用卡申请","您的信用卡未通过申请")).start();
        res.put("code",200);
        res.put("msg","已拒绝申请");
        return res;
    }

    @Override
    public JSONObject getCreditCardApplicationProgress( String token) {
        JSONObject res=new JSONObject();
        String cardOwnerId= TokenUtil.getCardOwnerId(token);
        String creditCardApplicationProgress = bankMapper.getCreditCardApplicationProgress(cardOwnerId);
        if(creditCardApplicationProgress==null){
            creditCardApplicationProgress="未申请";
        }
        else if(creditCardApplicationProgress.equals("审核中")){

        }
       else{
            creditCardApplicationProgress=creditCardApplicationProgress.equals("已通过申请")?"成功":"失败";
        }
        res.put("code",200);
        res.put("status",creditCardApplicationProgress);
        return res;
    }

    @Override
    public JSONObject getLoanAndRepayCard(String token) {
        JSONObject res=new JSONObject();
        String cardOwnerId= TokenUtil.getCardOwnerId(token);
        String loanCardNumber=bankMapper.getCreditCardNumber(cardOwnerId);
        String repayCardNumber=bankMapper.getRepayCardNumber(cardOwnerId);

        res.put("code",200);
        res.put("loanCardNumber",loanCardNumber);
        res.put("repayCardNumber",repayCardNumber);
        return res;
    }

    @Override
    public JSONObject applyLoan(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        String name=(String) paramForm.get("name");
        String id=(String) paramForm.get("id");
        String password=(String) paramForm.get("password");
        String cardOwnerPassword=(String) paramForm.get("cardOwnerPassword");
        String loanCardNumber=(String) paramForm.get("loanCardNumber");
        String repayCardNumber=(String) paramForm.get("repayCardNumber");
        String email=(String) paramForm.get("email");
        String verificationCode=(String) paramForm.get("verificationCode");
        BigDecimal loanAmount;
        int loanMonth;
        try {
            loanAmount=new BigDecimal((String) paramForm.get("loanAmount"));
            loanMonth= Integer.parseInt((String) paramForm.get("loanMonth"));
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","贷款金额或贷款期限错误");
            return res;
        }
        try{
            password=Encryption.encrypt(password);
            cardOwnerPassword=Encryption.encrypt(cardOwnerPassword);
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","出现异常");
            return res;
        }
        String loanType=(String) paramForm.get("loanType");

        if(!name.equals(TokenUtil.getUserName(token))){
            res.put("code",400);
            res.put("msg","姓名错误");
            return res;
        }
        if(!id.equals(TokenUtil.getCardOwnerId(token))){
            res.put("code",400);
            res.put("msg","身份证号错误");
            return res;
        }
        if(!loanCardNumber.equals(bankMapper.getCreditCardNumber(id))){
            res.put("code",400);
            res.put("msg","信用卡号错误");
            return res;
        }
        if(!repayCardNumber.equals(bankMapper.getRepayCardNumber(id))){
            res.put("code",400);
            res.put("msg","还款卡号错误");
            return res;
        }

        if(!password.equals(bankMapper.getCardPasswordByCardNumber(loanCardNumber))){
            res.put("code",400);
            res.put("msg","信用卡密码错误");
            return res;
        }
        if(!cardOwnerPassword.equals(bankMapper.getCardOwnerPasswordByCardOwnerId(id))){
            res.put("code",400);
            res.put("msg","登录密码错误");
            return res;
        }
        if(!verificationCode.equals(bankMapper.getVerificationCode(email))){
            res.put("code",400);
            res.put("msg","验证码错误");
            return res;
        }
        if(bankMapper.getLoanApplyCount(id)>0){
            res.put("code",400);
            res.put("msg","您已申请过贷款");

            return res;

        }
        bankMapper.deleteApplyLoanInfo(id);
        bankMapper.applyLoan(id,name,loanAmount,loanMonth,loanType,repayCardNumber,loanCardNumber,"审核中");
        res.put("code",200);
        res.put("msg","贷款申请中");
        new Thread(()->Mail.sendMail(email,"贷款申请","您的贷款申请已提交，请等待审核"));
        return res;
    }

    @Override
    public JSONObject getLoanApplicationProgress( String token) {
        String id=TokenUtil.getCardOwnerId(token);
        JSONObject res=new JSONObject();
        String status=bankMapper.getLoanApplicationProgress(id);
        if(status==null){
            status="未申请";
        }

        else if(status.equals("未通过申请")){
            status="失败";
        }
        else if(status.equals("已通过申请")){
            status="成功";
        }
        res.put("code",200);
        res.put("status",status);
        return res;
    }

    @Override
    public PageInfo<LoanApplyInfo> getApplyLoanInfo(Map<String, Object> paramForm, String token) {

        int pageNum=(int) (paramForm.get("pageNum"));
        int pageSize=(int) (paramForm.get("pageSize"));

        PageHelper.startPage(pageNum,pageSize);

        List<LoanApplyInfo> infos=bankMapper.getApplyingLoanInfo();

        return new PageInfo<>(infos);
    }

    @Override
    public JSONObject agreeApplyLoan(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        BigDecimal loanAmount;
        int loanMonth;
        try {
            loanAmount=new BigDecimal((String) paramForm.get("loanAmount"));
            loanMonth=Integer.parseInt((String) paramForm.get("loanMonth"));
        }catch(Exception e){
            res.put("code",400);
            res.put("msg","贷款金额或贷款期限格式错误");
            return res;
        }
       String loanType=(String) paramForm.get("loanType");
       String repayCardNumber=(String) paramForm.get("repayCardNumber");
       String loanCardNumber=(String) paramForm.get("loanCardNumber");
       BigDecimal interestRate = new BigDecimal("0.0435");
       LocalDateTime loanTime = LocalDateTime.now();
       BigDecimal currentLoan=new BigDecimal("0.00");
       BigDecimal currentToBePaidMoney=new BigDecimal("0.00");
       LocalDateTime nextPayTime=loanTime.plusMonths(1);
       bankMapper.agreeApplyLoan(loanCardNumber,loanAmount,loanMonth,loanTime,currentLoan,currentToBePaidMoney,nextPayTime,repayCardNumber,interestRate,loanType);
       bankMapper.modifyApplyLoanInfo(loanCardNumber,"已通过申请");
       res.put("code",200);
       res.put("msg","贷款申请已通过");
       new Thread(()->Mail.sendMail((String) paramForm.get("email"),"贷款申请","您的贷款申请已通过，请及时还款")).start();
       return res;

    }

    @Override
    public JSONObject refuseApplyLoan(Map<String, Object> paramForm, String token) {
        JSONObject res=new JSONObject();
        String loanCardNumber=(String) paramForm.get("loanCardNumber");
        bankMapper.modifyApplyLoanInfo(loanCardNumber,"未通过申请");
        res.put("code",200);
        res.put("msg","贷款申请已拒绝");
        new Thread(()->Mail.sendMail((String) paramForm.get("email"),"贷款申请","您的贷款申请未通过")).start();
        return res;
    }


    @Override
    public ResponseEntity<byte[]> downloadTXT(Map<String, Object> paramForm, String token) {

        System.out.println(paramForm);
        List<String> header = Arrays.asList("time", "type", "amount","card_number","balance");
        List<BankInfo> cardDetails = bankMapper.getCardDetails((String) paramForm.get("start"), (String) paramForm.get("end"), (String) paramForm.get("registeredAccount"), (String) paramForm.get("category"));

        List<List<String>> data = cardDetails.stream()
                .map(cardDetail -> Arrays.asList(
                        cardDetail.getTime(),
                        cardDetail.getType(),
                        cardDetail.getAmount(),
                        StringUtil.markString(cardDetail.getCardNumber(),7,8),
                        cardDetail.getBalance()
                )).map(list->list.stream().map(Object::toString).collect(Collectors.toList())).toList();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(String.join(",", header).getBytes(StandardCharsets.UTF_8));
            outputStream.write("\n".getBytes(StandardCharsets.UTF_8));

            for (List<String> row : data) {
                outputStream.write(String.join(",", row).getBytes(StandardCharsets.UTF_8));
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }

        }catch (IOException e){
            return null;
        }
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "data.txt");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }





    //以下是swing中用到的
    @Override
    public CardOwner login(String Id, String Pwd) {
        return bankMapper.login(Id, Pwd);
    }

    @Override
    public int registerCardOwner(CardOwner cardOwner) {
        return bankMapper.registerCardOwner(cardOwner);
    }

    @Override
    public int getCardNumber(String id) {
        return bankMapper.getCardNumber(id);
    }

    @Override
    public DebitCard[] getCards(String id) {
        return bankMapper.getCards(id);
    }

    @Override
    public String getCardOwnerByCardNumber(String cardNumber) {
        return bankMapper.getCardOwnerByCardNumber(cardNumber);
    }

    @Override
    public String getCardPassword(String cardNumber) {
        return bankMapper.getCardPassword(cardNumber);
    }

    @Override
    public void deposit(String cardNumber, String amount) {
        bankMapper.deposit(cardNumber, amount);
    }

    @Override
    public boolean isRegister(String id) {
        return bankMapper.isRegister(id);
    }

    @Override
    public int openAccount(String cardNumber, String id, String password, String type, BigDecimal limit) {
        return bankMapper.openAccount(cardNumber, id, password, type, limit);
    }

    @Override
    public void withdraw(String cardNumber, String amount) {
        bankMapper.withdraw(cardNumber, amount);
    }

    @Override
    public BigDecimal getCardBalance(String cardNumber) {
        return bankMapper.getCardBalance(cardNumber);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void transfer(String payerCardNumber, String payeeCardNumber, String amount) {
        bankMapper.deposit(payeeCardNumber, amount);
        bankMapper.withdraw(payerCardNumber, amount);
    }

    @Override
    public void cancelAccount(String cardNumber) {
        bankMapper.cancelAccount(cardNumber);
    }

    @Override
    public void addBankInfo(BankInfo bankInfo) {
        bankMapper.addBankInfo(bankInfo);
    }

    @Override
    public List<BankInfo> getBankInfosByOwnerId(String id) {
        return bankMapper.getBankInfosByOwnerId(id);
    }

    @Override
    public String getCardTypeByCardNumber(String cardNumber) {
        return bankMapper.getCardTypeByCardNumber(cardNumber);
    }

    @Override
    public BigDecimal getCardLimitByCardNumber(String cardNumber) {
        return bankMapper.getCardLimitByCardNumber(cardNumber);
    }

    @Override
    public int getAllCardsCount() {
        return bankMapper.getAllCardsCount();
    }

    @Override
    public int loginByCardOwner(CardOwner cardOwner) {
        return bankMapper.loginByCardOwner(cardOwner);
    }

    @Override
    public String getEmail(String cardNumber) {
        return bankMapper.getEmail(cardNumber);
    }

    @Override
    public BankManager loginByBankManager(String id, String password) {
        return bankMapper.loginByBankManager(id, password);
    }

    @Override
    public List<CardOwner> getAllCardsOwner() {
        return bankMapper.getAllCardsOwner();
    }

    @Override
    public List<Card> getCardList(String token) {
        if (TokenUtil.checkToken(token)) {
            System.out.println("123");
            System.out.println(TokenUtil.getCardOwnerId(token));

            List<Card> cardList = bankMapper.getCardList(TokenUtil.getCardOwnerId(token));
            System.out.println("cardListSize"+cardList.size());
            return cardList;
        } else {
            return null;
        }
    }

    @Override
    public JSONObject getUserInfo(String token) {
        JSONObject res = new JSONObject();
        if (TokenUtil.checkToken(token)) {

            CardOwner cardOwner = bankMapper.getUserInfo(TokenUtil.getCardOwnerId(token));
            if (cardOwner == null) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd号HH:mm:ss");
            res.put("level", cardOwner.getLevel());
            res.put("lastLogin", cardOwner.getLastLogin().format(formatter));
            res.put("points", cardOwner.getPoints());
            res.put("image", cardOwner.getAvatar());
            return res;
        }
        res.put("msg", "登录信息过期");
        return res;

    }




}
