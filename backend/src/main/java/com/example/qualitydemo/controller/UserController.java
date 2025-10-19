package com.example.qualitydemo.controller;

import com.example.qualitydemo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Map<Long, User> store = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @GetMapping
    public List<User> list() {
        return new ArrayList<>(store.values());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        long id = idGen.getAndIncrement();
        user.setId(id);
        store.put(id, user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User u = store.get(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }
}
