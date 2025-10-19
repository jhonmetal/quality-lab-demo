package com.example.qualitydemo;

import com.example.qualitydemo.controller.UserController;
import com.example.qualitydemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    @Test
    public void createAndGetUser() {
        UserController uc = new UserController();
        User u = new User(null, "alice", "alice@example.com");
        ResponseEntity<com.example.qualitydemo.model.User> resp = uc.create(u);
        assertNotNull(resp.getBody());
        Long id = resp.getBody().getId();
        assertTrue(id > 0);
        ResponseEntity<com.example.qualitydemo.model.User> fetched = uc.get(id);
        assertEquals("alice", fetched.getBody().getUsername());
    }

    @Test
    public void getNonexistentReturns404() {
        UserController uc = new UserController();
        ResponseEntity<com.example.qualitydemo.model.User> r = uc.get(9999L);
        assertEquals(404, r.getStatusCodeValue());
    }
}
