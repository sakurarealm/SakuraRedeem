package com.sakurarealm.sakuraredeem.data.mysql.helper;


import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.PackageMapper;
import com.sakurarealm.sakuraredeem.utils.BukkitLogger;
import org.apache.ibatis.session.SqlSession;

import javax.activation.CommandMap;

public class PackageHelper {

    private static final PackageHelper INSTANCE = new PackageHelper();

    private PackageHelper() {

    }

    public static PackageHelper getInstance() {
        return INSTANCE;
    }

    synchronized public boolean newPackage(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(PackageMapper.class).newPackage(packageName);
            session.getMapper(ItemStackMapper.class).clearItemStacks(packageName);
            session.getMapper(CommandMapper.class).clearCommands(packageName);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    synchronized public boolean exists(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            return null != session.getMapper(PackageMapper.class).findPackageWithoutSubjects(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
