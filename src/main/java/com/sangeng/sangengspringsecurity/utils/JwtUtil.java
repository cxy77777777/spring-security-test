package com.sangeng.sangengspringsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * jwt工具类
 */
@Slf4j
public class JwtUtil {

    public static final Long JWT_TTL = 60*60*1000L;//60*60*1000l 一小时

    //设置密钥明文
    public static final String JWT_KEY = "sangeng";

    public static String getUID(){
        String token = UUID.randomUUID().toString().replace("-","");
        return  token;
    }

    /**
     * 生成jwt
     * @param subject token中要存放的数据 json格式
     * @return
     */
    public static String createJWT(String subject){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,null,getUID());
        return jwtBuilder.compact();
    }

    /**
     * 生成jwt
     * @param id
     * @param subject token中要存放的数据 json格式
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject,Long ttlMillis){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,ttlMillis,getUID());
        return jwtBuilder.compact();
    }

    /**
     * 生成jwt
     * @param id
     * @param subject token中要存放的数据 json格式
     * @param ttlMillis token超时时间
     * @return
     */
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

    /**
     * 生成加密后的密钥secretKey
     * @return
     */
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
        return key;
    }

    /**
     * 解析
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws Exception{
//        String jwt = createJWT("1234");
        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3ZjIwMGVlYTA0MzA0MTdhOGQ0MGJjY2Y0MTJjMTY1MiIsInN1YiI6IjEyMzQiLCJpc3MiOiJzZyIsImlhdCI6MTY1NDE2MjkxNiwiZXhwIjoxNjU0MTY2NTE2fQ.CF00mEpRyyWGIngVVZpMX6bZ8SO6LUppF1B45hxuc6E");
        System.out.println(claims.getSubject());
    }
}
