package com.gmail.pashasimonpure.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.pashasimonpure.model.Item;
import com.gmail.pashasimonpure.repository.ItemRepository;
import com.gmail.pashasimonpure.service.ItemService;
import com.gmail.pashasimonpure.service.model.ItemDTO;
import com.gmail.pashasimonpure.service.model.constant.ItemStatusEnum;
import com.gmail.pashasimonpure.service.model.constant.UserRoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDTO add(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convert(itemDTO);
                //returns item with id field:
                item = itemRepository.add(connection, item);
                connection.commit();
                return convert(item);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return itemDTO;
    }

    @Override
    public List<ItemDTO> findAll() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.findAll(connection);
                List<ItemDTO> itemsDTO = new ArrayList<>();
                for (Item item : items) {
                    ItemDTO itemDTO = convert(item);
                    itemsDTO.add(itemDTO);
                }
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ItemDTO> findAllCompleted() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.findAllCompleted(connection);
                List<ItemDTO> itemsDTO = new ArrayList<>();
                for (Item item : items) {
                    ItemDTO itemDTO = convert(item);
                    itemsDTO.add(itemDTO);
                }
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ItemDTO> findAllByRole() {
        List<ItemDTO> items = new ArrayList<>();

        if (hasRole(UserRoleEnum.ROLE_ADMIN)) {
            items = findAll();
        } else if (hasRole(UserRoleEnum.ROLE_USER)) {
            items = findAllCompleted();
        } else {
            throw new UnsupportedOperationException("Invalid role.");
        }
        return items;
    }

    @Override
    public boolean updateStatusById(Long id, ItemStatusEnum status) {
        boolean success = false;
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.updateStatusById(connection, id, status.toString());
                connection.commit();
                success = true;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return success;
    }

    @Override
    public boolean deleteAllByStatus(ItemStatusEnum status) {
        boolean success = false;
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.deleteAllByStatus(connection, status.toString());
                connection.commit();
                success = true;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return success;
    }

    private boolean hasRole(UserRoleEnum role) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(role.toString()));
    }

    private Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setStatus(itemDTO.getStatus().name());
        return item;
    }

    private ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setStatus(ItemStatusEnum.valueOf(item.getStatus()));
        return itemDTO;
    }

}