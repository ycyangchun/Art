package com.funs.appreciate.art.model.entitys;

/**
 * Created by yc on 2017/6/30.
 *  详情
 */

public class DetailEntity {

    /**
     * data : {"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915561578ad142315ce7ae83887f77.png","name":"美人如诗，草木如织","remark":"美人如诗，草木如织","id":"17","datajson":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915561878ad142315ce7ae83887f76.png"}
     * status : 1
     */

    private DataBean data;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * surfaceimage : http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915561578ad142315ce7ae83887f77.png
         * name : 美人如诗，草木如织
         * remark : 美人如诗，草木如织
         * id : 17
         * datajson : http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915561878ad142315ce7ae83887f76.png
         */

        private String surfaceimage;
        private String name;
        private String remark;
        private String id;
        private String datajson;

        public String getSurfaceimage() {
            return surfaceimage;
        }

        public void setSurfaceimage(String surfaceimage) {
            this.surfaceimage = surfaceimage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDatajson() {
            return datajson;
        }

        public void setDatajson(String datajson) {
            this.datajson = datajson;
        }
    }
}
