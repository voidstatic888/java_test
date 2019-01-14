package kafka_learn;

import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaProducerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerTest.class);

    private static ProducerCallback producerCallback = new ProducerCallback();

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Producer<byte[], byte[]> getCommonProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.5.128:9092");
        properties.put("acks", "-1");
        properties.put("retries", "3");
        properties.put("batch.size", "16384");
        properties.put("linger.ms", "1");
        properties.put("buffer.memory", "33554432");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        Producer<byte[], byte[]> producer = new KafkaProducer<byte[], byte[]>(properties);
        return producer;
    }

    private static class ProducerCallback implements Callback{
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null){
                e.printStackTrace();
            }
            if (recordMetadata != null){
                System.out.println(String.format("%s: 消息已发送，内容为：%s", simpleDateFormat.format(Calendar.getInstance().getTime()), recordMetadata.toString()));
            }
            LOGGER.info("发送完毕！");
        }
    }

    public static Future<RecordMetadata> sendMessage(Producer<byte[], byte[]> producer, String message) throws Exception{
        Future<RecordMetadata> res = producer.send(new ProducerRecord<byte[], byte[]>("test.message", message.getBytes("utf-8")), producerCallback);
        return res;
    }

    public static void main(String[] args){
        Producer<byte[], byte[]> producer = getCommonProducer();
        try{
            List<Future<RecordMetadata>> futureList = new ArrayList<>();
            String message = "测试-@@time";
            for (int i = 0; i < 100; i++){
                message = message.replace("@@time", i + "");
                futureList.add(sendMessage(producer, message));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
