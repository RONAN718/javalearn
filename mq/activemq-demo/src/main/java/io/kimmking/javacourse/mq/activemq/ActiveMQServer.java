package io.kimmking.javacourse.mq.activemq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;

import static io.kimmking.javacourse.mq.activemq.ActivemqApplication.testDestination;

public class ActiveMQServer {

    public static void main(String[] args) throws Exception {
        // 尝试用java代码启动一个ActiveMQ broker server
        BrokerService broker = new BrokerService();
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        // 然后用前面的测试demo代码，连接这个嵌入式的server
        Destination destination = new ActiveMQQueue("test.queue");

        testDestination(destination);
    }
}
