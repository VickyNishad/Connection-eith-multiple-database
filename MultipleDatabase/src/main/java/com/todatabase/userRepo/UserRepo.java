package com.todatabase.userRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todatabase.userModel.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
