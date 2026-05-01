package com.example.academic_vault;

import android.content.Intent;
import android.net.Uri;
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

public class PapersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PaperAdapter adapter;
    List<PaperHelperClass> paperList;
    ProgressBar progressBar;
    TextView txtNoPapers;
    Toolbar toolbar;

    String courseName, level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers_list);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        level = getIntent().getStringExtra("LEVEL");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(courseName + " - " + level);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_papers);
        progressBar = findViewById(R.id.progress_bar);
        txtNoPapers = findViewById(R.id.txt_no_papers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        paperList = new ArrayList<>();
        adapter = new PaperAdapter(this, paperList);
        recyclerView.setAdapter(adapter);

        fetchPapers();
    }

    private void fetchPapers() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("exam_papers")
                .child(courseName)
                .child(level);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paperList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    PaperHelperClass paper = data.getValue(PaperHelperClass.class);
                    if (paper != null) {
                        paperList.add(paper);
                    }
                }
                
                progressBar.setVisibility(View.GONE);
                if (paperList.isEmpty()) {
                    txtNoPapers.setVisibility(View.VISIBLE);
                } else {
                    txtNoPapers.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PapersListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
