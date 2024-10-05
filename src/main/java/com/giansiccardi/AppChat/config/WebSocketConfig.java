package com.giansiccardi.AppChat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Especificar orígenes
                .withSockJS(); // Habilita SockJS como un fallback para navegadores que no soportan WebSocket.
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app");    // Define el prefijo que se utilizará para las rutas de destino de la aplicación. Los mensajes enviados desde el cliente se deben dirigir a esta ruta.

        // Habilita un corredor de mensajes simple para gestionar los mensajes que se envían a los clientes a través de las rutas especificadas.
        registry.enableSimpleBroker("/topic");

    //    registry.setUserDestinationPrefix("/customer");    // Establece un prefijo para los destinos de usuario. Esto se utiliza para mensajes dirigidos a usuarios específicos.
    }
}
