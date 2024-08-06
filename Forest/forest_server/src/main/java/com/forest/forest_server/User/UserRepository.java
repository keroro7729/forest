package com.forest.forest_server.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ForestUser, Long> {
}
