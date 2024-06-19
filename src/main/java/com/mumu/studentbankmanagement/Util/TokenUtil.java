package com.mumu.studentbankmanagement.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mumu.studentbankmanagement.model.CardOwner;

import java.util.Date;


public class TokenUtil {
    //token到期时间30分钟(根据需求改)
    private static final long EXPIRE_TIME= 30*60*1000;
    //密钥 (随机生成,可以从网上找到随机密钥生成器)
    private static final String TOKEN_SECRET="MD9**+4MG^EG79RV+T?J87AI4NWQVT^&";

    public static String createToken(CardOwner cardOwner){
        String token=null;
        try {
            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("mumu1307meng")
                    //存放数据
                    .withClaim("email",cardOwner.getEmail())
                    .withClaim("name",cardOwner.getName())
                    .withClaim("cardOwnerId",cardOwner.getId())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException je) {}
        System.out.println(token);
        return token;
    }

    public static Boolean checkToken(String token){
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("mumu1307meng").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("name: " + decodedJWT.getClaim("name").asString());
            System.out.println("email: " + decodedJWT.getClaim("email").asString());
            System.out.println("过期时间：" + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }
    public static String getCardOwnerId(String token){
        DecodedJWT decodedJWT;
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("mumu1307meng").build();
            decodedJWT=jwtVerifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("name: " + decodedJWT.getClaim("name").asString());
            System.out.println("email: " + decodedJWT.getClaim("email").asString());
            System.out.println("过期时间：" + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return null;
        }
       if(decodedJWT==null){
           return null;
       }
        return decodedJWT.getClaim("cardOwnerId").asString();
    }
    public static String getUserName(String token){
        DecodedJWT decodedJWT;
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("mumu1307meng").build();
            decodedJWT=jwtVerifier.verify(token);
//            System.out.println("认证通过：");
//            System.out.println("name: " + decodedJWT.getClaim("name").asString());
//            System.out.println("email: " + decodedJWT.getClaim("email").asString());
//            System.out.println("过期时间：" + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return null;
        }
        if(decodedJWT==null){
            return null;
        }
        return decodedJWT.getClaim("name").asString();
    }
}
