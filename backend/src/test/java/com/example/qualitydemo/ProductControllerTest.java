package com.example.qualitydemo;

import com.example.qualitydemo.controller.ProductController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {
    @Test
    public void listContainsProducts() {
        ProductController pc = new ProductController();
        assertTrue(pc.list().size() >= 1);
    }
}
