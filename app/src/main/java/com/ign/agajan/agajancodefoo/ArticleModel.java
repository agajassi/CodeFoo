package com.ign.agajan.agajancodefoo;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

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


    public static ArticleModel fromJson(JSONObject jsonObject) {
        ArticleModel aModel = new ArticleModel();
        try {
            // Deserialize json into object fields
            aModel.headline = jsonObject.getJSONObject("metadata").getString("headline");
            aModel.publishDate = jsonObject.getJSONObject("metadata").getString("publishDate");
            aModel.articleType = jsonObject.getJSONObject("metadata").getString("articleType");
            JSONArray thumbnails = jsonObject.getJSONArray("thumbnails");
            aModel.posterUrl = thumbnails.getJSONObject(1).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return aModel;
    }

    // Decodes array of box office movie json results into business model objects
    public static ArrayList<ArticleModel> fromJson(JSONArray jsonArray) {
        ArrayList<ArticleModel> articles = new ArrayList<ArticleModel>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject articleJson = null;
            try {
                articleJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            ArticleModel aModel = ArticleModel.fromJson(articleJson);
            if (aModel != null) {
                articles.add(aModel);
            }
        }
        return articles;
    }
}
