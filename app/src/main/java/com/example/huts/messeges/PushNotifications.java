package com.example.huts.messeges;

public class PushNotifications {

    private NotificationsData notificationsData ;
    private String to;

    public PushNotifications(NotificationsData notificationsData, String to) {
        this.notificationsData = notificationsData;
        this.to = to;
    }

    public NotificationsData getNotificationsData() {
        return notificationsData;
    }

    public void setNotificationsData(NotificationsData notificationsData) {
        this.notificationsData = notificationsData;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
