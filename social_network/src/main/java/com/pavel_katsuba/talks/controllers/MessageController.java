package com.pavel_katsuba.talks.controllers;

import com.pavel_katsuba.talks.beans.Message;
import com.pavel_katsuba.talks.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {
    private final MessageRepo messageRepo;
    private int idCount = 3;

    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> getMessages(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getMessage(@PathVariable("id") Message  message){
        return message;
    }

    @PostMapping
    public Message setMessage(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message updateMessage(@PathVariable("id") Message messageFromDB , @RequestBody Message message){
        BeanUtils.copyProperties(message, messageFromDB, "id");
        return messageRepo.save(messageFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }
}