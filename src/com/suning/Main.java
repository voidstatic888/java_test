package com.suning;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by 18070888 on 2018/7/4.
 */
public class Main {

    public static String transferMysqlBit(byte[] bytes,int precision){
        if(bytes == null){
            return "null";
        }
        String temp = new BigInteger(1, bytes).toString(2);
        int count = precision - temp.length();
        for(int i = 0; i < count; i++){
            temp = "0" + temp;
        }
        return temp;
    }

    public void doy(){
    }

    public static void readFileByChannelNIO(String filePath, String toFilePath){
        try(FileInputStream fio = new FileInputStream(new File(filePath));
            FileOutputStream foo = new FileOutputStream(new File(toFilePath));
            FileChannel writeChannel = foo.getChannel();
            FileChannel fileChannel = fio.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            while (true){
                int eof = fileChannel.read(byteBuffer);
                if(eof == -1){
                    break;
                }
                byteBuffer.flip();
                byte[] bytes = byteBuffer.array();
                //String str = new String(bytes,0, eof, "GB2312");
                //System.out.print(str);
                writeChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }catch (FileNotFoundException ex1){
            ex1.printStackTrace();
        }catch (IOException ex2){
            ex2.printStackTrace();
        }

    }

    public static String trimSinkVersion(String src_version){
        if(src_version == null || "".equals(src_version)){
            return src_version;
        }
        int snapshotOffset = src_version.lastIndexOf("-SNAPSHOT");
        int jarOffset = src_version.lastIndexOf(".jar");
        if(snapshotOffset >= 0){
            return src_version.substring(0, snapshotOffset);
        }else{
            return src_version.substring(0, jarOffset);
        }
    }

    public static String transferPgNumber(String srcString){
        switch (srcString){
            case "int2": return "smallint";
            case "int4": return "integer";
            case "int8": return "bigint";
            case "float4": return "float";
            case "bpchar": return "char";
            default: return srcString;
        }
    }


    public static void main(String[] args) {
        Set<String> set = new TreeSet<>();
        set.add("item1");
        set.add("item2");
        System.out.println("res:" + set );

        Map<String, String> map111 = new HashMap<>();
        System.out.println(map111.get("123"));
        System.out.println(transferPgNumber("int2"));
        //long time = new Date(System.currentTimeMillis()).getTime();
        //System.out.println(new SimpleDateFormat("hh:mm:ss.SSSSSS").format(time));
        //System.out.println(new Date(System.currentTimeMillis()).getYear()+1900);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        //String nodeName = "/rdrs/jobId";
        //String[] node = nodeName.split("/");
        //System.out.println(node.length);
        //System.out.println(node);
        //System.out.println(transferMysqlBit(new byte[]{1, 2}, 1));
        //readFileByChannelNIO("test/log1.txt", "test/logbak.txt");
        //System.out.println(trimSinkVersion("rdrs-synchz-2.8.0-SNAPSHOT.jar"));
        //System.out.println(trimSinkVersion("rdrs-synchz-2.8.0.jar"));
        //List<String> list = new ArrayList<>();
        //list.add("admin");
        //list.add("costom");
        //System.out.println(list.contains("admi"));
        //System.out.println(String.join(",", list));
        //if(true){
        //    System.out.println(1);
        //}else if(true){
        //    System.out.println(2);
        //}
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<String> a = new ArrayList<>();
        a.add("c");
        a.add("d");
        System.out.println(a);
        List<String> b = new ArrayList<>();
        b.add("a");
        b.add("b");
        b.addAll(a);
        System.out.println(b);
        System.out.println(Long.valueOf("456"));
        System.out.println(new Integer(2));
        Map<String, String> map = new HashMap<>();
        System.out.println(map.get("asd"));
        for(int i = 0; i < 10;){
            try{
                StringBuilder str = new StringBuilder();
            }catch (Exception e){

            }finally {
                System.out.println((i++) + "hello");
            }
        }
        int[] array = new int[]{1, 2, 3};
        Long longNum = 3L;
        String longStr = longNum + "";
        System.out.println("longStr:" + longStr);
        System.out.println(Long.valueOf(longStr) == 3L);
        System.out.println(System.currentTimeMillis());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(1535990595860L));
        System.out.println(new StringBuilder().length());
        System.out.println("cpu核心数：" + Runtime.getRuntime().availableProcessors());
        System.out.println(Math.ceil(-1.4));
        System.out.println(Math.floor(-1.4));
        System.out.println(Math.round(-1.4));
        System.out.println(Math.ceil(-1.5));
        System.out.println(Math.floor(-1.5));
        System.out.println(Math.round(-1.5));
        System.out.println(new HashMap<String, String>().get("a"));
    }

}
