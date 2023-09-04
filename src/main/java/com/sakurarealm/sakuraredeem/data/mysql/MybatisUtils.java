package com.sakurarealm.sakuraredeem.data.mysql;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.PackageMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.sql.DataSource;

public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlSession;

    /**
     * Initialize Mybatis
     *
     * @param configuration The configurations loaded from yaml
     * @throws RuntimeException Threw if any error encountered
     */
    public static void init(FileConfiguration configuration) {
        // Initialize sql datasource from external yaml
        try {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setUrl(configuration.getString("mysql.url"));
            dataSource.setUsername(configuration.getString("mysql.username"));
            dataSource.setPassword(configuration.getString("mysql.password"));
            dataSource.setDriver(configuration.getString("mysql.driver-class-name"));

            initFromDatasource(dataSource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void initFromDatasource(DataSource dataSource) {
        // Build the session factory
        sqlSessionFactory = createSessionFactory(dataSource,
                "com.sakurarealm.sakuraredeem.data.mysql.mapper");
    }

    /**
     * Build a new Sql session factory
     *
     * @param dataSource           Mysql data source
     * @param packageNameOfMappers The package containing all mappers
     * @return
     */
    public static SqlSessionFactory createSessionFactory(DataSource dataSource, String packageNameOfMappers) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        MybatisConfiguration configuration = new MybatisConfiguration();

        // Use cache
        configuration.setCacheEnabled(true);
        // Use camel case
        configuration.setMapUnderscoreToCamelCase(true);
        // Add logger
//        configuration.setLogImpl(Log4j2Impl.class);

        // Set environment
        Environment environment = new Environment("dev", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);

        // Add all mappers
        //configuration.addMappers(packageNameOfMappers);
        configuration.addMapper(PackageMapper.class);
        configuration.addMapper(ItemStackMapper.class);
        configuration.addMapper(CommandMapper.class);

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
