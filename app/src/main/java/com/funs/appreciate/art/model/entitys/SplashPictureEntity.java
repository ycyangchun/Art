package com.funs.appreciate.art.model.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yc on 2017/6/29.\
 * 启动 or 屏保
 */

public class SplashPictureEntity {

    private ConfigBean config;
    private String status;

    /**
     * config : {"duration":"5","screenSaverTime":"60","businessId":"9f9499363a40f6d4e0768c3328bf5808","id":"32","dataJson":[{"imgUrl":"http:07-05 16:33:22.935 21066-8721/com.funs.appreciate.art D/OkHttp: .jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/201707041745582069a1775e05836912f2e05e18202170.jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/201707041745582b75fddea9e32e41aa533b68dc4d784d.jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/2017070516105985a34d5dac7dbbb68b742117f29762a8.jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/20170705161102c6edbb64b5f1b7050034ea3c54a811ed.jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/201707051618358b28ff659e6d069fd41a648fff2b75b4.png"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/2017070516183685ff0d8a4bcecad36cd6e6c8b88f9e34.jpg"},{"imgUrl":"http://hksytest.oss-cn-beijing.aliyuncs.com/201707051618388551d2a0e754614de5f2c9334c9d72f0.jpg"}],"type":"1","status":"0"}
     */

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

        private String screenSaverTime;

        private List<DataJsonBean> imageArray;

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

        public String getScreenSaverTime() {
            return screenSaverTime;
        }

        public void setScreenSaverTime(String screenSaverTime) {
            this.screenSaverTime = screenSaverTime;
        }

        public List<DataJsonBean> getImageArray() {
            return imageArray;
        }

        public void setImageArray(List<DataJsonBean> imageArray) {
            this.imageArray = imageArray;
        }

        public static class DataJsonBean {
            /**
             * imgUrl : http:07-05 16:33:22.935 21066-8721/com.funs.appreciate.art D/OkHttp: .jpg
             */

            private String imgUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }

}
