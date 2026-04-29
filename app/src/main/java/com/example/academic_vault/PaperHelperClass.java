package com.example.academic_vault;

public class PaperHelperClass {
    String moduleName, year, fileUri;

    public PaperHelperClass() {
    }

    public PaperHelperClass(String moduleName, String year, String fileUri) {
        this.moduleName = moduleName;
        this.year = year;
        this.fileUri = fileUri;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
