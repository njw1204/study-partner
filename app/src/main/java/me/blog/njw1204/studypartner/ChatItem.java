package me.blog.njw1204.studypartner;

public class ChatItem {
    private String id, nick, message;

    public ChatItem(String id, String nick, String message) {
        this.id = id;
        this.nick = nick;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
