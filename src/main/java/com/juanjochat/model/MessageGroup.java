package com.juanjochat.model;

import com.juanjochat.model.Message;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageGroup {
    private final ConcurrentHashMap<Long, Set<Message>> messageGroup;

    public MessageGroup(){
        messageGroup = new ConcurrentHashMap<>();
    }

    public synchronized Set<Message> getMessageList(long groupID){
        return messageGroup.get(groupID);
    }

    public synchronized void addGroup(long groupID){
        messageGroup.put(groupID, new HashSet<>());
    }

    public synchronized void addMessage(long groupID, Message message){
        messageGroup.get(groupID).add(message);
    }
}
