package com.example.oauth2.entity;

import com.example.oauth2.enumaration.LoginErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "OAUTH_LOG")
@Builder(builderMethodName = "Builder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthLog {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "IS_LOGIN_SUCCESS")
    private boolean isLoginSuccess;

    @Column(name = "OAUTH_ROLE")
    private String oauthRole;

    @Column(name = "ERROR_TYPE")
    @Enumerated(EnumType.STRING)
    private LoginErrorEnum loginErrorEnum;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "REMOTE_ADDRESS")
    private String remoteAddress;

    @PrePersist
    public void prePersist(){ createDate = new Date();}
}
