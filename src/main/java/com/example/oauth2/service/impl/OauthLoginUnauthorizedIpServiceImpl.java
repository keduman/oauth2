package com.example.oauth2.service.impl;

import com.example.oauth2.entity.OauthLoginUnauthorizedIpEntity;
import com.example.oauth2.repository.OauthLoginUnauthorizedRepository;
import com.example.oauth2.service.OauthLoginUnauthorizedIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthLoginUnauthorizedIpServiceImpl implements OauthLoginUnauthorizedIpService {
    @Autowired
    OauthLoginUnauthorizedRepository repository;

    @Override
    public void save(String ip) {
        repository.save(OauthLoginUnauthorizedIpEntity.Builder().ip(ip).build());
    }
}
