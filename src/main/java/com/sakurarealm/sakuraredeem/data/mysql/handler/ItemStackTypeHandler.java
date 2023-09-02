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
        String serializedNBT = ItemStackHelper.getInstance().itemToNBT(parameter);
        ps.setString(i, serializedNBT);
    }

    @Override
    public ItemStack getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String serializedNBT = rs.getString(columnName);
        return ItemStackHelper.getInstance().nbtToItem(serializedNBT);
    }

    @Override
    public ItemStack getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String serializedNBT = rs.getString(columnIndex);
        return ItemStackHelper.getInstance().nbtToItem(serializedNBT);
    }

    @Override
    public ItemStack getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String serializedNBT = cs.getString(columnIndex);
        return ItemStackHelper.getInstance().nbtToItem(serializedNBT);
    }
}
