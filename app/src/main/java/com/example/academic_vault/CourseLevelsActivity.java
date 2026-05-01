package com.example.academic_vault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class CourseLevelsActivity extends AppCompatActivity {

    TextView toolbarTitle;
    MaterialCardView cardFirstYear, cardSecondYear, cardThirdYear;
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_levels);

        toolbarTitle = findViewById(R.id.toolbar_title);
        cardFirstYear = findViewById(R.id.cardFirstYear);
        cardSecondYear = findViewById(R.id.cardSecondYear);
        cardThirdYear = findViewById(R.id.cardThirdYear);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        if (courseName != null) {
            toolbarTitle.setText(courseName + " - Select Level");
        }

        cardFirstYear.setOnClickListener(v -> openLevelOptions("1st Year"));
        cardSecondYear.setOnClickListener(v -> openLevelOptions("2nd Year"));
        cardThirdYear.setOnClickListener(v -> openLevelOptions("3rd Year"));

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void openLevelOptions(String level) {
        Intent intent = new Intent(CourseLevelsActivity.this, LevelOptionsActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
    }
}
