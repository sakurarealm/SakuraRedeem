package com.sakurarealm.sakuraredeem.data.mysql.helper;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.entity.Command;
import com.sakurarealm.sakuraredeem.data.mysql.mapper.CommandMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CommandHelper {

    private static final CommandHelper INSTANCE = new CommandHelper();

    private CommandHelper() {

    }

    public static CommandHelper getInstance() {
        return INSTANCE;
    }

    synchronized public List<Command> getCommands(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            return session.getMapper(CommandMapper.class).getAllCommands(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    synchronized public boolean insertCommand(String packageName, String command, boolean useTerminal) {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(CommandMapper.class).insertCommand(packageName, command, useTerminal);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    synchronized public boolean clearCommands(String packageName) {
        try {
            SqlSession session = MybatisUtils.getSession();
            session.getMapper(CommandMapper.class).clearCommands(packageName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
