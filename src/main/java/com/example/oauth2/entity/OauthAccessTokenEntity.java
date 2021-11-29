package com.example.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_access_token", uniqueConstraints = {@UniqueConstraint(columnNames = {"authentication", "authentication_id"})})
public class OauthAccessTokenEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private String tokenId;

    private byte[] token;

    @Column(name = "authentication_id")
    String authenticationId;

    @Column(name = "user_name", unique = true)
    String userName;

    @Column(name = "client_id")
    private String clientId;

    private byte[] authentication;

    @Column(name = "refresh_token")
    private String refreshToken;
}
