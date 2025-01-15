package com.social.network.repository.postgres;

import com.social.network.entity.mongo.UserEvents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEventsRepository  extends MongoRepository<UserEvents, String> {
}
