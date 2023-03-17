package gdsc.netwalk.service.group.socket;

import gdsc.netwalk.domain.group.socket.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        return null;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(Long roomId) {
        return null;
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        return null;
    }
}