package wq.study.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activemq")
public class Producer {
//    @Autowired
//    private JmsMessagingTemplate jmsTemplate;
//
//    @Autowired
//    private Queue queue;
//
//    @Autowired
//    private Topic topic;
//
//    //发送queue类型消息
//    @GetMapping("/queue")
//    public void sendQueueMsg(String msg){
//        jmsTemplate.convertAndSend(queue, msg);
//    }
//
//    //发送topic类型消息
//    @GetMapping("/topic")
//    public void sendTopicMsg(String msg){
//        jmsTemplate.convertAndSend(topic, msg);
//    }

}