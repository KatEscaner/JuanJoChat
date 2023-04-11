package com.juanjochat.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private long id = 0;
    private long sender;
    private long group;
    private String content;
    private LocalDateTime timestamp;
    @Serial
    private static final long serialVersionUID = -790315814723546702L;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Message(long id, long sender, long group,String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.group = group;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message(long id, long sender, long group,String content) {
        this.id = id;
        this.sender = sender;
        this.group = group;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Message(long sender, long group,String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.group = group;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message( long group, String content) {
        this.group = group;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Message(long id){
        this.id = id;
    }

    public Message(){}

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", group=" + group +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
