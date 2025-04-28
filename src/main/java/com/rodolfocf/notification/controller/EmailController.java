package com.rodolfocf.notification.controller;

import com.rodolfocf.notification.business.EmailService;
import com.rodolfocf.notification.business.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody TaskDTO taskDTO){
        emailService.sendEmail(taskDTO);
        return ResponseEntity.ok().body("Email sent successfully");
    }

}
