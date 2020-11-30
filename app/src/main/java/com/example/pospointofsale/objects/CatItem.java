
package com.example.pospointofsale.objects;


public class CatItem {


    private String mCatimg;
    private String mCatname;
    private String mId;
    private String count;

    public CatItem(){
    };

    public CatItem(String mCatimg, String mCatname, String mId, String count) {
        this.mCatimg = mCatimg;
        this.mCatname = mCatname;
        this.mId = mId;
        this.count = count;
;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCatimg() {
        return mCatimg;
    }

    public void setCatimg(String catimg) {
        mCatimg = catimg;
    }

    public String getCatname() {
        return mCatname;
    }

    public void setCatname(String catname) {
        mCatname = catname;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
