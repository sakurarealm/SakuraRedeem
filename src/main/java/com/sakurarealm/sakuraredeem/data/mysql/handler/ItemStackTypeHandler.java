package com.sakurarealm.sakuraredeem.data.mysql.handler;

import com.sakurarealm.sakuraredeem.data.mysql.helper.ItemStackHelper;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;
import java.sql.*;

public class ItemStackTypeHandler extends BaseTypeHandler<ItemStack> {

    /**
     * Converts a Blob into an ItemStack representation.
     * <p>
     * Utilizes the ItemStackHelper's nbtToItem method to parse the Blob and produce the corresponding ItemStack.
     * If the provided Blob is null, the function returns null.
     * </p>
     *
     * @param blob The Blob representation of an ItemStack.
     * @return The ItemStack corresponding to the given Blob, or null if the Blob is null.
     * @throws SQLException If there's an error accessing the Blob data.
     */
    private ItemStack fromBlob(Blob blob) throws SQLException {
        if (blob == null)
            return null;
        return ItemStackHelper.getInstance().nbtToItem(blob.getBytes(1, (int) blob.length()));
    }

    /**
     * Sets the given ItemStack as a non-null parameter in the PreparedStatement.
     * <p>
     * This method converts an ItemStack to its NBT (Named Binary Tag) representation in byte format
     * using the ItemStackHelper utility. The byte data is then wrapped in a Blob and set into the
     * PreparedStatement at the specified index.
     * </p>
     *
     * @param ps         The PreparedStatement to set the ItemStack parameter in.
     * @param i          The index at which the ItemStack parameter should be set.
     * @param parameter  The ItemStack to be set into the PreparedStatement.
     * @param jdbcType   The JDBC type of the parameter. This parameter is currently unused but may be required for
     *                   overriding from a base class or interface.
     * @throws SQLException If there's an error setting the Blob data or other database-related issues.
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ItemStack parameter, JdbcType jdbcType) throws SQLException {
        byte[] byteData = ItemStackHelper.getInstance().itemToNBT(parameter);
        Blob blob = ps.getConnection().createBlob();
        blob.setBytes(1, byteData);
        ps.setBlob(i, blob);
    }

    /**
     * Retrieves an ItemStack from the specified column in the given ResultSet, identified by column name.
     * <p>
     * The method fetches a Blob from the ResultSet using the provided column name and
     * then converts this Blob to its corresponding ItemStack representation.
     * </p>
     *
     * @param rs         The ResultSet from which the ItemStack should be retrieved.
     * @param columnName The name of the column containing the ItemStack Blob data.
     * @return The ItemStack corresponding to the Blob data, or null if the Blob is null.
     * @throws SQLException If there's an error accessing the Blob data or other database-related issues.
     */
    @Override
    public ItemStack getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        return fromBlob(blob);
    }

    /**
     * Retrieves an ItemStack from the specified column in the given ResultSet, identified by column index.
     * <p>
     * The method fetches a Blob from the ResultSet using the provided column index and
     * then converts this Blob to its corresponding ItemStack representation.
     * </p>
     *
     * @param rs          The ResultSet from which the ItemStack should be retrieved.
     * @param columnIndex The index of the column containing the ItemStack Blob data.
     * @return The ItemStack corresponding to the Blob data, or null if the Blob is null.
     * @throws SQLException If there's an error accessing the Blob data or other database-related issues.
     */
    @Override
    public ItemStack getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        return fromBlob(blob);
    }

    /**
     * Retrieves an ItemStack from the specified column in the given CallableStatement, identified by column index.
     * <p>
     * The method fetches a Blob from the CallableStatement using the provided column index and
     * then converts this Blob to its corresponding ItemStack representation.
     * </p>
     *
     * @param cs          The CallableStatement from which the ItemStack should be retrieved.
     * @param columnIndex The index of the column containing the ItemStack Blob data.
     * @return The ItemStack corresponding to the Blob data, or null if the Blob is null.
     * @throws SQLException If there's an error accessing the Blob data or other database-related issues.
     */
    @Override
    public ItemStack getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        return fromBlob(blob);
    }
}
