package com.enterprise.c2server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Device {
    public String session_id;
    public String model;
    public String android;
    public int battery;
    public String ip;
    public boolean root;
    public long last_seen;
    public String status;

    public Device() {}

    public String getLastSeenFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return sdf.format(new Date(last_seen));
    }
}
