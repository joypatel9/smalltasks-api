package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Integer> {
    
    boolean existsByMobile(String mobile);
}
