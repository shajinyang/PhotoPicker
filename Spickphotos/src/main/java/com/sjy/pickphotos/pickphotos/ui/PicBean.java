package com.sjy.pickphotos.pickphotos.ui;

/**
 * Created by sjy on 2017/11/18.
 */

public class PicBean {
    private String id;
    private String path;
    private String thumbnailpath;
    private String name;
    private String size;
    private String displayname;
    private boolean isSel=false;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getSize() {

        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnailpath() {
        return thumbnailpath;
    }

    public void setThumbnailpath(String thumbnailpath) {
        this.thumbnailpath = thumbnailpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
