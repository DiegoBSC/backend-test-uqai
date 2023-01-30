package com.uqai.demo.app.uqaidemo.dto;

public class ResponseError extends Exception {
    public ResponseError(String message){
        super(message);
    }
}