package com.juanjochat.service;

import com.juanjochat.ChatApplication;
import model.CloseSignal;
import model.Group;
import model.Message;

import java.io.ObjectInputStream;

public class ListenThread extends Thread{
    ObjectInputStream objIn;
    public ListenThread(ObjectInputStream objIn){
        this.objIn = objIn;
    }

    @Override
    public void run() {
        try {
            Object obj = new Object();
            // When server send a CloseSignal Object application will stop
            while (!(obj instanceof CloseSignal)) {
                obj = objIn.readObject();
                // When server send a Message, it will be saved in the correct group
                if (obj instanceof Message message) {
                    ChatApplication.messageGroup.addMessage(message.getGroup(), message);
                    System.out.println(message);
                } else if( obj instanceof Group group){
                    ChatApplication.addGroup(group);
                    System.out.println(group);
                }
            }
            ChatApplication.setClose(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
