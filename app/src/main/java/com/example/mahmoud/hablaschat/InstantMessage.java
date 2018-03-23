package com.example.mahmoud.hablaschat;

/**
 * Created by Mahmoud on 3/23/2018.
 */

public class InstantMessage {
    private String msg;
    private String author;

    public InstantMessage()
    {

    }
    public InstantMessage(String msg, String author) {
        this.msg = msg;
        this.author = author;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
