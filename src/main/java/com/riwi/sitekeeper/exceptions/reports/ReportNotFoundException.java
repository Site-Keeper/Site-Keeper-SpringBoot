package com.riwi.sitekeeper.exceptions.reports;

public class ReportNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1;

    public ReportNotFoundException(String message){
        super(message);

    }
}
