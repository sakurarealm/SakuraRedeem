package com.sakurarealm.sakuraredeem.data.yaml;

import com.sakurarealm.sakuraredeem.utils.BukkitLogger;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlHelper {

    /**
     * Load a YAML file into a Java Map
     * NOTE: A Null object is returned if any error encountered
     *
     * @param yamlFile The yaml File
     * @return A Map representing the YAML content
     */
    @SuppressWarnings("unchecked")
    public static @Nullable Map<String, Object> loadYamlFile(File yamlFile) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(yamlFile)) {
            return (Map<String, Object>) yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            BukkitLogger.error(String.format("%s file not found error\n%s", yamlFile, e));
        } catch (Exception e) {
            BukkitLogger.error(String.format("Error while loading %s\n%s", yamlFile, e));
        }
        return null;
    }

    /**
     * Load a YAML from classpath resources into a Java Map.
     * NOTE: A Null object is returned if any error encountered
     *
     * @param resourcePath The path to the resource, e.g., "/config/config.yaml".
     * @return A Map representing the YAML content.
     */
    @SuppressWarnings("unchecked")
    public static @Nullable Map<String, Object> loadYamlFromResources(String resourcePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlHelper.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                BukkitLogger.error(String.format("%s file not found error", resourcePath));
                return null;
            }
            return (Map<String, Object>) yaml.load(inputStream);
        } catch (Exception e) {
            BukkitLogger.error(String.format("Error while loading %s\n%s", resourcePath, e));
        }
        return null;
    }
}
