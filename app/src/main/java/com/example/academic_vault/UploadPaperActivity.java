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

public class UploadPaperActivity extends AppCompatActivity {

    AutoCompleteTextView spinnerCourse, spinnerLevel;
    TextInputEditText editModuleName, editYear;
    Button btnSelectFile, btnUpload;
    TextView txtFileStatus;

    Uri fileUri;
    private static final int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_paper);

        spinnerCourse = findViewById(R.id.spinner_course);
        spinnerLevel = findViewById(R.id.spinner_level);
        editModuleName = findViewById(R.id.edit_module_name);
        editYear = findViewById(R.id.edit_year);
        btnSelectFile = findViewById(R.id.btn_select_file);
        btnUpload = findViewById(R.id.btn_upload);
        txtFileStatus = findViewById(R.id.txt_file_status);

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
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
        });

        btnUpload.setOnClickListener(v -> uploadPaper());

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            txtFileStatus.setText("File Selected: " + fileUri.getLastPathSegment());
        }
    }

    private void uploadPaper() {
        String course = spinnerCourse.getText().toString();
        String level = spinnerLevel.getText().toString();
        String module = editModuleName.getText().toString();
        String year = editYear.getText().toString();

        if (course.isEmpty() || level.isEmpty() || module.isEmpty() || year.isEmpty() || fileUri == null) {
            Toast.makeText(this, "Please fill all fields and select a file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save metadata in Database
        // Structured by course -> level -> paper
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("exam_papers")
                .child(course)
                .child(level)
                .push();
        
        PaperHelperClass paper = new PaperHelperClass(module, year, fileUri.toString());
        ref.setValue(paper).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Paper uploaded successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
