package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("admin")
public class MainAdmin extends Member {

    @Id
    private String id;
    private String mainAdminId;

    public MainAdmin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainAdminId() {
        return mainAdminId;
    }

    public void setMainAdminId(String mainAdminId) {
        this.mainAdminId = mainAdminId;
    }
}
