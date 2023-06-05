package com.example.heybuddy.Models;

import java.util.ArrayList;

public class GroupChats {
    private String name;
    private String userName;
    private String groupId;
    private String messages;
    private String profilepic;
    private long timestamp;

    public ArrayList<Users> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Users> members) {
        this.members = members;
    }

    private ArrayList<Users> members;

    public String getLastmessageuserid() {
        return lastmessageuserid;
    }

    public void setLastmessageuserid(String lastmessageuserid) {
        this.lastmessageuserid = lastmessageuserid;
    }

    private String lastmessageuserid;

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    private String lastmessage;

    public String getType() {
        return type;
    }

    public String getUserId(String key) {
        return groupId;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public GroupChats(){

    }
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setUserId(String groupId) {
        this.groupId = groupId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
