package com.example.oauth2.service.impl;

import com.example.oauth2.entity.OauthLog;
import com.example.oauth2.repository.OauthLogRepository;
import com.example.oauth2.repository.OauthStaticIpRepository;
import com.example.oauth2.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class OauthServiceImpl implements OauthService {

    @Autowired
    OauthLogRepository oauthLogRepository;

    @Autowired
    OauthStaticIpRepository oauthStaticIpRepository;

    @Override
    public void saveLog(OauthLog.OauthLogBuilder oauthLogBuilder) {
        oauthLogRepository.save(oauthLogBuilder.build());
    }

    @Override
    public boolean checkIp(String ip) {
        Long size = oauthStaticIpRepository.countByIp(ip);
        if(size > 0){
            return true;
        } else {
            return false;
        }
    }
}
