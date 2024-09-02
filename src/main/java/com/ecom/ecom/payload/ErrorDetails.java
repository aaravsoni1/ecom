package com.ecom.ecom.payload;

import java.util.Date;

public class ErrorDetails {

    private String errorMessage;
    private String webRequest;
    private Date date;
    public ErrorDetails(String errorMessage, String webRequest, Date date){
        this.errorMessage = errorMessage;
        this.webRequest = webRequest;
        this.date = date;
    }
}
