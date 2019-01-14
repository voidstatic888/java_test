package com.suning;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @ClassName TreeCompare
 * @Description TODO
 * @Author 18070888
 * @Date 2018/9/27 10:16
 * @Version 1.0
 **/
public class TreeCompare {

    public static void main(String[] args) {

        TreeCompare treeCompare = new TreeCompare();
        treeCompare.test();

    }

    public void test(){
        Row rowSrc1 = new Row();
        rowSrc1.putColumn(new Column("int", "id", true, false, 1));
        rowSrc1.putColumn(new Column("varchar", "str", false, true, "string"));

        Row rowSrc2 = new Row();
        rowSrc2.putColumn(new Column("int", "id", true, false, 2));
        rowSrc2.putColumn(new Column("varchar", "str", false, true, "string2"));

        Row rowDst2 = new Row();
        rowDst2.putColumn(new Column("int", "id", true, false, 2));
        rowDst2.putColumn(new Column("varchar", "str", false, true, "string2"));

        Row rowDst1 = new Row();
        rowDst1.putColumn(new Column("int", "id", true, false, 1));
        rowDst1.putColumn(new Column("varchar", "str", false, true, "string"));

        List<Data> srcList = new ArrayList<>();
        Data data1 = new Data(0, 0L, "insert", rowSrc1);
        Data data2 = new Data(0, 1L, "delete", rowSrc2);
        srcList.add(data1);
        srcList.add(data2);

        List<Data> dstList = new ArrayList<>();
        Data data3 = new Data(0, 1L, "delete", rowDst2);
        Data data4 = new Data(0, 0L, "insert", rowDst1);
        dstList.add(data3);
        dstList.add(data4);

        System.out.println(isSame(srcList, dstList));



    }

    public boolean isSame(List<Data> listSrc, List<Data> listDst){
        Set<Data> set = new TreeSet<>();
        for(Data data: listSrc){
            set.add(data);
        }
        Iterator<Data> iterator = listDst.iterator();
        while (iterator.hasNext()){
            Data data = iterator.next();
            if(set.contains(data)){
                System.out.println(1);
                set.remove(data);
            }
        }
        return set.isEmpty();
    }


    class Data implements Comparable<Data> {

        private Integer partition;

        private Long offset;

        //数据库操作类型
        private String type;

        private Row row;

        public Data(Integer partition, Long offset, String type, Row row) {
            this.partition = partition;
            this.offset = offset;
            this.type = type;
            this.row = row;
        }

        public Integer getPartition() {
            return partition;
        }

        public void setPartition(Integer partition) {
            this.partition = partition;
        }

        public Long getOffset() {
            return offset;
        }

        public void setOffset(Long offset) {
            this.offset = offset;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Row getRow() {
            return row;
        }

        public void setRow(Row row) {
            this.row = row;
        }

        public boolean equals(Data data) {
            return  type.equals(data.getType()) && row.equals(data.getRow());
        }

        @Override
        public int compareTo(Data o) {
            //System.out.println(3);
            if(partition < o.getPartition()){
                System.out.println("不相等1");
                return -1;
            }else if(partition.equals(o.getPartition()) && offset < o.getOffset()){
                System.out.println("不相等2");
                return -1;
            }else if(partition.equals(o.getPartition()) && offset.equals(o.getOffset()) && equals(o)){
                System.out.println("相等");
                return 0;
            }
            System.out.println("不相等3");
            return 1;
        }
    }

    class Row{

        private List<Column> columns = new ArrayList<>();

        public void putColumn(Column column) {
            this.columns.add(column);
        }

        public List<Column> getColumns() {
            return columns;
        }

        public void setColumns(List<Column> columns) {
            this.columns = columns;
        }

        public boolean equals(Row row) {
            if(this == row){
                return true;
            }

            if(row == null){
                return false;
            }
            if(columns.size() != row.getColumns().size()){
                return false;
            }
            int count = 0;
            List<Column> otherColumns = row.getColumns();
            Iterator<Column> iterator = columns.iterator();
            while (iterator.hasNext()){
                Column column = iterator.next();
                for(Column columnOther: otherColumns){
                    if(column.equals(columnOther)){
                        count ++;
                        break;
                    }
                }
            }
            if(columns.size() > count){
                return false;
            }
            return true;
        }
    }

    class Column{

        /**
         * data type
         */
        private String  dataType = "";

        /**
         * column name
         */
        private String  name     = "";

        /**
         * 是否为主键
         */
        private boolean isKey;

        /**
         * 值是否为null
         */
        private boolean isNull;

        /**
         * 具体的内容
         */
        private Object  value;

        public Column(String dataType, String name, boolean isKey, boolean isNull, Object value) {
            this.dataType = dataType;
            this.name = name;
            this.isKey = isKey;
            this.isNull = isNull;
            this.value = value;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isKey() {
            return isKey;
        }

        public void setKey(boolean isKey) {
            this.isKey = isKey;
        }

        public boolean isNull() {
            return isNull;
        }

        public void setNull(boolean isNull) {
            this.isNull = isNull;
        }

        public String getValue() {
            return value.toString();
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "{dataType=" + dataType + ", name=" + name + ", isKey=" + isKey + ", isNull=" + isNull + ", value="
                    + value.toString() + "}";
        }


        public boolean equals(Column column) {
            boolean a = name.equals(column.getName());
            boolean b = dataType.equals(column.getDataType());
            boolean c = isKey == column.isKey();
            boolean d = isNull == column.isNull();
            boolean e = getValue().equals(column.getValue());
            return  a && b && c && d && e;
        }
    }
}
