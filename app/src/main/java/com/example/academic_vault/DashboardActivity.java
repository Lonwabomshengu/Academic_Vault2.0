package com.example.academic_vault;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardStudyGuides, cardModuleHandouts, cardModuleGuidelines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardStudyGuides = findViewById(R.id.cardStudyGuides);
        cardModuleHandouts = findViewById(R.id.cardModuleHandouts);
        cardModuleGuidelines = findViewById(R.id.cardModuleGuidelines);

        cardStudyGuides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Opening Study Guides...", Toast.LENGTH_SHORT).show();
            }
        });

        cardModuleHandouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Opening Module Handouts...", Toast.LENGTH_SHORT).show();
            }
        });

        cardModuleGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Opening Module Guidelines...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
