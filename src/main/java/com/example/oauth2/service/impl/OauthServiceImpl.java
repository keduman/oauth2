package com.example.oauth2.service.impl;

import com.example.oauth2.entity.OauthLog;
import com.example.oauth2.service.OauthService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class OauthServiceImpl implements OauthService {
    @Override
    public void saveLog(OauthLog.OauthLogBuilder oauthLogBuilder) {

    }

    @Override
    public boolean checkIp(String ip) {
        return false;
    }
}
