package com.todomypet.userservice.repository;

import com.todomypet.userservice.config.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
