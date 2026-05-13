package com.enterprise.c2server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FileExplorerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("📂 File Explorer\n\nRemote file browser coming soon.\nUse /ls, /download, /rm commands.");
        tv.setTextSize(14);
        tv.setTextColor(0xFFFFFFFF);
        tv.setPadding(32, 32, 32, 32);
        tv.setBackgroundColor(0xFF121212);
        return tv;
    }
}
