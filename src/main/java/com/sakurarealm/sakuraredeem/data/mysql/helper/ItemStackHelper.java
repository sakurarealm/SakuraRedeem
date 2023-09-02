package com.sakurarealm.sakuraredeem.data.mysql.helper;

import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemStackHelper {

    private static final ItemStackHelper INSTANCE = new ItemStackHelper();

    private ItemStackHelper() {

    }

    public static ItemStackHelper getInstance() {
        return INSTANCE;
    }

    public String itemToNBT(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = new NBTTagCompound();
        nmsItemStack.save(compound);
        return compound.toString();
    }

    public ItemStack nbtToItem(String nbt) {
        try {
            NBTTagCompound compound = MojangsonParser.parse(nbt);
            net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = new net.minecraft.server.v1_12_R1.ItemStack(compound);
            return CraftItemStack.asBukkitCopy(nmsItemStack);
        } catch (MojangsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
