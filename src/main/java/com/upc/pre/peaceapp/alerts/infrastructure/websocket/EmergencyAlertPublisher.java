package com.upc.pre.peaceapp.alerts.infrastructure.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.pre.peaceapp.alerts.domain.model.aggregates.Alert;
import com.upc.pre.peaceapp.alerts.domain.model.valueobjects.AlertType;
import com.upc.pre.peaceapp.alerts.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmergencyAlertPublisher {

    private final EmergencyAlertWebSocketHandler socketHandler;
    private final ObjectMapper objectMapper;

    public EmergencyAlertPublisher(EmergencyAlertWebSocketHandler socketHandler, ObjectMapper objectMapper) {
        this.socketHandler = socketHandler;
        this.objectMapper = objectMapper;
    }

    public void publishIfEmergency(Alert alert) {
        boolean isEmergency = alert.getType() == AlertType.EMERGENCY ||
                (alert.getDescription() != null && alert.getDescription().startsWith("[EMERGENCY]"));

        if (!isEmergency) return;

        try {
            var payload = objectMapper.writeValueAsString(
                    AlertResourceFromEntityAssembler.toResourceFromEntity(alert)
            );
            socketHandler.broadcast(payload);
        } catch (JsonProcessingException e) {
            log.warn("Could not serialize emergency alert {}", alert.getId(), e);
        }
    }
}
