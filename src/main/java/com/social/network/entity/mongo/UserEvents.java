package com.social.network.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "user_events")
@Data
public class UserEvents {

    @Id
    private String id;
    private String name;
    private String email;
}
