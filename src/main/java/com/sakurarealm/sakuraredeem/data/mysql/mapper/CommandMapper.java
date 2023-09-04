package com.sakurarealm.sakuraredeem.data.mysql.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommandMapper {

    String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS `sd_commands` (\n" +
                    "  `package_name` varchar(255),\n" +
                    "  `command` varchar(1024)\n" +
                    ");\n" +
                    "ALTER TABLE `sd_commands` ADD FOREIGN KEY (`package_name`) REFERENCES `sd_packages` (`name`);";

    String INSERT_QUERY =
            "INSERT INTO sd_commands (package_name, command) VALUES (#{package_name}, #{command})";

    String DELETE_QUERY = "DELETE FROM sd_commands WHERE package_name=#{package_name}";

    String GET_COMMANDS_QUERY = "SELECT command FROM sd_commands WHERE package_name=#{package_name}";

    @Update(CREATE_QUERY)
    void createCommandsTableIfNotExist();

    @Insert(INSERT_QUERY)
    void insertCommand(@Param("package_name") String package_name, @Param("command") String command);

    @Delete(DELETE_QUERY)
    void clearCommands(@Param("package_name") String package_name);

    @Select(GET_COMMANDS_QUERY)
    @Results({
            @Result(column = "command")
    })
    List<String> getAllCommands(@Param("package_name") String package_name);
}
