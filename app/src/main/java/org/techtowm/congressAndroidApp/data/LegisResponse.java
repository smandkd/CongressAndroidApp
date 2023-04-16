package org.techtowm.congressAndroidApp.data;

import com.google.gson.annotations.SerializedName;

public class LegisResponse {
    @SerializedName("법률안명")
    String billName;

    @SerializedName("대")
    String age;

    @SerializedName("소관위원회")
    String currCommitee;

    @SerializedName("URL")
    String linkUrl;

    @SerializedName("제안이유/주요내용")
    String legConRe;

    @SerializedName("입법예고기간")
    String legisPeriod;

    public String getRegisPeriod() {
        return legisPeriod;
    }

    public void setRegisPeriod(String regisPeriod) {
        this.legisPeriod = regisPeriod;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCurrCommitee() {
        return currCommitee;
    }

    public void setCurrCommitee(String currCommitee) {
        this.currCommitee = currCommitee;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLegConRe() {
        return legConRe;
    }

    public void setLegConRe(String legConRe) {
        this.legConRe = legConRe;
    }

}
