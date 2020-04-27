package com.video.yali.bean;

public class CheckVersionBean {


    /**
     * createdAt : 1570021951000
     * downloadUrl : https://ucan.25pp.com/PPAssistant_PP_2.apk
     * description : 1.优化订单显示\n2.解决数据加载异常问题\n3.优化无网络显示效果\n4.解决iPhoneX 兼容性问题\n5.修复定位错误问题
     * id : 1
     * version : v1.2.0
     * platform : Android
     * status : 2
     * updatedAt : 1570021953000
     */

    private long createdAt;
    private String downloadUrl;
    private String description;
    private int id;
    private String version;
    private String platform;
    private int status;
    private long updatedAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
