package com.example.janackoverflow;


import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.service.ChatMessageService;
import com.example.janackoverflow.chat.service.ChatRoomService;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.main.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
class JanackOverflowApplicationTests {

	@Autowired
	ChatMessageService chatMessageService;
	@Autowired
	ChatRoomService chatRoomService;

	@Test
	void contextLoads() {
		ChatRoom chatRoom = chatRoomService.create("방3", 3L);
		ChatMessageDTO.RequestDTO chatMessageDTO = ChatMessageDTO.RequestDTO.builder()
				.type(ChatMessageDTO.MessageType.TALK)
				.roomId(chatRoom.getRoomId())
				.content("내용.")
				.userId(1L)
				.createdAt(LocalDateTime.now())
				.build();

		ChatMessage chatMessage = chatMessageService.create(chatMessageDTO);
		System.out.println(chatMessage);
		chatRoomService.readAll().forEach(System.out::println);
	}


}
