package io.jenkins.plugins.wechat.model;

import java.util.List;

public class weChatData {
    /**
     * touser : 9873f5c0855b834958cf262daef10964|9d411f31f18ec99d4b8b65353deaca04
     * toparty :
     * totag :
     * msgtype : news
     * agentid : 1000003
     * news : {"articles":[{"title":"爱康_Android ","description":"爱康体检宝v3.1打包时间 2019-10-1","url":"https://www.pgyer.com/oVBU","picurl":"http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"}]}
     * enable_id_trans : 0
     */

    private String touser;
    private String toparty;
    private String totag;
    private String msgtype;
    private int agentid;
    private NewsBean news;
    private int enable_id_trans;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public int getEnable_id_trans() {
        return enable_id_trans;
    }

    public void setEnable_id_trans(int enable_id_trans) {
        this.enable_id_trans = enable_id_trans;
    }

    public static class NewsBean {
        private List<ArticlesBean> articles;

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlesBean> articles) {
            this.articles = articles;
        }

        public static class ArticlesBean {
            /**
             * title : 爱康_Android
             * description : 爱康体检宝v3.1打包时间 2019-10-1
             * url : https://www.pgyer.com/oVBU
             * picurl : http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png
             */

            private String title;
            private String description;
            private String url;
            private String picurl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }

            @Override
            public String toString() {
                return "ArticlesBean{" +
                        "title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", url='" + url + '\'' +
                        ", picurl='" + picurl + '\'' +
                        '}';
            }
        }
    }
}

