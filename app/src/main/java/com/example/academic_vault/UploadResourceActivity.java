package com.example.academic_vault;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadResourceActivity extends AppCompatActivity {

    AutoCompleteTextView spinnerType, spinnerCourse, spinnerLevel;
    TextInputEditText editModuleName;
    Button btnSelectFile, btnUpload;
    TextView txtFileStatus;

    Uri fileUri;
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resource);

        spinnerType = findViewById(R.id.spinner_type);
        spinnerCourse = findViewById(R.id.spinner_course);
        spinnerLevel = findViewById(R.id.spinner_level);
        editModuleName = findViewById(R.id.edit_module_name);
        btnSelectFile = findViewById(R.id.btn_select_file);
        btnUpload = findViewById(R.id.btn_upload);
        txtFileStatus = findViewById(R.id.txt_file_status);

        // Setup Type Spinner
        String[] types = {"Module Guidelines", "Module Handouts", "Study Guides"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, types);
        spinnerType.setAdapter(typeAdapter);

        // Setup Course Spinner
        String[] courses = {"BICT", "DICT", "HICT"};
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, courses);
        spinnerCourse.setAdapter(courseAdapter);

        // Setup Level Spinner
        String[] levels = {"1st Year", "2nd Year", "3rd Year"};
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, levels);
        spinnerLevel.setAdapter(levelAdapter);

        btnSelectFile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("application/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
        });

        btnUpload.setOnClickListener(v -> uploadResource());

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            txtFileStatus.setText("File Selected: " + fileUri.getLastPathSegment());
        }
    }

    private void uploadResource() {
        String type = spinnerType.getText().toString();
        String course = spinnerCourse.getText().toString();
        String level = spinnerLevel.getText().toString();
        String module = editModuleName.getText().toString();

        if (type.isEmpty() || course.isEmpty() || level.isEmpty() || module.isEmpty() || fileUri == null) {
            Toast.makeText(this, "Please fill all fields and select a file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Map UI type to database key
        String dbKey = type.toLowerCase().replace(" ", "_");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(dbKey)
                .child(course)
                .child(level)
                .push();
        
        ResourceHelperClass resource = new ResourceHelperClass(type, module, fileUri.toString());
        ref.setValue(resource).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Resource uploaded successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
