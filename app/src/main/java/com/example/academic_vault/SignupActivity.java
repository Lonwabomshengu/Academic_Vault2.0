package com.example.academic_vault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    //variables
    TextInputLayout firstName, surname, studentNo, courseName, email, password;
    Button signup, login;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //hook for the variables
        firstName = findViewById(R.id.firstNameInputLayout);
        surname = findViewById(R.id.surnameInputLayout);
        studentNo = findViewById(R.id.studentNoInputLayout);
        courseName = findViewById(R.id.CourseNameInputLayout);
        email = findViewById(R.id.emailInputLayout);
        password = findViewById(R.id.password_input_layout);
        signup = findViewById(R.id.sign_go_button);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Save data in firebase
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                // Get all the values
                String fName = firstName.getEditText() != null ? firstName.getEditText().getText().toString().trim() : "";
                String sName = surname.getEditText() != null ? surname.getEditText().getText().toString().trim() : "";
                String sNo = studentNo.getEditText() != null ? studentNo.getEditText().getText().toString().trim() : "";
                String cName = courseName.getEditText() != null ? courseName.getEditText().getText().toString().trim() : "";
                String mail = email.getEditText() != null ? email.getEditText().getText().toString().trim() : "";
                String pass = password.getEditText() != null ? password.getEditText().getText().toString().trim() : "";

                // Validation
                if (fName.isEmpty() || sName.isEmpty() || sNo.isEmpty() || cName.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Determine role based on email domain
                final String role = mail.toLowerCase().endsWith("@admin.com") ? "admin" : "student";

                UserHelperClass helperClass = new UserHelperClass(fName, sName, sNo, cName, mail, pass, role);

                reference.child(sNo).setValue(helperClass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Registration Successful as " + role, Toast.LENGTH_SHORT).show();
                        
                        Intent intent;
                        if (role.equals("admin")) {
                            intent = new Intent(SignupActivity.this, AdminDashboard.class);
                        } else {
                            intent = new Intent(SignupActivity.this, DashboardActivity.class);
                        }

                        startActivity(intent);
                        finish();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(SignupActivity.this, "Registration Failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
