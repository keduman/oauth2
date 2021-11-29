package com.example.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OAUTH_STATIC_IP")
@Builder(builderMethodName = "Builder")
public class OauthStaticIp {

    @Id
    @Column(name = "IP")
    private String ip;
}
