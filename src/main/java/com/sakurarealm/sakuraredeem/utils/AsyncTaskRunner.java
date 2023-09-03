package com.sakurarealm.sakuraredeem.utils;

import com.sakurarealm.sakuraredeem.SakuraRedeem;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class AsyncTaskRunner<T> {

    public void runAsyncTask(Callable<T> asyncTask, Consumer<T> syncCallback) {
        Bukkit.getScheduler().runTaskAsynchronously(SakuraRedeem.getPlugin(), () -> {
            T result;
            try {
                result = asyncTask.call();
                Bukkit.getScheduler().runTask(SakuraRedeem.getPlugin(), () -> {
                    syncCallback.accept(result);
                });
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(SakuraRedeem.getPlugin(), () -> {
                    BukkitLogger.error("Error when running async task: " + e);
                });
            }
        });
    }

}
