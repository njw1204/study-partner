package me.blog.njw1204.studypartner;

public class StudyItem {
    private String icon, kind, school, area, title;
    private int cnt, study_no;
    private boolean staff;

    public StudyItem(String icon, String kind, String school, String area, String title, int cnt, int study_no, boolean staff) {
        this.icon = icon;
        this.kind = kind;
        this.school = school;
        this.title = title;
        this.area = area;
        this.cnt = cnt;
        this.study_no = study_no;
        this.staff = staff;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getStudy_no() {
        return study_no;
    }

    public void setStudy_no(int study_no) {
        this.study_no = study_no;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }
}
