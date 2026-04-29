package com.example.academic_vault;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class CourseLevelsActivity extends AppCompatActivity {

    TextView toolbarTitle;
    MaterialCardView cardFirstYear, cardSecondYear, cardThirdYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_levels);

        toolbarTitle = findViewById(R.id.toolbar_title);
        cardFirstYear = findViewById(R.id.cardFirstYear);
        cardSecondYear = findViewById(R.id.cardSecondYear);
        cardThirdYear = findViewById(R.id.cardThirdYear);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        if (courseName != null) {
            toolbarTitle.setText(courseName + " - Select Level");
        }

        cardFirstYear.setOnClickListener(v -> Toast.makeText(this, "First Year selected", Toast.LENGTH_SHORT).show());
        cardSecondYear.setOnClickListener(v -> Toast.makeText(this, "Second Year selected", Toast.LENGTH_SHORT).show());
        cardThirdYear.setOnClickListener(v -> Toast.makeText(this, "Third Year selected", Toast.LENGTH_SHORT).show());

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }
}
