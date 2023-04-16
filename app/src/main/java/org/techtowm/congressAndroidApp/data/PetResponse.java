package org.techtowm.congressAndroidApp.data;

import com.google.gson.annotations.SerializedName;

public class PetResponse {
    @SerializedName("청원명")
    String billName;

    @SerializedName("소개의원")
    String approver;

    @SerializedName("URL")
    String url;

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
