package com.example.janackoverflow.chat.controller;

import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.service.ChatMessageService;
import com.example.janackoverflow.chat.service.ChatRoomService;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatrooms")
@Slf4j
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatMessageService chatMessageService;

    //모든 채팅방 리스트
    @GetMapping
    public ResponseEntity<?> getAllChatRooms(){
        List<ChatRoomDTO.ResponseDTO> chatRoomDTOList = chatRoomService.readAll();
        return ResponseEntity.ok(chatRoomDTOList);
    }
    //내가 만든 채팅방(가능하면 들어가있는 채팅방으로 수정)
    @GetMapping("/my")
    public ResponseEntity<?> getMyChatRooms(@AuthenticationPrincipal NowUserDetails nowUserDetails){
        List<ChatRoomDTO.ResponseDTO> chatRoomDTOList = chatRoomService.readMyChatRooms(nowUserDetails.getUser().getId());

        return ResponseEntity.ok(chatRoomDTOList);
    }
    //채팅 내역 불러오기+입장
    @GetMapping("/{roomId}")
    public ResponseEntity<?> chatList(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                      @PathVariable(value = "roomId") Long roomId){
        Long userId = nowUserDetails.getUser().getId();

        ChatRoomDTO.ResponseDTO chatRoomDTO = chatRoomService.read(roomId);
        List<ChatMessageDTO.ResponseDTO> chatMessageList = chatMessageService.readAll(roomId);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("roomInfo", chatRoomDTO);
        responseMap.put("messageList", chatMessageList);
        return ResponseEntity.ok(responseMap);
    }
    //유저 입장
    @GetMapping("/enter/{roomId}")
    public ResponseEntity<?> enterChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                           @PathVariable(value = "roomId") Long roomId){


        Long userId = nowUserDetails.getUser().getId();

        //유저 수 다 찼을때 못들어오게 하기
        if(!chatRoomService.isMax(roomId)) {
            chatRoomService.enterChatRoom(roomId, userId);
            return ResponseEntity.ok("입장");
        } else {
            return ResponseEntity.badRequest().body("인원수 다 참");
        }



    }
    //유저 퇴장
    @DeleteMapping("/quit/{roomId}")
    public ResponseEntity<?> quitChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                          @PathVariable(value = "roomId") Long roomId){
        log.info("삭제됨");
        Long userId = nowUserDetails.getUser().getId();
        chatRoomService.quitChatRoom(roomId, userId);

        return ResponseEntity.ok("퇴장");
    }

    //채팅방 생성
    @PostMapping
    public ResponseEntity<?> createChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                            @RequestBody ChatRoomDTO.RequestDTO chatRoomDTO){
        Long userId = nowUserDetails.getUser().getId();
        ChatRoom chatRoom =chatRoomService.create(chatRoomDTO, userId);
        return ResponseEntity.ok(chatRoom);
    }
    //채팅방 삭제
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                                 @PathVariable(value = "roomId") Long roomId){
        log.info("실행됨");
        
            Long userId = nowUserDetails.getUser().getId();
            if(chatRoomService.delete(roomId, userId)){
                return ResponseEntity.ok("삭제되었습니다.");
            }
            else {
                return ResponseEntity.badRequest().body("채팅방 생성자가 아닙니다.");
            }

        }





}
