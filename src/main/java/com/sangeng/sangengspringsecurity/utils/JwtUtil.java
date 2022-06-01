package com.sangeng.sangengspringsecurity.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * jwt工具类
 */
public class JwtUtil {

    public static final Long JWT_TTL = 60*60*1000L;//60*60*1000l 一小时

    //设置密钥明文
    public static final String JWT_KEY = "sange";

    public static String getUID(){
        String token = UUID.randomUUID().toString().replace("-","");
        return  token;
    }

    public static String createJWT(String id,String subject,Long ttlMillis){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,ttlMillis,id);
        return jwtBuilder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject,Long ttlMillis,String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null){
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)        //唯一的id
                .setSubject(subject)//主题。可以是json数据
                .setIssuer("sg")    //签发者
                .setIssuedAt(now)   //签发时间
                .signWith(signatureAlgorithm,secretKey) //使用HS256对称加密算法签名，第二个参数为密钥
                .setExpiration(expDate);
    }
}
