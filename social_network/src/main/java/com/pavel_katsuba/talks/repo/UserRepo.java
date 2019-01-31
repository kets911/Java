package com.pavel_katsuba.talks.repo;

import com.pavel_katsuba.talks.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
