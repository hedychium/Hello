package com.peijie.repositories;

import com.peijie.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor{
}
