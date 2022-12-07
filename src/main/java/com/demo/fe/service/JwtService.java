package com.demo.fe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
@Service
public class JwtService {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    public static String getJwt(HttpServletRequest request){
        log.info(request.getHeader(TOKEN_HEADER));
        return request.getHeader(TOKEN_HEADER);
    }

    public static String getToken(String tokenHeader){
        return tokenHeader.replace(TOKEN_PREFIX,"");
    }

    public static String parseToken(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        //String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        return payload;
    }

}
