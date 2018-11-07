package me.blog.njw1204.studypartner;

public class PlanDTO {

    private String scheduleName;
    private String scheduleTime;

    public PlanDTO() {}
    public PlanDTO(String scheduleName, String scheduleTime) {
        this.scheduleName = scheduleName;
        this.scheduleTime = scheduleTime;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }
}
