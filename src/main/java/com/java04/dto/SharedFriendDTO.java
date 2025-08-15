package com.java04.dto;

import java.util.Date;

public class SharedFriendDTO {
    private String senderName;
    private String senderEmail;
    private String receiverEmail;
    private Date sentDate;

    public SharedFriendDTO(String senderName, String senderEmail, String receiverEmail, Date sentDate) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.sentDate = sentDate;
    }

    public String getSenderName() { return senderName; }
    public String getSenderEmail() { return senderEmail; }
    public String getReceiverEmail() { return receiverEmail; }
    public Date getSentDate() { return sentDate; }
}




