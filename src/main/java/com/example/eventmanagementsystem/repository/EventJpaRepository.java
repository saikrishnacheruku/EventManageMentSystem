package com.example.eventmanagementsystem.repository;/*
                                                     * You can use the following import statements
                                                     *
                                                     * import org.springframework.data.jpa.repository.JpaRepository;
                                                     * import org.springframework.stereotype.Repository;
                                                     * 
                                                     */

// Write your code here

import com.example.eventmanagementsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Integer> {
}