package org.laurichapp.servicenotification.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.facades.FacadeNotification;
import org.laurichapp.servicenotification.facades.FacadeNotificationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final FacadeNotification facadeNotification;
    private final Validator validator;

    public NotificationController(FacadeNotificationImpl facadeNotification, @Autowired Validator validator) {
        this.facadeNotification = facadeNotification;
        this.validator = validator;
    }

    /*========== GET ==========*/
    @GetMapping
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity<Paginate<NotificationOutDTO>> getAllNotifications(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "sortDirection", required = false) Sort.Direction sortDirection) {

        PaginateRequestDTO paginateRequest = new PaginateRequestDTO(page, limit, sort, sortDirection);

        Set<ConstraintViolation<PaginateRequestDTO>> violations = this.validator.validate(paginateRequest);
        if(!violations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Paginate<NotificationOutDTO> notification = facadeNotification.getAllNotifications(paginateRequest);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity<NotificationOutDTO> getCommande(@PathVariable String id) {
        try {
            NotificationOutDTO notifications = facadeNotification.getNotificationById(id);
            return ResponseEntity.ok(notifications);
        } catch (NotificationNotFoundException c) {
            return ResponseEntity.notFound().build();
        }
    }
}
