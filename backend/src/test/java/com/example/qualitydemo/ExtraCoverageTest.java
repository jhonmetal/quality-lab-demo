package com.example.qualitydemo;

import com.example.qualitydemo.controller.ProductController;
import com.example.qualitydemo.controller.UserController;
import com.example.qualitydemo.controller.PageController;
import com.example.qualitydemo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Extra Coverage Tests")
public class ExtraCoverageTest {

    @Test
    @DisplayName("Exercise ProductController list")
    public void exerciseProductController() {
        ProductController pc = new ProductController();
        List<Map<String, Object>> products = pc.list();
        assertEquals(2, products.size());
        Map<String, Object> p1 = products.get(0);
        assertEquals(1, p1.get("id"));
        assertEquals("Laptop", p1.get("name"));
        assertEquals(1200, p1.get("price"));
    }

    @Test
    @DisplayName("Exercise PageController views")
    public void exercisePageController() {
        PageController pg = new PageController();
        assertEquals("register", pg.register());
        assertEquals("login", pg.login());
    }

    @Test
    @DisplayName("Exercise UserController create and get paths and list")
    public void exerciseUserControllerPaths() {
        UserController uc = new UserController();
        User u = new User(null, "x", "x@x.com");
        ResponseEntity<User> r = uc.create(u);
        assertEquals(200, r.getStatusCodeValue());
        User created = r.getBody();
        assertNotNull(created);
        assertEquals("x", created.getUsername());

        Long id = created.getId();
        ResponseEntity<User> fetched = uc.get(id);
        assertEquals(200, fetched.getStatusCodeValue());
        assertEquals("x@x.com", fetched.getBody().getEmail());

        // fetch nonexistent
        ResponseEntity<User> notFound = uc.get(9999L);
        assertEquals(404, notFound.getStatusCodeValue());

        // list should contain created user
        List<User> list = uc.list();
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Exercise User model setters and getters")
    public void exerciseUserModel() {
        User u = new User();
        u.setId(10L);
        u.setUsername("name");
        u.setEmail("email@example.com");

        assertEquals(10L, u.getId());
        assertEquals("name", u.getUsername());
        assertEquals("email@example.com", u.getEmail());
    }
}
