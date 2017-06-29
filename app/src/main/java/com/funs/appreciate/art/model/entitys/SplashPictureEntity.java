package com.funs.appreciate.art.model.entitys;

/**
 * Created by yc on 2017/6/29.\
 * 启动 or 屏保
 */

public class SplashPictureEntity {

    /**
     * config : {"duration":"5","businessId":"ae08ae93211679947540b18dd0bccca1","id":"4","dataJson":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170627/2017062714033378ad142315ce7ae83887feb.jpg","type":"0","status":"0"}
     * status : 1
     */

    private ConfigBean config;
    private String status;

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ConfigBean {
        /**
         * duration : 5
         * businessId : ae08ae93211679947540b18dd0bccca1
         * id : 4
         * dataJson : http://img.ad.test.lhq-golive.com:7180/resource/image/20170627/2017062714033378ad142315ce7ae83887feb.jpg
         * type : 0
         * status : 0
         */

        private String duration;
        private String businessId;
        private String id;
        private String dataJson;
        private String type;
        private String status;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDataJson() {
            return dataJson;
        }

        public void setDataJson(String dataJson) {
            this.dataJson = dataJson;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
