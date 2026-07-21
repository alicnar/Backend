package com.spring.gamebackend.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TemporaryUser, String> {
}
