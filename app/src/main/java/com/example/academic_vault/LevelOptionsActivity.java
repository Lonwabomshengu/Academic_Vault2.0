package com.example.academic_vault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.card.MaterialCardView;

public class LevelOptionsActivity extends AppCompatActivity {

    MaterialCardView cardPapers, cardGuides, cardHandouts, cardGuidelines;
    String courseName, level;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_options);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        level = getIntent().getStringExtra("LEVEL");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(courseName + " - " + level);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        cardPapers = findViewById(R.id.card_past_papers);
        cardGuides = findViewById(R.id.card_study_guides);
        cardHandouts = findViewById(R.id.card_handouts);
        cardGuidelines = findViewById(R.id.card_guidelines);

        cardPapers.setOnClickListener(v -> openPapers());
        cardGuides.setOnClickListener(v -> openResources("study_guides"));
        cardHandouts.setOnClickListener(v -> openResources("module_handouts"));
        cardGuidelines.setOnClickListener(v -> openResources("module_guidelines"));
    }

    private void openPapers() {
        Intent intent = new Intent(this, PapersListActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
    }

    private void openResources(String type) {
        Intent intent = new Intent(this, ResourceListActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        intent.putExtra("LEVEL", level);
        intent.putExtra("TYPE", type);
        startActivity(intent);
    }
}
