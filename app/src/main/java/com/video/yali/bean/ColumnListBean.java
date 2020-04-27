package com.video.yali.bean;

import java.util.List;

public class ColumnListBean {


    /**
     * count : 15
     * list : [{"desc":"牢狱风云","dislike_count":0,"download_count":0,"download_url":"https://yingxiong.qiling-yongjiu.com/20191203/14560_befa3f6b/index.m3u8","duration":"14:13:32","favorite_count":0,"id":1093,"like_count":0,"name":"牢狱风云","play_count":2,"play_url":"https://yingxiong.qiling-yongjiu.com/20191203/14560_befa3f6b/index.m3u8","poster":"http://static.1995519.com/images/20191213/L2dWp9wABPt4jlVtZ7ag3nJMrboJBLJR1wDFlDjk.png","poster_v":"http://static.1995519.com/images/20191213/BxlnxX6qiFcZWZLeqcXbRYv1NBZ5QE5LfjaLDbLK.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]},{"desc":"恐怖份子","dislike_count":0,"download_count":0,"download_url":"https://yingxiong.qiling-yongjiu.com/20191203/14558_116c0758/index.m3u8","duration":"14:12:54","favorite_count":0,"id":1092,"like_count":0,"name":"恐怖份子","play_count":0,"play_url":"https://yingxiong.qiling-yongjiu.com/20191203/14558_116c0758/index.m3u8","poster":"http://static.1995519.com/images/20191213/BJ4bJsAmXQ2Az5qZA5dewEOq6xReYJOc8lpZRJN3.png","poster_v":"http://static.1995519.com/images/20191213/HuvMDlWTGoKQ3wmP2ZiKhKsIRc5A818prQYsggnr.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]},{"desc":"纽约的一个雨天","dislike_count":0,"download_count":0,"download_url":"https://yingxiong.qiling-yongjiu.com/20191209/15214_adc37564/index.m3u8","duration":"14:12:06","favorite_count":0,"id":1090,"like_count":0,"name":"纽约的一个雨天","play_count":3,"play_url":"https://yingxiong.qiling-yongjiu.com/20191209/15214_adc37564/index.m3u8","poster":"http://static.1995519.com/images/20191213/oQtp1ZmWFGoCCYZaspocjdlM9CXHenHTAdGaCWGP.png","poster_v":"http://static.1995519.com/images/20191213/VadSwUX33sRyeqCT4Sxo37FBTIhltM6WydYyZuf7.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]},{"desc":"行骗天下JP：浪漫篇","dislike_count":0,"download_count":0,"download_url":"https://txxs.mahua-yongjiu.com/20191210/7119_c03a3c4c/index.m3u8","duration":"13:46:59","favorite_count":0,"id":1089,"like_count":0,"name":"行骗天下JP：浪漫篇","play_count":0,"play_url":"https://txxs.mahua-yongjiu.com/20191210/7119_c03a3c4c/index.m3u8","poster":"http://static.1995519.com/images/20191213/ebz3lF37bLD2jp2F6aPoiGzeV1lt1vuXcOzuzptn.png","poster_v":"http://static.1995519.com/images/20191213/B9lqN2UAw9CbaE9FEzF7mjkY0nIFaKMl11ApbV9a.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]},{"desc":"自杀羽衣甘蓝","dislike_count":0,"download_count":0,"download_url":"https://txxs.mahua-yongjiu.com/20191211/7243_44298c18/index.m3u8","duration":"13:45:01","favorite_count":0,"id":1080,"like_count":0,"name":"自杀羽衣甘蓝","play_count":3,"play_url":"https://txxs.mahua-yongjiu.com/20191211/7243_44298c18/index.m3u8","poster":"http://static.1995519.com/images/20191213/eqnJqwzPpDD3yzD2I9JFR4AfNGTaUYBXLMUbqafE.png","poster_v":"http://static.1995519.com/images/20191213/Kl16bmrc2KtqxW5tzSgFCeZQdn3F4s9XCVSgNutr.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]},{"desc":"河妖","dislike_count":0,"download_count":0,"download_url":"https://yingxiong.qiling-yongjiu.com/20191212/15621_95ac0771/index.m3u8","duration":"13:44:42","favorite_count":0,"id":1079,"like_count":0,"name":"河妖","play_count":5,"play_url":"https://yingxiong.qiling-yongjiu.com/20191212/15621_95ac0771/index.m3u8","poster":"http://static.1995519.com/images/20191213/e5WN5MwuowQJ4QDSnOwrDrfgpHLi5hlm0AqYHuHk.png","poster_v":"http://static.1995519.com/images/20191213/vOlAAaQZUU91t1dbHPp YWKQlOa1Je3b4nRhPxOcd.png","published_at":"2019-12-13 00:00:00","rate":0,"tags":[]}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        /**
         * desc : 牢狱风云
         * dislike_count : 0
         * download_count : 0
         * download_url : https://yingxiong.qiling-yongjiu.com/20191203/14560_befa3f6b/index.m3u8
         * duration : 14:13:32
         * favorite_count : 0
         * id : 1093
         * like_count : 0
         * name : 牢狱风云
         * play_count : 2
         * play_url : https://yingxiong.qiling-yongjiu.com/20191203/14560_befa3f6b/index.m3u8
         * poster : http://static.1995519.com/images/20191213/L2dWp9wABPt4jlVtZ7ag3nJMrboJBLJR1wDFlDjk.png
         * poster_v : http://static.1995519.com/images/20191213/BxlnxX6qiFcZWZLeqcXbRYv1NBZ5QE5LfjaLDbLK.png
         * published_at : 2019-12-13 00:00:00
         * rate : 0
         * tags : []
         */
        private int  movie_id;

        public int getMovie_id() {
            return movie_id;
        }

        public void setMovie_id(int movie_id) {
            this.movie_id = movie_id;
        }

        private String updated_at;
        private String description;
        private String desc;
        private int dislike_count;
        private int download_count;
        private String download_url;
        private String duration;
        private int favorite_count;
        private int id;
        private int like_count;
        private String name;
        private int play_count;
        private String play_url;
        private String poster;
        private String poster_v;
        private String published_at;
        private float rate;
        private List<?> tags;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getDislike_count() {
            return dislike_count;
        }

        public void setDislike_count(int dislike_count) {
            this.dislike_count = dislike_count;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getPoster_v() {
            return poster_v;
        }

        public void setPoster_v(String poster_v) {
            this.poster_v = poster_v;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
