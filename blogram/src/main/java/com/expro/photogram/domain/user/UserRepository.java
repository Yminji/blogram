package com.expro.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
	boolean existsByName(String name);
}
