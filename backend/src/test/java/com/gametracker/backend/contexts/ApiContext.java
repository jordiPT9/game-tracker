package com.gametracker.backend.contexts;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiContext {
    private String currentJwt;
    private ResponseEntity<String> latestResponse;

    public ApiContext() {
    }

    public ResponseEntity<String> getLatestResponse() {
        return latestResponse;
    }

    public void setLatestResponse(ResponseEntity<String> latestResponse) {
        this.latestResponse = latestResponse;
    }

    public String getCurrentJwt() {
        return currentJwt;
    }

    public void setCurrentJwt(String currentJwt) {
        this.currentJwt = currentJwt;
    }
}
