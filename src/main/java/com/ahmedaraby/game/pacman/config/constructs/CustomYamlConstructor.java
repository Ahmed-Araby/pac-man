package com.ahmedaraby.game.pacman.config.constructs;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

public class CustomYamlConstructor extends Constructor {

    public CustomYamlConstructor(LoaderOptions loadingConfig) {
        super(loadingConfig);
        registerFxColorCustomConstructor();

    }

    private void registerFxColorCustomConstructor() {
        final FxColorConstruct fxColorConstruct = new FxColorConstruct(this);
        this.yamlConstructors.put(new Tag("!color"), fxColorConstruct);
//        this.yamlClassConstructors.put(NodeId.scalar, fxColorConstruct);
    }
}
