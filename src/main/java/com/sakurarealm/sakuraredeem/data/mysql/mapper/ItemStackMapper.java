package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import com.sakurarealm.sakuraredeem.data.mysql.handler.ItemStackTypeHandler;
import org.apache.ibatis.annotations.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Mapper
public interface ItemStackMapper {

    String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS `sd_items` (\n" +
            "  `package_name` varchar(255),\n" +
            "  `serialized_item` varchar(65565)\n" +
            ");\n" +
            "CREATE INDEX `sd_items_index_0` ON `sd_items` (`package_name`);\n" +
            "ALTER TABLE `sd_items` ADD FOREIGN KEY (`package_name`) REFERENCES `sd_packages` (`name`);";

    String INSERT_QUERY =
            "INSERT INTO sd_items (package_name, serialized_item)" +
            "VALUES (#{package_name}, " +
                    "#{serialized_item, typeHandler=com.sakurarealm.sakuraredeem.data.mysql.handler.ItemStackHandler})";

    String DELETE_QUERY = "DELETE FROM sd_items WHERE package_name=#{package_name}";

    String GET_ITEM_STACKS_QUERY = "SELECT (serialized_item) FROM sd_items WHERE package_name=#{package_name}";

    @Update(CREATE_QUERY)
    void createItemStacksTableIfNotExist();

    @Insert(INSERT_QUERY)
    void insertItemStack(@Param("package_name") String package_name, @Param("serialized_data") ItemStack itemStack);

    @Delete(DELETE_QUERY)
    void clearItemStacks(@Param("package_name") String package_name);

    @Select(GET_ITEM_STACKS_QUERY)
    @Results({
            @Result(column = "serialized_item", typeHandler = ItemStackTypeHandler.class)
    })
    List<ItemStack> getAllItemStacks(@Param("package_name") String package_name);
}
