package com.falcon.dpp.message.model.base;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public abstract class Model {

    @Id
    private String recordId;

    private Date recordCreated;

    private Date recordLastTimeEdited;

    public Model() {
        setRecordCreated(new Date());
        setRecordLastTimeEdited(new Date());
        setRecordId(UUID.randomUUID().toString());
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setRecordCreated(Date recordCreated) {
        this.recordCreated = recordCreated;
    }

    public void setRecordLastTimeEdited(Date recordLastTimeEdited) {
        this.recordLastTimeEdited = recordLastTimeEdited;
    }
}
