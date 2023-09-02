package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommandMapper {

    String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS `sd_commands` (\n" +
            "  `package_name` varchar(255),\n" +
            "  `command` varchar(1024)\n" +
            ");\n" +
            "CREATE INDEX `sd_commands_index_1` ON `sd_commands` (`package_name`);\n" +
            "ALTER TABLE `sd_commands` ADD FOREIGN KEY (`package_name`) REFERENCES `sd_packages` (`name`);";
    String INSERT_QUERY = "INSERT INTO sd_commands( package_name, command )\n" +
            "VALUES ( #{package_name}, #{command} )";
    String CLEAR_QUERY = "DELETE FROM sd_commands WHERE package_name = #{package_name}";
    String SELECT_QUERY = "SELECT command FROM sd_commands WHERE package_name = #{package_name}";

    @Update(CREATE_QUERY)
    void createCommandTableIfNotExists();

    @Insert(INSERT_QUERY)
    void insertPackageCommand(@Param("package_name") String package_name, @Param("command") String command);

    @Delete(CLEAR_QUERY)
    void clearPackageCommands(@Param("package_name") String package_name);

    @Select(SELECT_QUERY)
    @Results({
            @Result(property = "", column = "command")
    })
    List<String> getPackageCommands(@Param("package_name") String package_name);

}
