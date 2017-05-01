package com.ign.agajan.agajancodefoo;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ajumakuliyev on 4/28/17.
 */
public class ArticleModel {
    private String headline;
    private String publishDate;
    private String posterUrl;
    private String articleUrl;

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setPublishedSince(String fullDate) {
        // calendar with current date and time
        Calendar c = Calendar.getInstance();
        StringUtils sUtils = new StringUtils();

        // parse published date and time data retrieved from json
        int publishMonth = Integer.valueOf(sUtils.substring(fullDate, 5, 7));
        int publishDay = Integer.valueOf(sUtils.substring(fullDate, 8, 10));
        int publishHour = Integer.valueOf(sUtils.substring(fullDate, 11, 13));
        int publishMinutes = Integer.valueOf(sUtils.substring(fullDate, 11, 13));

        // init published date and time
        Calendar p = Calendar.getInstance();
        p.set(Calendar.DAY_OF_MONTH,publishDay);
        p.set(Calendar.HOUR,publishHour);
        p.set(Calendar.MINUTE,publishMinutes);
        p.set(Calendar.MONTH, publishMonth);

        long diffHours = TimeUnit.MILLISECONDS.toHours(Math.abs(c.getTimeInMillis() - p.getTimeInMillis()));
        if(diffHours != 0) {
            publishDate = String.valueOf(diffHours) + " hours ago";
        }
        else {
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(Math.abs(c.getTimeInMillis() - p.getTimeInMillis()));
            publishDate = String.valueOf(diffMinutes) + " minutes ago";
        }
    }

    public String getHeadline() {
        return headline;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
