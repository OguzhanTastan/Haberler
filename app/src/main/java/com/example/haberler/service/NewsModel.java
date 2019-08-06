package com.example.haberler.service;

import java.util.List;

public class NewsModel extends BaseModel {

    public long Id;
    public String Title;
    public String Description;
    public String Url;
    public String Path;
    public String CreatedDate;
    public List<FileModel> Files;

}