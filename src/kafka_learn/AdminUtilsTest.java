package kafka_learn;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.requests.MetadataResponse.PartitionMetadata;
import org.apache.kafka.common.requests.MetadataResponse.TopicMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.JavaConversions;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AdminUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUtilsTest.class);

    private static final String zkUrl = "192.168.5.128:2181";

    public static void holdForTwoSec() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    public static String createTopic(String topicName, int partitions, int replias){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, false);
            boolean isExists = AdminUtils.topicExists(zkUtils, topicName);
            if (isExists){
                return String.format("topic[%s]已经存在！", topicName);
            }
            AdminUtils.createTopic(zkUtils, topicName, partitions, replias, new Properties(), RackAwareMode.Enforced$.MODULE$);
            holdForTwoSec();
            isExists = AdminUtils.topicExists(zkUtils, topicName);
            if (isExists) {
                return String.format("topic[%s]创建成功！", topicName);
            } else {
                return String.format("topic[%s]创建失败！", topicName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("topic[%s]创建失败！", topicName);
        } finally {
            if (zkUtils != null) {
                zkUtils.close();
            }
        }
    }



    public static String deleteTopic(String topicName){
        ZkUtils zkUtils = null;
        try {
            System.setProperty("zookeeper.sasl.client", "false");
            zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, false);
            if (!AdminUtils.topicExists(zkUtils, topicName)){
                return String.format("topic[%s]不存在！无法删除！", topicName);
            }
            AdminUtils.deleteTopic(zkUtils, topicName);
            holdForTwoSec();
            if (!AdminUtils.topicExists(zkUtils, topicName)){
                return String.format("topic[%s]删除成功！", topicName);
            } else {
                return String.format("topic[%s]删除失败！", topicName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除topic时出错！";
        } finally {
            if (zkUtils != null) {
                zkUtils.close();
            }
        }
    }

    public static String listAllTopics(){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, false);
            List<String> topics = JavaConversions.seqAsJavaList(zkUtils.getAllTopics());
            return topics.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取topic列表时出错！";
        } finally {
            if (zkUtils != null) {
                zkUtils.close();
            }
        }
    }

    public static Integer getPartitionNum(String topicName){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, false);
            TopicMetadata topicMetadata = AdminUtils.fetchTopicMetadataFromZk(topicName, zkUtils);
            List<PartitionMetadata> partitionMetadataList = topicMetadata.partitionMetadata();
            int num = partitionMetadataList.size();
            if (num == 0){
                return -2;
            } else {
                return num;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (zkUtils != null) {
                zkUtils.close();
            }
        }
    }

    public static String listTopicAllConfig(String topicName){
        ZkUtils zkUtils = null;
        try {
            zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, false);
            Map<String, Properties> configs = JavaConversions.mapAsJavaMap(AdminUtils.fetchAllTopicConfigs(zkUtils));
            Properties properties = configs.get(topicName);
            if (properties != null) {
                return properties.toString();
            } else {
                return String.format("无此topic:[%s]", topicName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "获取topic参数时出错！";
        } finally {
            if (zkUtils != null) {
                zkUtils.close();
            }
        }
    }



    public static void main(String[] args) {
        //System.out.println(createTopic("test-1", 1, 1));
        //System.out.println(listTopicAllConfig("test"));
        System.out.println(listAllTopics());
        //System.out.println(deleteTopic("test-1"));
        System.out.println(getPartitionNum("test-1"));
    }
}
