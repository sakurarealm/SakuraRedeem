package com.sakurarealm.sakuraredeem.data.mysql.helper;

import com.sakurarealm.sakuraredeem.utils.BukkitLogger;
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

    public String itemToNBT(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = new NBTTagCompound();
        nmsItemStack.save(compound);
        return compound.toString();
    }

    public ItemStack nbtToItem(String nbtString) {
        try {
            NBTTagCompound compound = MojangsonParser.parse(nbtString);
            net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = new net.minecraft.server.v1_12_R1.ItemStack(compound);
            return CraftItemStack.asBukkitCopy(nmsItemStack);
        } catch (Exception e) {
            BukkitLogger.error(String.format("An error occurred while deserializing the nbt data:\n%s", e));
            return null;
        }
    }

}
