package com.giansiccardi.AppChat.dtos;



public record AuthResponse(String jwt, String message, boolean status) {
}
