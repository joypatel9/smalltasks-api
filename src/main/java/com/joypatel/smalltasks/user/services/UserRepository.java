package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByMobile(String mobile);

    Optional<User> findByMobile(String mobile);
}
