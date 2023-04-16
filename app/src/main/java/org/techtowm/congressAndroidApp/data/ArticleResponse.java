package org.techtowm.congressAndroidApp.data;

import com.google.gson.annotations.SerializedName;

public class ArticleResponse {

    @SerializedName("제목")
    String title;

    @SerializedName("URL")
    String url;

    @SerializedName("최종수정일")
    String fixDT;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFixDT() {
        return fixDT;
    }

    public void setFixDT(String fixDT) {
        this.fixDT = fixDT;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
