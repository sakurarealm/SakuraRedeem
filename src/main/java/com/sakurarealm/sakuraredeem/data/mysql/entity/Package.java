package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a package with its associated details.
 * <p>
 * Each package has a name, creation timestamp, list of items (represented as ItemStacks),
 * and a list of associated commands.
 * </p>
 */
public class Package {

    /**
     * The name of the package.
     */
    public String name;

    /**
     * The date and time when the package was created.
     */
    LocalDateTime created_at;

    /**
     * A list of items associated with the package.
     * <p>
     * The ItemStack is of org.bukkit.inventory.ItemStack instance.
     * </p>
     */
    List<ItemStack> items;

    /**
     * A list of commands associated with the package.
     */
    List<Command> commands;
}