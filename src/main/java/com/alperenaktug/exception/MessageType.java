package com.alperenaktug.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001" , "Kayıt bulunamadı!!"),
    TOKEN_IS_EXPIRED("1003" , "Tokenın süresi bitmiştir!!"),
    USERNAME_NOT_FOUND("1004" , "Username bulunamadı!!"),
    USERNAME_OR_PASSWORD_INVALID("1005" , "Kullanıcı adı veya şifre hatalı!!"),
    GENERAL_EXCEPTION("9999" , "Genel bir hata oluştu!!");

    private String code;
    private String message;


    MessageType(String code , String message ){
        this.code = code;
        this.message = message;
    }
}
