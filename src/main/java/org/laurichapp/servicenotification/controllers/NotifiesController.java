package org.laurichapp.servicenotification.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifies")
public class NotifiesController {

    @GetMapping
    public ResponseEntity get() {
        // TO DO
        return ResponseEntity.ok().build();
    }
}
