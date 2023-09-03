package com.sakurarealm.sakuraredeem.data.mysql.helper;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.PackageMapper;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemStackHelper {

    private static final ItemStackHelper INSTANCE = new ItemStackHelper();

    private ItemStackHelper() {

    }

    public static ItemStackHelper getInstance() {
        return INSTANCE;
    }

    synchronized public ItemStack[] loadItems(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            List<ItemStack> items = session.getMapper(ItemStackMapper.class).getAllItemStacks(packageName);
            return items.toArray(new ItemStack[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    synchronized public boolean saveItems(String packageName, ItemStack[] items) {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(ItemStackMapper.class).clearItemStacks(packageName);
            for (ItemStack item : items) {
                session.getMapper(ItemStackMapper.class).insertItemStack(packageName, item);
            }
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
