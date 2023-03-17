package gdsc.netwalk.service.group.socket.kafka;

import gdsc.netwalk.domain.group.socket.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessageSendingOperations sendingOperations;

    @KafkaListener(topics = "testTopic", groupId = "testgroup", containerFactory = "kafkaListener")
    public void consume(ChatMessage message) {
        System.out.println("카프카 컨슈머 = " + message.getMessage());

        if ("ENTER".equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }

        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message );
    }
}