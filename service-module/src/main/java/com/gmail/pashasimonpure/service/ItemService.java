package com.gmail.pashasimonpure.service;

import java.util.List;

import com.gmail.pashasimonpure.service.model.ItemDTO;
import com.gmail.pashasimonpure.service.model.constant.ItemStatusEnum;

public interface ItemService {

    ItemDTO add(ItemDTO item);

    List<ItemDTO> findAll();

    List<ItemDTO> findAllCompleted();

    List<ItemDTO> findAllByRole();

    boolean updateStatusById(Long id, ItemStatusEnum status);

    boolean deleteAllByStatus(ItemStatusEnum status);

}