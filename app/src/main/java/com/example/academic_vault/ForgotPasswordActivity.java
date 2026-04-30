package com.example.academic_vault;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView backBtn;
    TextInputLayout studentNo, email;
    Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backBtn = findViewById(R.id.back_button);
        studentNo = findViewById(R.id.student_no_input_layout);
        email = findViewById(R.id.email_input_layout);
        resetBtn = findViewById(R.id.reset_button);

        backBtn.setOnClickListener(v -> finish());

        resetBtn.setOnClickListener(v -> {
            String sNo = studentNo.getEditText().getText().toString().trim();
            String mail = email.getEditText().getText().toString().trim();

            if (sNo.isEmpty() || mail.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            checkUser(sNo, mail);
        });
    }

    private void checkUser(String sNo, String mail) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("studentNo").equalTo(sNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String emailFromDB = userSnapshot.child("email").getValue(String.class);
                        if (emailFromDB != null && emailFromDB.equals(mail)) {
                            String password = userSnapshot.child("password").getValue(String.class);
                            Toast.makeText(ForgotPasswordActivity.this, "Your password is: " + password, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Toast.makeText(ForgotPasswordActivity.this, "Email does not match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
