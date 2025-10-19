package com.example.qualitydemo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @GetMapping
    public List<Map<String,Object>> list() {
        return List.of(
            Map.of("id",1,"name","Laptop","price",1200),
            Map.of("id",2,"name","Phone","price",700)
        );
    }
}
