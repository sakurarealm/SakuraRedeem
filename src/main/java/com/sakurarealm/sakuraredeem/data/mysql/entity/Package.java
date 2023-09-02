package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.List;

public class Package {

    public String name;

    LocalDateTime created_at;

    List<ItemStack> items;

    List<String> commands;

}
