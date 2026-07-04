package com.ahmedaraby.game.pacman.config.constructs;

import javafx.scene.paint.Color;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class FxColorConstruct extends AbstractConstruct {

    private final Constructor constructor;

    public FxColorConstruct(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public Object construct(Node node) {
        final ScalarNode scalarNode = (ScalarNode) node;

        String val = scalarNode.getValue();
        switch (val) {
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "CYAN":
                return Color.CYAN;
            case "WHITE":
                return Color.WHITE;
            case "BLACK":
                return Color.BLACK;
            default:
                throw new IllegalArgumentException("color : " + val + ", is not supported by the custom deserializer");
        }
    }
}
