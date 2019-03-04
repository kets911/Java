package com.pavel_katsuba.talks.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Message {
    @Id
    @GeneratedValue
    private long id;
    @JsonView
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
}
