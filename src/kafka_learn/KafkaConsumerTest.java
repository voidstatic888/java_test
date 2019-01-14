package kafka_learn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.data.SchemaAndValue;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.*;

public class KafkaConsumerTest {

    private static MyOffsetCommitCallback myOffsetCommitCallback = new MyOffsetCommitCallback();

    private static MyConsumerRebalanceListener myConsumerRebalanceListener = new MyConsumerRebalanceListener();

    public static Consumer<String, String> getCommonConsumer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.244.68.54:9094,10.244.68.55:9094,10.244.68.56:9094");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", "false");
        properties.put("group.id", "DataCompare-eaa66c4d120f45d09907");
        properties.put("max.poll.records", "2147483647");
        properties.put("receive.buffer.bytes", "65535");
        properties.put("fetch.message.max.bytes", "1000000");
        properties.put("auto.offset.reset", "earliest");
        return new KafkaConsumer<>(properties);
    }

    public static List<TopicPartition> getTopicPartitions(Consumer<String, String> consumer, String topic) throws Exception{
        List<TopicPartition> res = new ArrayList<>();
        List<PartitionInfo> infos = consumer.partitionsFor(topic);
        for (PartitionInfo partitionInfo: infos){
            res.add(new TopicPartition(topic, partitionInfo.partition()));
        }
        return res;
    }

    public static ConsumerRecords<String, String> getMessageByAssign(Consumer<String, String> consumer, List<TopicPartition> tps, String topic) throws Exception{
        Map<String, Long> positionInfos = new HashMap<>();
        consumer.assign(tps);
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(tps);
        for (TopicPartition topicPartition: tps){
            long position = consumer.position(topicPartition);
            positionInfos.put(topicPartition.partition()+"", position);
            positionInfos.put(topicPartition.partition() + "end", endOffsets.get(topicPartition));
            //System.out.println(String.format("[%d]分区的position为[%d], end为[%d]", topicPartition.partition(), position, endOffsets.get(topicPartition)));
        }
        //System.out.println(String.format("[%s]的各分区position为%s", topic, positionInfos.toString()));
        return consumer.poll(1000);
    }

    public static ConsumerRecords<String, String> getMessageBySubscribe(Consumer<String, String> consumer, List<TopicPartition> tps, String topic){
        consumer.subscribe(Collections.singletonList(topic));
        return consumer.poll(1000);
    }

    private static class MyOffsetCommitCallback implements OffsetCommitCallback{
        @Override
        public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
            if (map != null){
                for (Map.Entry<TopicPartition, OffsetAndMetadata> entry: map.entrySet()){
                    TopicPartition topicPartition = entry.getKey();
                    OffsetAndMetadata offsetAndMetadata = entry.getValue();
                    System.out.println(String.format("%s的[%d]分区消费情况为：%s", topicPartition.topic(), topicPartition.partition(), offsetAndMetadata.toString()));
                }
            }
            if (e != null){
                e.printStackTrace();
            }
            System.out.println("已提交消费位点！");
        }
    }

    private static class MyConsumerRebalanceListener implements ConsumerRebalanceListener{

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> collection) {
            System.out.println("发生rebalance了");
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> collection) {

        }
    }





    public static void main(String[] args) throws Exception{
        String topic = "unmatch_DataCompare-eaa66c4d120f45d09907";
        Consumer<String, String> consumer = getCommonConsumer();
        FileWriter fileWriter =new FileWriter(new File("D:/unmatchLogsAll.txt"));
        try{
            int count = 0;
            List<TopicPartition> tps = getTopicPartitions(consumer, topic);
            ConsumerRecords<String, String> records = getMessageByAssign(consumer, tps, topic);
            while (records.count() > 0){
                //System.out.println(String.format("获取的消息数为%d", records.count()));
                for (ConsumerRecord<String, String> consumerRecord: records){

                    //System.out.println(consumerRecord.value());
                    fileWriter.write(consumerRecord.value());
                    fileWriter.write("\r\n");
                    count ++;
                }
                records = getMessageByAssign(consumer, tps, topic);
            }
            System.out.println("count:" + count);
            //getMessageByAssign(consumer, tps, topic);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
            fileWriter.close();
        }
    }

    public static String transCsvData(String jsonData){
        JSONObject jsonObject = JSON.parseObject(jsonData);
        return null;
    }


}
