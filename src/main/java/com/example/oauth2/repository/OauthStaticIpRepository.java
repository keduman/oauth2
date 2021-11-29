package com.example.oauth2.repository;

import com.example.oauth2.entity.OauthLog;
import com.example.oauth2.entity.OauthStaticIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthStaticIpRepository extends JpaRepository<OauthStaticIp,String> {

    Long countByIp(String ip);

}
