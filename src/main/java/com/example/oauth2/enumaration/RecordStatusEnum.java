package com.example.oauth2.enumaration;

public enum RecordStatusEnum {

    DELETED(0),
    ACTIVE(1),
    PASSIVE(2),
    SUSPENDED(3),
    EXPIRED(4),
    HIDDEN(5),
    NONAPPROVED(6);

    private final int code;

    RecordStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public RecordStatusEnum parse(final int code){
        for (final RecordStatusEnum recordStatusEnum : RecordStatusEnum.values()){
            if(code == recordStatusEnum.getCode()) {
                return recordStatusEnum;
            }
        }
        return null;
    }
}
