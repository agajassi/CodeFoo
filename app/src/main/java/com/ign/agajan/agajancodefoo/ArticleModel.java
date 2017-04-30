package com.ign.agajan.agajancodefoo;

/**
 * Created by ajumakuliyev on 4/28/17.
 */
public class ArticleModel {
    private String headline;
    private String publishDate;
    private String articleType;
    private String posterUrl;

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getArticleType() {
        return articleType;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
