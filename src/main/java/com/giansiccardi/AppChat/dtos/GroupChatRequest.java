package com.giansiccardi.AppChat.dtos;

import java.util.List;

public record GroupChatRequest(List<Long> customersIds ,String chat_name ,String chat_image) {
}
