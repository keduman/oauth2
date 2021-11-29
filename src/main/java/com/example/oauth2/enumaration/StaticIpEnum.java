package com.example.oauth2.enumaration;

import java.util.Arrays;
import java.util.List;

public enum StaticIpEnum {
    IP_LIST(Arrays.asList("1.1.1.1","1.1.1.2"));

    List<String> ipBlock;

    StaticIpEnum(List<String> ipBlock) {
        this.ipBlock = ipBlock;
    }

    public List<String> getIpBlock() {
        return ipBlock;
    }
}
