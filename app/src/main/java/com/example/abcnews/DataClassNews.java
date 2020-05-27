package com.example.abcnews;


// this is the data class for our news channel.

/**
 * @author tusha
 */

public class DataClassNews {
    private String abcNewsHeading;
    private String abcNewsDate;
    private String abcNewsType;
    private String abcNewsURL;
    private String abcNewsTime;


    public DataClassNews(String abcNewsHeading, String abcNewsDate, String abcNewsType, String abcNewsURL, String abcNewsTime) {
        this.abcNewsHeading = abcNewsHeading;
        this.abcNewsDate = abcNewsDate;
        this.abcNewsType = abcNewsType;
        this.abcNewsURL = abcNewsURL;
        this.abcNewsTime = abcNewsTime;
    }

    public String getAbcNewsTime() {
        return abcNewsTime;
    }

    public void setAbcNewsTime(String abcNewsTime) {
        this.abcNewsTime = abcNewsTime;
    }


    public String getAbcNewsHeading() {
        return abcNewsHeading;
    }

    public void setAbcNewsHeading(String abcNewsHeading) {
        this.abcNewsHeading = abcNewsHeading;
    }

    public String getAbcNewsDate() {
        return abcNewsDate;
    }

    public void setAbcNewsDate(String abcNewsDate) {
        this.abcNewsDate = abcNewsDate;
    }

    public String getAbcNewsType() {
        return abcNewsType;
    }

    public void setAbcNewsType(String abcNewsType) {
        this.abcNewsType = abcNewsType;
    }

    public String getAbcNewsURL() {
        return abcNewsURL;
    }

    public void setAbcNewsURL(String abcNewsURL) {
        this.abcNewsURL = abcNewsURL;
    }
}
