package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import com.sakurarealm.sakuraredeem.data.mysql.handler.ItemStackTypeHandler;
import org.apache.ibatis.annotations.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Mapper
public interface ItemStackMapper {

    String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS `sd_items` (\n" +
            "  `package_name` varchar(255),\n" +
            "  `serialized_item` varchar(65565)\n" +
            ");\n" +
            "CREATE INDEX `sd_items_index_0` ON `sd_items` (`package_name`);\n" +
            "ALTER TABLE `sd_items` ADD FOREIGN KEY (`package_name`) REFERENCES `sd_packages` (`name`);";
    String INSERT_QUERY = "INSERT INTO sd_items( package_name, serialized_item )\n" +
            "VALUES ( #{package_name}, #{serialized_item} )";
    String CLEAR_QUERY = "DELETE FROM sd_items WHERE package_name = #{package_name}";
    String SELECT_QUERY = "SELECT serialized_item FROM sd_items WHERE package_name = #{package_name}";

    @Update(CREATE_QUERY)
    void createItemTableIfNotExists();

    @Insert(INSERT_QUERY)
    void insertPackageItem(@Param("package_name") String package_name, @Param("serialized_item") String serialized_item);

    @Delete(CLEAR_QUERY)
    void clearPackageItems(@Param("package_name") String package_name);

    @Select(SELECT_QUERY)
    @Results({
            @Result(property = "", column = "serialized_item", typeHandler = ItemStackTypeHandler.class)
    })
    List<ItemStack> getPackageItems(@Param("package_name") String package_name);
}
