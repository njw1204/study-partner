package me.blog.njw1204.studypartner;

import android.app.Application;
import android.graphics.Typeface;

import java.util.ArrayList;

public class StudyPartner extends Application {
    private ArrayList<StudyItem> studyListItems;
    private String addIconUrl;
    public Typeface fas;

    @Override
    public void onCreate() {
        super.onCreate();
        studyListItems = new ArrayList<>();
        addIconUrl = "";
        fas = Typeface.createFromAsset(getAssets(), "fa_solid_900.ttf");
    }

    public ArrayList<StudyItem> getStudyListItems() {
        return studyListItems;
    }

    public void setStudyListItems(ArrayList<StudyItem> items) {
        this.studyListItems = items;
    }

    public String getAddIconUrl() {
        return addIconUrl;
    }

    public void setAddIconUrl(String addIconUrl) {
        this.addIconUrl = addIconUrl;
    }
}
