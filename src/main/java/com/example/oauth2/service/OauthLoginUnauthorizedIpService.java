package com.example.oauth2.service;

import org.springframework.stereotype.Service;

public interface OauthLoginUnauthorizedIpService {
    void save (String ip);
}
