package com.mumu.studentbankmanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mumu.studentbankmanagement.Util.TokenUtil;
import com.mumu.studentbankmanagement.model.*;
import com.mumu.studentbankmanagement.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController

public class BankController {
    @Autowired
    private BankService bankService;

    // 检验token
    @GetMapping("/api/checkToken")
    public JSONObject checkToken(HttpServletRequest request){
        JSONObject res=new JSONObject();
        System.out.println("前端访问页面验证token");
        String token = request.getHeader("token");
        System.out.println(token);
        res.put("msg",TokenUtil.checkToken(token));
        res.put("username",TokenUtil.getUserName(token));
        return res;
    }

    @PostMapping("/api/register")
    public JSONObject registerUser(@RequestBody Map<String, Object> paramForm) {
        return bankService.register(paramForm);
    }
    @PostMapping("/api/getVerificationCode")
    public JSONObject getVerificationCode(HttpServletRequest request,@RequestBody Map<String, Object> paramForm) {
        return bankService.getVerificationCode(paramForm,request.getHeader("token"));
    }
    @PostMapping("/api/login")
    public JSONObject loginUser(@RequestBody LoginParams loginParams) {
        return bankService.login(loginParams);

    }
    @GetMapping("/api/getCardList")
    public JSONArray getCardList(HttpServletRequest request){
        String token=request.getHeader("token");
        JSONArray res=new JSONArray();
        List<Card> cardList = bankService.getCardList(token);
        if(cardList==null){
            return null;
        }
        cardList.sort((o1, o2) ->o1.getLevel().compareTo(o2.getLevel()));

        for(Card card:cardList){
            JSONObject cardInfo=new JSONObject();
            cardInfo.put("id",card.getId());
            cardInfo.put("isFavorite",card.isFavorite());
            cardInfo.put("image",card.getImage());
            cardInfo.put("bank",card.getBank());
            cardInfo.put("type",card.getType());
            cardInfo.put("number",card.getNumber());
            JSONObject balance = new JSONObject();
            balance.put("currency", "人民币");
            balance.put("amount", card.getBalance());
            JSONArray balances=new JSONArray();
            balances.add(balance);
            cardInfo.put("balances",balances);
            cardInfo.put("level",card.getLevel());

//            System.out.println(card.getBalance());

            cardInfo.put("actions",new JSONArray(Arrays.asList(new String[]{"明细","缴费","汇款","理财","开户网点"})));
            res.add(cardInfo);
            System.out.println(card);
        }


        return  res;
    }
    @GetMapping("api/getUserInfo")
    public JSONObject getUserInfo(HttpServletRequest request){
        return bankService.getUserInfo(request.getHeader("token"));

    }
    @PostMapping("api/exit")
    public JSONObject exit(HttpServletRequest request){
        return bankService.exit(request.getHeader("token"));
    }
    @PostMapping("api/getCardDetails")
    public PageInfo<BankInfo>  getCardDetails(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){
        PageInfo<BankInfo> res = bankService.getCardDetails(request.getHeader("token"), paramForm);

        return res!=null?res:new PageInfo<>();
    }

    @PostMapping("/api/downloadCSV")
    public ResponseEntity<byte[]> downloadCSV(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) throws IOException {
        System.out.println(paramForm);
        return bankService.downloadCSV(paramForm,request.getHeader("token"));
        // 示例数据，可以根据实际情况生成或获取数据

    }
    @PostMapping("/api/downloadTXT")
    public ResponseEntity<byte[]>  downloadTXT(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) throws IOException {
        System.out.println(paramForm);
        return bankService.downloadTXT(paramForm,request.getHeader("token"));
        // 示例数据，可以根据实际情况生成或获取数据

    }
    @PostMapping("/api/transfer")
    public JSONObject transfer(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){
        return bankService.transfer(paramForm,request.getHeader("token"));
    }
    @PostMapping("/api/markDeposit")
    public JSONObject markDeposit(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){
        return bankService.markDeposit(paramForm,request.getHeader("token"));

    }
    @PostMapping("/api/markDraw")
    public JSONObject markDraw(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){
        return bankService.markDraw(paramForm,request.getHeader("token"));

    }
    @PostMapping("/api/openAccount")
    public JSONObject openAccount(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){
        return bankService.openAccount(paramForm,request.getHeader("token"));
    }

    @PostMapping("/api/getTypeNumber")
    public JSONObject getTypeNumber(@RequestBody Map<String, Object> paramForm,HttpServletRequest request){

        return bankService.getTypeNumber(paramForm,request.getHeader("token"));
    }
    @PostMapping("/api/getBalance")
    public JSONObject getBalance(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {
        return bankService.getBalance(paramForm, request.getHeader("token"));
    }
    @PostMapping("/api/uploadAvatar")
    public JSONObject uploadAvatar(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {
        return bankService.uploadAvatar(paramForm, request.getHeader("token"));
    }

    @PostMapping("/api/applyCreditCard")
    public JSONObject applyCreditCard(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.applyCreditCard(paramForm, request.getHeader("token"));
    }
    @PostMapping("/api/getApplyCardInfo")
    public PageInfo<CreditCardApplyInfo> getApplyingCardInfo(@RequestBody Map<String, Object> paramForm, HttpServletRequest request) {
        return bankService.getApplyingCardInfo(paramForm, request.getHeader("token"));
    }
    @PostMapping("/api/agreeApplyCard")
    public JSONObject agreeApplyCard(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.agreeApplyCard(paramForm, request.getHeader("token"));
    }
    @PostMapping("/api/refuseApplyCard")
    public JSONObject refuseApplyCard(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.refuseApplyCard(paramForm, request.getHeader("token"));
    }
    @GetMapping("/api/getCreditCardApplicationProgress")
    public JSONObject getCreditCardApplicationProgress(HttpServletRequest request) {

        return bankService.getCreditCardApplicationProgress( request.getHeader("token"));
    }
    @GetMapping("/api/getLoanAndRepayCard")
    public JSONObject getLoanAndRepayCard(HttpServletRequest request) {

        return bankService.getLoanAndRepayCard( request.getHeader("token"));
    }
    @PostMapping("/api/applyLoan")
    public JSONObject applyLoan(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.applyLoan(paramForm, request.getHeader("token"));
    }
    @GetMapping("/api/getLoanApplicationProgress")
    public JSONObject getLoanApplicationProgress(HttpServletRequest request) {

        return bankService.getLoanApplicationProgress(request.getHeader("token"));
    }
    @PostMapping("/api/getApplyLoanInfo")
    public PageInfo<LoanApplyInfo> getApplyLoanInfo(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.getApplyLoanInfo(paramForm, request.getHeader("token"));
    }

    @PostMapping("/api/agreeApplyLoan")
    public JSONObject agreeApplyLoan(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.agreeApplyLoan(paramForm, request.getHeader("token"));
    }
    @PostMapping("/api/refuseApplyLoan")
    public JSONObject refuseApplyLoan(@RequestBody Map<String, Object> paramForm,HttpServletRequest request) {

        return bankService.refuseApplyLoan(paramForm, request.getHeader("token"));
    }
}
