package com.speedment.jpastreamer.fieldgenerator.standard.component;

import java.io.Serializable;

public class FilmTitle implements Serializable {

    private String mainTitle;
    private String subTitle;

    public FilmTitle() {
        this.mainTitle = "";
        this.subTitle = "";
    }

    public FilmTitle(String mainTitle) {
        this.mainTitle = mainTitle;
        this.subTitle = "";
    }

    public FilmTitle(String mainTitle, String subTitle) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "FilmTitle{" +
                "mainTitle='" + mainTitle + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}