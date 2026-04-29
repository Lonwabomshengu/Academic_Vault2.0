package com.example.academic_vault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class AdminDashboard extends AppCompatActivity {

    MaterialCardView cardManagePapers, cardManageCourses, cardReports, cardManageUsers, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardManagePapers = findViewById(R.id.card_manage_papers);
        cardManageCourses = findViewById(R.id.card_manage_courses);
        cardReports = findViewById(R.id.card_reports);
        cardManageUsers = findViewById(R.id.card_manage_users);
        cardLogout = findViewById(R.id.card_logout);

        cardManagePapers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, UploadPaperActivity.class);
            startActivity(intent);
        });

        cardManageCourses.setOnClickListener(v -> 
            Toast.makeText(this, "Courses management coming soon", Toast.LENGTH_SHORT).show());

        cardReports.setOnClickListener(v -> 
            Toast.makeText(this, "Reports coming soon", Toast.LENGTH_SHORT).show());

        cardManageUsers.setOnClickListener(v -> 
            Toast.makeText(this, "User management coming soon", Toast.LENGTH_SHORT).show());

        cardLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
