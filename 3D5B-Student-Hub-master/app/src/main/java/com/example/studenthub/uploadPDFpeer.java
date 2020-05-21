package com.example.studenthub;

public class uploadPDFpeer {

    public String name;
    public String url;
    public String description;

    public uploadPDFpeer(){
    }


    public uploadPDFpeer(String name, String url, String description){
        this.name = name;
        this.url = url;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {return description;}
}
