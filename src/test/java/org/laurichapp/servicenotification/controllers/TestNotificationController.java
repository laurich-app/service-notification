package org.laurichapp.servicenotification.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.dtos.pagination.Pagination;
import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.enums.NotificationType;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.facades.FacadeNotificationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TestNotificationController extends TestConfigurationControlleurRest{
    @MockBean
    private FacadeNotificationImpl facadeNotification;

    @MockBean
    private Validator validator;

    private static String BASE_URL = "/notifications";

    // ===============================================================================================
    //                                   GET getAllNotifications
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllNotificationsUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si non admin : FORBIDDEN
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllNotificationsNotAdminForbidden(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL)
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    /**
     * Si violation : BAD_REQUEST
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllNotificationsViolation(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        Set<ConstraintViolation<PaginateRequestDTO>> mocked = mock(Set.class);
        doReturn(mocked).when(this.validator).validate(any(PaginateRequestDTO.class));
        doReturn(false).when(mocked).isEmpty();

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL)
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /**
     * Si admin et toutes les notifications trouvées : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllNotificationsOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        Paginate<NotificationOutDTO> p = new Paginate<>(List.of(), new Pagination(0, 10, 0));
        doReturn(p).when(facadeNotification).getAllNotifications(any());

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL)
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   GET getNotificationById
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetNotificationByIdUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL+"/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si non admin : FORBIDDEN
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetNotificationByIdNotAdminForbidden(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL+"/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    /**
     * Si notification non trouvée : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetNotificationByIdNotFoundAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        doThrow(NotificationNotFoundException.class).when(facadeNotification).getNotificationById("123");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL+"/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si admin et notification trouvée : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetNotificationByIdOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();

        NotificationOutDTO n = new NotificationOutDTO("id", "email@root.com", "pseudo", LocalDateTime.now(), NotificationEtat.EN_ATTENTE, NotificationType.MAIL, NotificationFonction.NOTIFIER_ADMIN_COMMANDE);
        doReturn(n).when(facadeNotification).getNotificationById("123");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get(BASE_URL+"/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
