package com.gmail.pashasimonpure.web.controller;

import java.util.List;

import com.gmail.pashasimonpure.service.ItemService;
import com.gmail.pashasimonpure.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/items")
    public String showItems(Model model) {
        List<ItemDTO> items = itemService.findAllByRole();
        model.addAttribute("items", items);
        return "items";
    }

}