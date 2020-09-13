package com.github.saphyra.apphub.app.web.model.request;

public class RegistrationRequest {
    private final String email;
    private final String username;
    private final String password;

    public RegistrationRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
