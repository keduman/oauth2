package com.example.oauth2.repository;

import com.example.oauth2.entity.OauthLoginUnauthorizedIpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthLoginUnauthorizedRepository extends JpaRepository<OauthLoginUnauthorizedIpEntity,String> {
}
