package com.example.oauth2.service;

import com.example.oauth2.entity.OauthLog;
import org.springframework.scheduling.annotation.Async;

public interface OauthService {

    @Async
    void saveLog( OauthLog.OauthLogBuilder oauthLogBuilder);
}
