package com.example.qualitydemo.solved;

import com.example.qualitydemo.controller.UserController;
import com.example.qualitydemo.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolvedUserTests {
    @Test
    public void createMultipleUsersAndList() {
        UserController uc = new UserController();
        uc.create(new User(null,"u1","u1@example.com"));
        uc.create(new User(null,"u2","u2@example.com"));
        assertEquals(2, uc.list().size());
    }
}
