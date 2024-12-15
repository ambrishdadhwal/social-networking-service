package com.social.network.repository;

import com.social.network.entity.UserEvents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEventsRepository  extends MongoRepository<UserEvents, String> {
}
