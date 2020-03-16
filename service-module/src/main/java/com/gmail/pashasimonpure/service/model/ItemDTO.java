package com.gmail.pashasimonpure.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gmail.pashasimonpure.service.model.constant.ItemStatusEnum;

public class ItemDTO {

    private Long id;

    @Size(min = 5, max = 40)
    private String name;

    @NotNull
    private ItemStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemDTO{" + "id=" + id + ", name='" + name + ", status=" + status + '}';
    }

}