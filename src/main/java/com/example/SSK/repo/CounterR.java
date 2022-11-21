package com.example.SSK.repo;

import com.example.SSK.model.CounterM;
import org.springframework.data.repository.CrudRepository;

public interface CounterR extends CrudRepository<CounterM, Long> {
}
