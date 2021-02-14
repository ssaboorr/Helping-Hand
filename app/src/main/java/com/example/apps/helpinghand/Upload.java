package com.example.apps.helpinghand;

import com.google.firebase.database.Exclude;

public class Upload {
    private String imgName;
    private String imgUrl;
    private  String mkey;
    public Upload() {
    }

    public Upload(String imgName, String imgUrl) {
        if(imgName.trim().equals(""))
        {
            imgName="No name";
        }
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Exclude
    public String getkey() {

        return mkey;
    }
    @Exclude
    public void setkey(String key) {
        mkey = key;
    }

}
