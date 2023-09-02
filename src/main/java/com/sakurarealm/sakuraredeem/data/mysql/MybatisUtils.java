package com.sakurarealm.sakuraredeem.data.mysql;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.sakurarealm.sakuraredeem.utils.Logger;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Map;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlSession;

    /**
     * Initialize Mybatis
     *
     * @param mysqlConfig The database configurations loaded from yaml
     * @throws RuntimeException Threw if any error encountered
     */
    public static void init(Map<String, Object> mysqlConfig) {
        // Initialize sql datasource from external yaml
        try {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setUrl(mysqlConfig.get("url").toString());
            dataSource.setUsername(mysqlConfig.get("username").toString());
            dataSource.setPassword(mysqlConfig.get("password").toString());
            dataSource.setDriver("driver-class-name");

            // Build the session factory
            sqlSessionFactory = createSessionFactory(dataSource,
                    "com.sakurarealm.sakuraredeem.data.mysql.mapper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build a new Sql session factory
     *
     * @param dataSource           Mysql data source
     * @param packageNameOfMappers The package containing all mappers
     * @return
     */
    public static SqlSessionFactory createSessionFactory(DataSource dataSource, String packageNameOfMappers) {
        Logger.info("Creating Sql Session Factory");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        MybatisConfiguration configuration = new MybatisConfiguration();

        // Use cache
        configuration.setCacheEnabled(true);
        // Use camel case
        configuration.setMapUnderscoreToCamelCase(true);
        // Add logger
        configuration.setLogImpl(Slf4jImpl.class);

        // Set environment
        Environment environment = new Environment("dev", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);

        // Add all mappers
        configuration.addMappers(packageNameOfMappers);

        return builder.build(configuration);
    }

    /**
     * Only one session opened the entire time
     *
     * @throws RuntimeException Threw if init() was never called
     */
    public static void openSession() {
        if (sqlSessionFactory == null)
            throw new RuntimeException("Mybatis is not initialized!");

        Logger.info("Connection session opened");
        if (sqlSession != null)
            sqlSession.close();

        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * Only one session is opened the entire time.
     * Usage:
     *
     * <pre>
     *      MybatisUtils.getSession().getMapper(PackageMapper.class).newPackage("test");
     * </pre>
     *
     * @return The opened Mysql Session
     * @throws RuntimeException Threw if init() was never called
     */
    public static SqlSession getSession() {
        if (sqlSession == null)
            throw new RuntimeException("SqlSession is never opened!");
        return sqlSession;
    }

}
