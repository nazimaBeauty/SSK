package com.example.SSK.repo;

import com.example.SSK.model.UserMessages;
import org.springframework.data.repository.CrudRepository;

public interface UserMessageR extends CrudRepository<UserMessages, Long> {
}
