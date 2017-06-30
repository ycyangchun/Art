package com.funs.appreciate.art.model.entitys;

import java.util.List;

/**
 * Created by yc on 2017/6/30.
 *  内容
 */

public class ContentEntity {

    /**
     * data : {"surfaceimage":null,"bgimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062814173878ad142315ce7ae83887fce.png","name":"带你走进山水画廊","id":"3","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915570278ad142315ce7ae83887f75.png","name":"石榴","remark":"石榴","id":"6","type":"0"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915590478ad142315ce7ae83887f70.jpg","name":"宋茜新星辣聊青春密事","remark":null,"id":"11","type":"1"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915591278ad142315ce7ae83887f6f.jpg","name":"宋慧乔宋仲基同房遭非法拍摄","remark":null,"id":"10","type":"1"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915580578ad142315ce7ae83887f73.png","name":"风景","remark":"风景","id":"8","type":"0"}]}
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
         * surfaceimage : null
         * bgimage : http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062814173878ad142315ce7ae83887fce.png
         * name : 带你走进山水画廊
         * id : 3
         * content : [{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915570278ad142315ce7ae83887f75.png","name":"石榴","remark":"石榴","id":"6","type":"0"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915590478ad142315ce7ae83887f70.jpg","name":"宋茜新星辣聊青春密事","remark":null,"id":"11","type":"1"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915591278ad142315ce7ae83887f6f.jpg","name":"宋慧乔宋仲基同房遭非法拍摄","remark":null,"id":"10","type":"1"},{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170629/2017062915580578ad142315ce7ae83887f73.png","name":"风景","remark":"风景","id":"8","type":"0"}]
         */

        private Object surfaceimage;
        private String bgimage;
        private String name;
        private String id;
        private List<LayoutModel.LayoutBean.ContentBean> content;

        public Object getSurfaceimage() {
            return surfaceimage;
        }

        public void setSurfaceimage(Object surfaceimage) {
            this.surfaceimage = surfaceimage;
        }

        public String getBgimage() {
            return bgimage;
        }

        public void setBgimage(String bgimage) {
            this.bgimage = bgimage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<LayoutModel.LayoutBean.ContentBean> getContent() {
            return content;
        }

        public void setContent(List<LayoutModel.LayoutBean.ContentBean> content) {
            this.content = content;
        }


    }
}
