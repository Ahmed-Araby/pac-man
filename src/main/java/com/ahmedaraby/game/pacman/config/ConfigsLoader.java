package com.ahmedaraby.game.pacman.config;

import com.ahmedaraby.game.pacman.config.intConfigs.ConfigsEx;
import com.ahmedaraby.game.pacman.config.constructs.CustomYamlConstructor;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigsLoader {
    public ConfigsEx load() throws FileNotFoundException, URISyntaxException {
        // [TODO] return default configs if failed to load the file
        // read configs file
        final URL url = getClass().getResource("/configs.yaml");
        final File configFile = new File(url.toURI());
        final FileInputStream configFileStream = new FileInputStream(configFile);

        // parse the file
        Yaml parser = new Yaml(new CustomYamlConstructor(new LoaderOptions()));
        ConfigsEx configs = parser.loadAs(configFileStream, ConfigsEx.class);

        return configs;
    }
}
