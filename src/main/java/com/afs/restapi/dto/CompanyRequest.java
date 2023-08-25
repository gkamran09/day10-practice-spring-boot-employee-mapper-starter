package com.afs.restapi.dto;

public class CompanyRequest {

    public CompanyRequest() {
    }
    private String name;
    public CompanyRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
