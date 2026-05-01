package com.example.academic_vault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardStudyGuides, cardModuleHandouts, cardModuleGuidelines;
    View cardBict, cardDict, cardHict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // UI Hooks
        cardStudyGuides = findViewById(R.id.cardStudyGuides);
        cardModuleHandouts = findViewById(R.id.cardModuleHandouts);
        cardModuleGuidelines = findViewById(R.id.cardModuleGuidelines);
        
        // Course Card Hooks (from includes)
        cardBict = findViewById(R.id.include_bict);
        cardDict = findViewById(R.id.include_dict);
        cardHict = findViewById(R.id.include_hict);

        // Click listeners for categories
        cardStudyGuides.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Study Guides...", Toast.LENGTH_SHORT).show());
        cardModuleHandouts.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Module Handouts...", Toast.LENGTH_SHORT).show());
        cardModuleGuidelines.setOnClickListener(v -> Toast.makeText(DashboardActivity.this, "Opening Module Guidelines...", Toast.LENGTH_SHORT).show());

        // Click listeners for courses
        cardBict.setOnClickListener(v -> openCourseLevels("BICT"));
        cardDict.setOnClickListener(v -> openCourseLevels("DICT"));
        cardHict.setOnClickListener(v -> openCourseLevels("HICT"));
    }

    private void openCourseLevels(String courseName) {
        Intent intent = new Intent(DashboardActivity.this, CourseLevelsActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        startActivity(intent);
    }
}
