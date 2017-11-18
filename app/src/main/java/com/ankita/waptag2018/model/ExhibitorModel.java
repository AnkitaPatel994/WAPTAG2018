package com.ankita.waptag2018.model;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class ExhibitorModel {

    String company_name,name,mobile,email,stall_no,location;

    public String getCompanyName() {
        return company_name;
    }

    public void setCompanyName(String company_name) {
        this.company_name = company_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStall_no() {
        return stall_no;
    }

    public void setStall_no(String stall_no) {
        this.stall_no = stall_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ExhibitorModel(String company_name, String name, String mobile, String email, String stall_no, String location) {
        this.company_name = company_name;
        this.name = name;
        this.mobile = mobile;

        this.email = email;
        this.stall_no = stall_no;
        this.location = location;
    }
}
