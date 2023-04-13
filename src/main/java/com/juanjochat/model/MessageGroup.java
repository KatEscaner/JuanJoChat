package com.juanjochat.model;

import model.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageGroup {
    private final ConcurrentHashMap<Long, List<Message>> messageGroup;

    public MessageGroup(){
        messageGroup = new ConcurrentHashMap<>();
    }

    public synchronized List<Message> getMessageList(long groupID){
        return messageGroup.get(groupID);
    }

    public synchronized void addGroup(long groupID){
        messageGroup.put(groupID, new ArrayList<>());
    }

    public synchronized void addMessage(long groupID, Message message){
        messageGroup.get(groupID).add(message);
    }
}
