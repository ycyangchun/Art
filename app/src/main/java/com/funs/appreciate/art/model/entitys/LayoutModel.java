package com.funs.appreciate.art.model.entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/6/21.
 */

public class LayoutModel {

    /**
     * layout : [{"topdistance":"0","columnid":"6","width":"560","id":"269","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816192078ad142315ce7ae83887fb7.png","contentid":"13","name":"女司机倒车冲玉器店 误伤男子要害","id":"21","type":"1","layoutid":"269"}],"height":"704","leftdistance":"0"},{"topdistance":"0","columnid":"6","leftid":"269","width":"270","id":"270","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816193578ad142315ce7ae83887fb6.jpg","contentid":"12","name":"内容3","id":"22","type":"0","layoutid":"270"}],"height":"484","leftdistance":"0"},{"topdistance":"0","columnid":"6","leftid":"270","width":"270","id":"271","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816193578ad142315ce7ae83887fb6.jpg","contentid":"11","name":"宋茜新星辣聊青春密事","id":"23","type":"1","layoutid":"271"}],"height":"484","leftdistance":"0"},{"topdistance":"0","columnid":"6","leftid":"271","width":"270","id":"272","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816203578ad142315ce7ae83887fb5.png","contentid":"10","name":"宋慧乔宋仲基同房遭非法拍摄","id":"24","type":"1","layoutid":"272"}],"height":"484","leftdistance":"0"},{"topdistance":"0","columnid":"6","leftid":"272","width":"270","id":"273","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816205778ad142315ce7ae83887fb4.png","contentid":"6","name":"石榴","id":"25","type":"0","layoutid":"273"}],"height":"484","leftdistance":"0"},{"topdistance":"0","columnid":"6","topid":"270","leftid":"269","width":"560","id":"274","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816205778ad142315ce7ae83887fb4.png","contentid":"9","name":"实拍女游客试戴翡翠手镯不慎摔断","id":"26","type":"1","layoutid":"274"}],"height":"200","leftdistance":"0"},{"topdistance":"0","columnid":"6","topid":"272","leftid":"274","width":"270","id":"275","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816213278ad142315ce7ae83887fb3.png","contentid":"8","name":"内容2","id":"19","type":"0","layoutid":"275"}],"height":"200","leftdistance":"0"},{"topdistance":"0","columnid":"6","topid":"273","leftid":"275","width":"270","id":"276","content":[{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816214478ad142315ce7ae83887fb2.png","contentid":"7","name":"内容1","id":"20","type":"0","layoutid":"276"}],"height":"200","leftdistance":"0"}]
     * column : [{"businessid":"18f84a1fc490b1d6bacee958dd51ffc9","name":"今日推荐","orderby":"1","id":"6"},{"businessid":"d971a5893b400955762b3a18d42fb584","name":"候鸟旅游","orderby":"2","id":"7"},{"businessid":"5093d4145e539c9c7d6364cafb1bb32c","name":"油画","orderby":"3","id":"8"}]
     * status : 1
     */

    private String status;
    private List<LayoutBean> layout;
    private List<ColumnBean> column;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LayoutBean> getLayout() {
        return layout;
    }

    public List<LayoutBean> getBottomLayout() {
        if(layout == null ) return  null;
        List<LayoutBean> list = new ArrayList<>();
        for(LayoutBean lb : layout){
            if("1".equals(lb.getIsbottom())){
                list.add(lb);
            }
        }
        return list;
    }

    public void setLayout(List<LayoutBean> layout) {
        this.layout = layout;
    }

    public List<ColumnBean> getColumn() {
        return column;
    }

    public void setColumn(List<ColumnBean> column) {
        this.column = column;
    }

    public static class LayoutBean {
        /**
         * topdistance : 0
         * columnid : 6
         * width : 560
         * id : 269
         * content : [{"surfaceimage":"http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816192078ad142315ce7ae83887fb7.png","contentid":"13","name":"女司机倒车冲玉器店 误伤男子要害","id":"21","type":"1","layoutid":"269"}]
         * height : 704
         * leftdistance : 0
         * leftid : 269
         * topid : 270
         */

        private String topdistance;
        private String columnid;
        private String width;
        private String id;
        private String height;
        private String leftdistance;
        private String leftid;
        private String topid;
        private String isbottom;
        private List<ContentBean> content;

        public String getIsbottom() {
            return isbottom;
        }

        public void setIsbottom(String isbottom) {
            this.isbottom = isbottom;
        }

        public String getTopdistance() {
            return topdistance;
        }

        public void setTopdistance(String topdistance) {
            this.topdistance = topdistance;
        }

        public String getColumnid() {
            return columnid;
        }

        public void setColumnid(String columnid) {
            this.columnid = columnid;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLeftdistance() {
            return leftdistance;
        }

        public void setLeftdistance(String leftdistance) {
            this.leftdistance = leftdistance;
        }

        public String getLeftid() {
            return leftid== null ? null : leftid;
        }

        public void setLeftid(String leftid) {
            this.leftid = leftid;
        }

        public String getTopid() {
            return topid == null ? null : topid;
        }

        public void setTopid(String topid) {
            this.topid = topid;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }



        public static class ContentBean {
            /**
             * surfaceimage : http://img.ad.test.lhq-golive.com:7180/resource/image/20170628/2017062816192078ad142315ce7ae83887fb7.png
             * contentid : 13
             * name : 女司机倒车冲玉器店 误伤男子要害
             * id : 21
             * type : 1
             * layoutid : 269
             */

            private String surfaceimage;
            private String contentid;
            private String name;
            private String id;
            private String type;
            private String layoutid;

            private String remark;


            public String getSurfaceimage() {
                return surfaceimage;
            }
            public void setSurfaceimage(String surfaceimage) {
                this.surfaceimage = surfaceimage;
            }

            public String getContentid() {
                return contentid;
            }

            public void setContentid(String contentid) {
                this.contentid = contentid;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLayoutid() {
                return layoutid;
            }

            public void setLayoutid(String layoutid) {
                this.layoutid = layoutid;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }


        }
    }

    public static class ColumnBean {
        /**
         * businessid : 18f84a1fc490b1d6bacee958dd51ffc9
         * name : 今日推荐
         * orderby : 1
         * id : 6
         */

        private String businessid;
        private String name;
        private String orderby;
        private String id;

        public String getBusinessid() {
            return businessid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderby() {
            return orderby;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public List<String> getColumnNames(){
        List<String> names = new ArrayList<>();
        names.clear();
        column = this.getColumn();
        if(column != null){
            for( ColumnBean cb : column){
                names.add(cb.getName());
            }
        }
        return names;
    }

    public List<String> getColumnIds(){
        List<String> names = new ArrayList<>();
        names.clear();
        column = this.getColumn();
        if(column != null){
            for( ColumnBean cb : column){
                names.add(cb.getId());
            }
        }
        return names;
    }
}
