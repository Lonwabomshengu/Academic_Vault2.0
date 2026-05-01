package com.example.academic_vault;

public class ResourceHelperClass {
    String type, moduleName, fileUri;

    public ResourceHelperClass() {
    }

    public ResourceHelperClass(String type, String moduleName, String fileUri) {
        this.type = type;
        this.moduleName = moduleName;
        this.fileUri = fileUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
