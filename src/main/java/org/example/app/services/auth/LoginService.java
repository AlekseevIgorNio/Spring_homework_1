package org.example.app.services.auth;

import org.example.web.dto.LoginForm;

public interface LoginService {

    boolean authenticate(LoginForm loginFrom);
}
