package me.blog.njw1204.studypartner;

public class ChatDTO {

    private String userId;
    private String userNick;
    private String message;

    public ChatDTO () {

    }

    public ChatDTO(String userId, String userNick, String message) {
        this.userId = userId;
        this.userNick = userNick;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
