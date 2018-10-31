package me.blog.njw1204.studypartner;

public class StudyItem {
    private String icon, kind, school, title;
    private int cnt;

    public StudyItem(String icon, String kind, String school, String title, int cnt) {
        this.icon = icon;
        this.kind = kind;
        this.school = school;
        this.title = title;
        this.cnt = cnt;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
