package yuhan.hgcq.client.controller;

import android.content.Context;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import yuhan.hgcq.client.config.NetworkClient;
import yuhan.hgcq.client.model.dto.chat.ChatDTO;
import yuhan.hgcq.client.model.dto.chat.CreateChatForm;
import yuhan.hgcq.client.model.service.ChatService;

public class ChatController {

    private ChatService chatService;

    public ChatController(Context context) {
        NetworkClient client = NetworkClient.getInstance(context.getApplicationContext());
        chatService = client.getChatService();
    }

    /**
     * 채팅 생성
     *
     * @param createChatForm 채팅 생성 폼
     * @param callback       비동기 콜백
     */
    public void addChat(CreateChatForm createChatForm, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = chatService.addChat(createChatForm);
        call.enqueue(callback);
    }

    /**
     * 채팅 삭제
     *
     * @param chatDTO  채팅 DTO
     * @param callback 비동기 콜백
     */
    public void deleteChat(ChatDTO chatDTO, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = chatService.deleteChat(chatDTO);
        call.enqueue(callback);
    }

    /**
     * 채팅 리스트
     *
     * @param albumId  앨범 id
     * @param callback 비동기 콜백
     */
    public void chatList(Long albumId, Callback<List<ChatDTO>> callback) {
        Call<List<ChatDTO>> call = chatService.chatList(albumId);
        call.enqueue(callback);
    }
}
