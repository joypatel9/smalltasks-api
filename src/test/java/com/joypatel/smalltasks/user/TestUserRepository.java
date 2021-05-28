package com.joypatel.smalltasks.user;

import com.joypatel.smalltasks.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<User, Integer> {
}
