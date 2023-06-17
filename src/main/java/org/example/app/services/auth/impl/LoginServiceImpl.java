package org.example.app.services.auth.impl;

import org.apache.log4j.Logger;
import org.example.app.services.auth.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = Logger.getLogger(LoginServiceImpl.class);

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        return loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123");
    }
}
