package com.sakurarealm.sakuraredeem;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.yaml.YamlHelper;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Map;

public class DatabaseTests {

    static Logger LOGGER = LoggerFactory.getLogger(DatabaseTests.class);

    @Test
    @SuppressWarnings("unchecked")
    void testSimulateReadingConfig_whenReadsTheMySqlFile_thenSuccess() {
        Map<String, Object> config = YamlHelper.loadYamlFromResources("/config.yml");
        assert config != null;
        Map<String, Object> mysqlConfig  = (Map<String, Object>) config.get("mysql");
        LOGGER.info(() -> String.format("url: %s; driver: %s; username: %s; password: %s",
                mysqlConfig.get("url").toString(),
                mysqlConfig.get("driver-class-name").toString(),
                mysqlConfig.get("username").toString(),
                mysqlConfig.get("password").toString()));
    }

//    @Test
//    void testInitDatabase_whenInitializingDatabase_thenSuccess() {
//        Map<String, Object> config = YamlHelper.loadYamlFromResources("/config.yml");
//        assert config != null;
//        Map<String, Object> mysqlConfig  = (Map<String, Object>) config.get("mysql");
//        MybatisUtils.init(mysqlConfig);
//        MybatisUtils.openSession();
//    }

}
