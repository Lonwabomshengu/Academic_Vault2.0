package com.example.academic_vault;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResourceListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ResourceAdapter adapter;
    List<ResourceHelperClass> resourceList;
    ProgressBar progressBar;
    TextView txtNoResources;
    Toolbar toolbar;

    String courseName, level, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_list);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        level = getIntent().getStringExtra("LEVEL");
        type = getIntent().getStringExtra("TYPE"); // e.g., "module_handouts"

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            String title = (type != null ? type.replace("_", " ") : "Resources");
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_resources);
        progressBar = findViewById(R.id.progress_bar);
        txtNoResources = findViewById(R.id.txt_no_resources);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resourceList = new ArrayList<>();
        adapter = new ResourceAdapter(this, resourceList);
        recyclerView.setAdapter(adapter);

        fetchResources();
    }

    private void fetchResources() {
        if (type == null || courseName == null || level == null) {
            Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(type)
                .child(courseName)
                .child(level);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resourceList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ResourceHelperClass resource = data.getValue(ResourceHelperClass.class);
                    if (resource != null) {
                        resourceList.add(resource);
                    }
                }
                
                progressBar.setVisibility(View.GONE);
                if (resourceList.isEmpty()) {
                    txtNoResources.setVisibility(View.VISIBLE);
                } else {
                    txtNoResources.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ResourceListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
