package gdsc.netwalk.controller.group.socket;

import gdsc.netwalk.domain.group.socket.ChatMessage;
import gdsc.netwalk.service.group.socket.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService producerService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        producerService.sendMessage(message);
    }
}