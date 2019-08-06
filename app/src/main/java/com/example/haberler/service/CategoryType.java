package com.example.haberler.service;

import android.graphics.Color;

public enum CategoryType {

    ALL,
    AGENDA,
    WORLD,
    SPORT,
    TECHNOLOGY,
    MAGAZINE,
    ECONOMY,

    SMALL,
    MEDIUM,
    LARGE,

    Default,
    USER1,
    USER2;

    public String getUrl() {
        if (this == ALL) return "";
        if (this == AGENDA) return "'/gundem/'";
        if (this == WORLD) return "'/dunya/'";
        if (this == SPORT) return "'/spor/'";
        if (this == TECHNOLOGY) return "'/teknoloji/'";
        if (this == MAGAZINE) return "'/kelebek/magazin/'";
        if (this == ECONOMY) return "'/ekonomi/'";
        return "";
    }

    public float textSize(float defaultSize) {

        if (this == SMALL) return (float) (defaultSize * 0.5);
        if (this == MEDIUM) return (float) (defaultSize * 0.7);
        if (this == LARGE) return (float) (defaultSize * 0.9);

        return defaultSize;
    }

    public int textColor(int defaultColor) {
        if (this == Default) return defaultColor = Color.parseColor("#050505");
        if (this == USER1) return defaultColor = Color.parseColor("#3F51B5");
        if (this == USER2) return defaultColor = Color.parseColor("#E91E63");
        return defaultColor = Color.parseColor("#050505");
    }

}
