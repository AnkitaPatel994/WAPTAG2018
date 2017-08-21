package com.konnect.waptag2017.model;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class ExhibitorModel {

    public String getExhibitor_company() {
        return exhibitor_company;
    }

    public void setExhibitor_company(String exhibitor_company) {
        this.exhibitor_company = exhibitor_company;
    }

    public String getExhibitor_name() {
        return exhibitor_name;
    }

    public void setExhibitor_name(String exhibitor_name) {
        this.exhibitor_name = exhibitor_name;
    }

    public String getExhibitor_mobile() {
        return exhibitor_mobile;
    }

    public void setExhibitor_mobile(String exhibitor_mobile) {
        this.exhibitor_mobile = exhibitor_mobile;
    }

    public String getExhibitor_mail() {
        return exhibitor_mail;
    }

    public void setExhibitor_mail(String exhibitor_mail) {
        this.exhibitor_mail = exhibitor_mail;
    }

    public String getExhibitor_stall() {
        return exhibitor_stall;
    }

    public void setExhibitor_stall(String exhibitor_stall) {
        this.exhibitor_stall = exhibitor_stall;
    }

    public String exhibitor_company;
    public String exhibitor_name;
    public String exhibitor_mobile;
    public String exhibitor_mail;
    public String exhibitor_stall;

    public String getExhibitor_add() {
        return exhibitor_add;
    }

    public void setExhibitor_add(String exhibitor_add) {
        this.exhibitor_add = exhibitor_add;
    }

    public String exhibitor_add;

    public ExhibitorModel(String name, String number, String mobile, String mail, String stall) {
        exhibitor_company = name;
        exhibitor_name = number;
        exhibitor_mobile = mobile;
        exhibitor_mail = mail;
        exhibitor_stall = stall;
    }

    public ExhibitorModel() {
    }


}
