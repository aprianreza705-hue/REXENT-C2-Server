package com.enterprise.c2server;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceViewHolder extends RecyclerView.ViewHolder {
    private TextView sessionId, model, androidVer, battery, ip, status;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        sessionId = itemView.findViewById(R.id.sessionId);
        model = itemView.findViewById(R.id.model);
        androidVer = itemView.findViewById(R.id.androidVer);
        battery = itemView.findViewById(R.id.battery);
        ip = itemView.findViewById(R.id.ip);
        status = itemView.findViewById(R.id.status);
    }

    public void bind(Device device) {
        sessionId.setText(device.session_id);
        model.setText(device.model);
        androidVer.setText(device.android);
        battery.setText(device.battery + "%");
        ip.setText(device.ip);

        boolean online = (System.currentTimeMillis() - device.last_seen) < 120000;
        if (online) {
            status.setText("● ONLINE");
            status.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            status.setText("● OFFLINE");
            status.setTextColor(Color.parseColor("#F44336"));
        }
    }
}
