package com.video.yali.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AdBean implements Parcelable {


    /**
     * image : http://static.1995519.com/images/20191213/lL2pzDMsDfhBdp9vbsZiERSI0I964CmQ6t2Htl7f.jpeg
     * target_url : http://www.baidu.com
     * id : 26
     * type : 2
     */

    private String image;
    private String target_url;
    private int id;
    private int type;

    protected AdBean(Parcel in) {
        image = in.readString();
        target_url = in.readString();
        id = in.readInt();
        type = in.readInt();
    }

    public static final Creator<AdBean> CREATOR = new Creator<AdBean>() {
        @Override
        public AdBean createFromParcel(Parcel in) {
            return new AdBean(in);
        }

        @Override
        public AdBean[] newArray(int size) {
            return new AdBean[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(target_url);
        dest.writeInt(id);
        dest.writeInt(type);
    }
}
