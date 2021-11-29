package com.example.oauth2.service;

import com.example.oauth2.entity.OauthLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

public interface OauthService {

    @Async
    void saveLog( OauthLog.OauthLogBuilder oauthLogBuilder);

    boolean checkIp(String ip);
}
