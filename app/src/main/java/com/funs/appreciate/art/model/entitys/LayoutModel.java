package com.funs.appreciate.art.model.entitys;

import java.util.List;

/**
 * Created by yc on 2017/6/21.
 */

public class LayoutModel {

    /**
     * status : 200
     * layout : [{"id":"13","height":"704","leftdistance":"0","leftid":null,"topdistance":"0","width":"560","colorid":null,"columnid":"5","topid":null},{"id":"14","height":"347","leftdistance":"0","leftid":"13","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":null},{"id":"15","height":"347","leftdistance":"0","leftid":"13","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":"14"},{"id":"16","height":"704","leftdistance":"0","leftid":"14","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":null},{"id":"17","height":"484","leftdistance":"0","leftid":"16","topdistance":"0","width":"560","colorid":null,"columnid":"5","topid":null},{"id":"18","height":"200","leftdistance":"0","leftid":"16","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":"17"},{"id":"19","height":"200","leftdistance":"0","leftid":"18","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":"17"},{"id":"20","height":"704","leftdistance":"0","leftid":"17","topdistance":"0","width":"270","colorid":null,"columnid":"5","topid":null}]
     * column : [{"id":"5","businessid":"fb4711b43ff7af1acb740504534cb3f5","name":"精品推荐","orderby":"1"}]
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
         * id : 13
         * height : 704
         * leftdistance : 0
         * leftid : null
         * topdistance : 0
         * width : 560
         * colorid : null
         * columnid : 5
         * topid : null
         */

        private String id;
        private String height;
        private String leftdistance;
        private Object leftid;
        private String topdistance;
        private String width;
        private Object colorid;
        private String columnid;
        private Object topid;

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

        public Object getLeftid() {
            return leftid  = leftid == null ? 0 : leftid;
        }

        public void setLeftid(Object leftid) {
            this.leftid = leftid;
        }

        public String getTopdistance() {
            return topdistance;
        }

        public void setTopdistance(String topdistance) {
            this.topdistance = topdistance;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public Object getColorid() {
            return colorid;
        }

        public void setColorid(Object colorid) {
            this.colorid = colorid;
        }

        public String getColumnid() {
            return columnid;
        }

        public void setColumnid(String columnid) {
            this.columnid = columnid;
        }

        public Object getTopid() {
            return topid = topid == null ? 0 : topid;
        }

        public void setTopid(Object topid) {
            this.topid = topid;
        }
    }

    public static class ColumnBean {
        /**
         * id : 5
         * businessid : fb4711b43ff7af1acb740504534cb3f5
         * name : 精品推荐
         * orderby : 1
         */

        private String id;
        private String businessid;
        private String name;
        private String orderby;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
    }
}
