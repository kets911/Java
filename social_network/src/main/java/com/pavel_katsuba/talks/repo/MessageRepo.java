package com.pavel_katsuba.talks.repo;

import com.pavel_katsuba.talks.beans.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
