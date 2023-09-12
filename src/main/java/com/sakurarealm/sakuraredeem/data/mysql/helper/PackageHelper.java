package com.sakurarealm.sakuraredeem.data.mysql.helper;


import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.entity.Package;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.ItemStackMapper;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.PackageMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PackageHelper {

    private static final PackageHelper INSTANCE = new PackageHelper();

    private PackageHelper() {

    }

    public static PackageHelper getInstance() {
        return INSTANCE;
    }

    synchronized public void createAllTables() {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(PackageMapper.class).createPackageTableIfNotExists();
            session.getMapper(ItemStackMapper.class).createItemStacksTableIfNotExist();
            session.getMapper(CommandMapper.class).createCommandsTableIfNotExist();
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    synchronized public boolean delPackage(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(PackageMapper.class).deletePackage(packageName);
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

    synchronized public Package getPackage(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            return session.getMapper(PackageMapper.class).findPackage(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    synchronized public Package getPackageWithoutSubjects(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            return session.getMapper(PackageMapper.class).findPackageWithoutSubjects(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    synchronized public List<Package> getAllPackagesWithoutSubjects() {
        try {
            SqlSession session = MybatisUtils.getSession();
            return session.getMapper(PackageMapper.class).getAllPackagesWithoutSubjects();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
