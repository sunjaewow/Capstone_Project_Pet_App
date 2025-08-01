package com.example.PetApp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chat")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    public enum ChatRoomType {
        ONE, MANY
    }

    @Id
    private String id;
    private MessageType messageType;
    private ChatRoomType chatRoomType;
    private Long chatRoomId;
    private Long senderId;
    private String senderName;
    private String senderImageUrl;
    private String message;
    private List<Long> users;
    private int chatUnReadCount;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime messageTime;//포맷 필요함.

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", messageType=" + messageType +
                ", chatRoomType=" + chatRoomType +
                ", chatRoomId=" + chatRoomId +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", senderImageUrl='" + senderImageUrl + '\'' +
                ", message='" + message + '\'' +
                ", messageTime=" + messageTime +
                '}';
    }
}
