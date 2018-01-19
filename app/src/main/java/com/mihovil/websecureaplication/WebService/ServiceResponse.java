package com.mihovil.websecureaplication.WebService;

/**
 * Created by Mihovil on 10.1.2018..
 */

public class ServiceResponse {

    private String token;
    private String email;
    private String responseMessage;
    private boolean postoji;
    private String publicKey;

    public boolean isPostoji() {
        return postoji;
    }

    public void setPostoji(boolean postoji) {
        this.postoji = postoji;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
