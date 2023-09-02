package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import org.apache.ibatis.annotations.*;

/**
 * All packages and their associations are handled in this Mapper
 */
@Mapper
public interface PackageMapper {

    String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS `sd_packages` (\n" +
            "  `name` varchar(255) NOT NULL,\n" +
            "  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`name`),\n" +
            "  UNIQUE KEY `name` (`name`)\n" +
            ")";
    String NEW_PACKAGE_QUERY = "SET autocommit = 0;\n" +
            "START TRANSACTION;\n" +
            "INSERT sd_packages(name, created_at)\n" +
            "VALUES (#{package_name}, NOW()})\n" +
            "ON DUPLICATE KEY UPDATE created_at = NOW();\n" +
            // Delete all packages associations
            "COMMIT;\n" +
            "SET autocommit = 1";
    String DELETE_PACKAGE_QUERY = "";

    @Update(CREATE_QUERY)
    void createPackageTableIfNotExist();

    @Insert(NEW_PACKAGE_QUERY)
    void newPackage(@Param("package_name") String package_name);

    @Delete(DELETE_PACKAGE_QUERY)
    void deletePackage(@Param("package_name") String package_name);


}
