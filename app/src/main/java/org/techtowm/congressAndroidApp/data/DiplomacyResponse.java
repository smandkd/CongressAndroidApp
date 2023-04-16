package org.techtowm.congressAndroidApp.data;

import com.google.gson.annotations.SerializedName;

public class DiplomacyResponse {
    @SerializedName("제목")
    String articleTitle;

    @SerializedName("구분1")
    String masterNM;

    @SerializedName("구분2")
    String categoryNM;

    @SerializedName("작성일")
    String updateDT;

    @SerializedName("URL")
    String url;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getMasterNM() {
        return masterNM;
    }

    public void setMasterNM(String masterNM) {
        this.masterNM = masterNM;
    }

    public String getCategoryNM() {
        return categoryNM;
    }

    public void setCategoryNM(String categoryNM) {
        this.categoryNM = categoryNM;
    }

    public String getUpdateDT() {
        return updateDT;
    }

    public void setUpdateDT(String updateDT) {
        this.updateDT = updateDT;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
