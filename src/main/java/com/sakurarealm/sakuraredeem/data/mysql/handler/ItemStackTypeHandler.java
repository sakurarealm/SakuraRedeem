package com.sakurarealm.sakuraredeem.data.mysql.handler;

import com.sakurarealm.sakuraredeem.data.mysql.helper.ItemStackHelper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.bukkit.inventory.ItemStack;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemStackTypeHandler extends BaseTypeHandler<ItemStack> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ItemStack parameter, JdbcType jdbcType) throws SQLException {
        String nbt = ItemStackHelper.getInstance().itemToNBT(parameter);
        ps.setString(i, nbt);
    }

    @Override
    public ItemStack getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ItemStackHelper.getInstance().nbtToItem(rs.getString(columnName));
    }

    @Override
    public ItemStack getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ItemStackHelper.getInstance().nbtToItem(rs.getString(columnIndex));
    }

    @Override
    public ItemStack getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ItemStackHelper.getInstance().nbtToItem(cs.getString(columnIndex));
    }
}
