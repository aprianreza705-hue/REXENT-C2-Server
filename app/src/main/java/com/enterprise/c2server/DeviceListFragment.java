package com.enterprise.c2server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeviceListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private FirebaseRecyclerAdapter<Device, DeviceViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference devicesRef = FirebaseDatabase.getInstance().getReference("devices");

        FirebaseRecyclerOptions<Device> options =
            new FirebaseRecyclerOptions.Builder<Device>()
                .setQuery(devicesRef, Device.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseRecyclerAdapter<Device, DeviceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DeviceViewHolder holder, int position, @NonNull Device model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
                return new DeviceViewHolder(v);
            }
        };

        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) adapter.stopListening();
    }
}
