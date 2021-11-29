package com.example.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OAUTH_LOGIN_UNAUTHORIZED_IP")
@Builder(builderMethodName = "Builder")
public class OauthLoginUnauthorizedIpEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "IP")
    private String ip;

    @PrePersist
    public void prePrersist(){
        createDate = new Date();
    }
}
