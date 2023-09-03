package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import com.sakurarealm.sakuraredeem.data.mysql.entity.Package;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PackageMapper {

    String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS `sd_packages` (\n" +
                    "  `name` varchar(255) NOT NULL,\n" +
                    "  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  PRIMARY KEY (`name`),\n" +
                    "  UNIQUE KEY `name` (`name`)\n" +
                    ")";

    String NEW_PACKAGE_QUERY =
            "INSERT sd_packages(name, created_at)\n" +
                    "VALUES (#{package_name}, NOW()})\n" +
                    "ON DUPLICATE KEY UPDATE created_at = NOW()";

    String DELETE_QUERY = "DELETE FROM sd_packages WHERE name=#{package_name}";

    String FIND_QUERY = "SELECT (name, created_at) FROM sd_packages WHERE name=#{package_name}";

    @Update(CREATE_QUERY)
    void createPackageTableIfNotExists();

    @Insert(NEW_PACKAGE_QUERY)
    void newPackage(@Param("package_name") String package_name);

    @Delete(DELETE_QUERY)
    void deletePackage(@Param("package_name") String package_name);

    @Select(FIND_QUERY)
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "items", column = "package_name=name",
                    many = @Many(select = "com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper.getAllItemStacks")),
            @Result(property = "commands", column = "package_name=name",
                    many = @Many(select = "com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper.getAllCommands")),
    })
    Package findPackage(@Param("package_name") String package_name);

    @Select(FIND_QUERY)
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "created_at", column = "created_at"),
    })
    Package findPackageWithoutSubjects(@Param("package_name") String package_name);
}
