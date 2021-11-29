package com.example.oauth2.repository;

import com.example.oauth2.entity.OauthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthLogRepository extends JpaRepository<OauthLog,String> {
}
