package com.sakurarealm.sakuraredeem.data.mysql.helper;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                if (item != null)
                    session.getMapper(ItemStackMapper.class).insertItemStack(packageName, item);
            }
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] itemToNBT(ItemStack itemStack) {
        try {
            net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound compound = new NBTTagCompound();
            nmsItemStack.save(compound);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            NBTCompressedStreamTools.a(compound, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ItemStack nbtToItem(byte[] nbt) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(nbt);
            NBTTagCompound compound = NBTCompressedStreamTools.a(bais);
            net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = new net.minecraft.server.v1_12_R1.ItemStack(compound);
            return CraftItemStack.asBukkitCopy(nmsItemStack);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
