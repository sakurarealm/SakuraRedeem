package com.sakurarealm.sakuraredeem;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
import com.sakurarealm.sakuraredeem.data.mysql.handler.ItemStackTypeHandler;
import com.sakurarealm.sakuraredeem.data.yaml.YamlHelper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
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

    @SuppressWarnings("unchecked")
    void testCreateTables_whenCreatingTables_thenSuccess() {
        Map<String, Object> config = YamlHelper.loadYamlFromResources("/config.yml");
        assert config != null;
        Map<String, Object> mysqlConfig  = (Map<String, Object>) config.get("mysql");

        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUrl(mysqlConfig.get("url").toString());
        dataSource.setUsername( mysqlConfig.get("username").toString());
        dataSource.setPassword(mysqlConfig.get("password").toString());
        dataSource.setDriver(mysqlConfig.get("driver-class-name").toString());

        MybatisUtils.initFromDatasource(dataSource);
        MybatisUtils.openSession();

        PackageHelper.getInstance().createAllTables();
    }

    @Test
    void testGetPackages_whenInsertingGettingPackages_thenSuccess() {
        Map<String, Object> config = YamlHelper.loadYamlFromResources("/config.yml");
        assert config != null;
        Map<String, Object> mysqlConfig  = (Map<String, Object>) config.get("mysql");

        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUrl(mysqlConfig.get("url").toString());
        dataSource.setUsername( mysqlConfig.get("username").toString());
        dataSource.setPassword(mysqlConfig.get("password").toString());
        dataSource.setDriver(mysqlConfig.get("driver-class-name").toString());

        MybatisUtils.initFromDatasource(dataSource);
        MybatisUtils.openSession();

        PackageHelper.getInstance().createAllTables();
        PackageHelper.getInstance().newPackage("asdasdasd");
        PackageHelper.getInstance().exists("asdasdasd");
    }

}
