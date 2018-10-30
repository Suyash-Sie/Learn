package com.iris.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iris.webapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
