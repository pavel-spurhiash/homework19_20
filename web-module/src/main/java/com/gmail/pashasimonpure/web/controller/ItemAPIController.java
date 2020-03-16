package com.gmail.pashasimonpure.web.controller;

import java.util.List;
import javax.validation.Valid;

import com.gmail.pashasimonpure.service.ItemService;
import com.gmail.pashasimonpure.service.model.ItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemAPIController {

    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> findAll() {
        return itemService.findAll();
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO add(@Valid @RequestBody ItemDTO item) {
        return itemService.add(item);
    }

    @PutMapping("/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateStatusById(@PathVariable Long id, @Valid @RequestBody ItemDTO item) {
        itemService.updateStatusById(id, item.getStatus());
        return "Item successfully updated.";
    }

    @DeleteMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public Object deleteItemByStatus(@Valid @RequestBody ItemDTO item) {
        itemService.deleteAllByStatus(item.getStatus());
        return "Items successfully deleted.";
    }

}