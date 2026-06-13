package com.ahmedaraby.game.pacman.constant;

public class SpriteFileNameC {
    private SpriteFileNameC() {}

    public static String FORWARD_SLASH = "/";

    public static final String SPRITE_FILES_DIRECTORY = "assets/sprites";

    // Ghosts
    public static final String GHOST_FOLDER = "ghost";
    public static final String GHOST_SPRITE_PATH_TEMPLATE = SpriteFileNameC.FORWARD_SLASH + SpriteFileNameC.SPRITE_FILES_DIRECTORY + SpriteFileNameC.FORWARD_SLASH + SpriteFileNameC.GHOST_FOLDER + SpriteFileNameC.FORWARD_SLASH + "%s" + SpriteFileNameC.FORWARD_SLASH + "%s";

    // Blinky, chaser and scatter
    public static final String BLINKY_FOLDER = "blinky";
    public static final String BLINKY_FRAME_1_FILE_NAME = "blinky-frame-1.png";
    public static final String BLINKY_FRAME_2_FILE_NAME = "blinky-frame-2.png";

    // frightened
    public static final String GHOST_FRIGHTENED_FOLDER = "frightened";
    public static final String GHOST_FRIGHTENED_FRAME_1_FILE_NAME = "ghost-frightened-frame-1.png";
    public static final String GHOST_FRIGHTENED_FRAME_2_FILE_NAME = "ghost-frightened-frame-2.png";

    // eaten
    public static final String GHOST_EATEN_FOLDER = "eaten";
    public static final String GHOST_EATEN_UP_FRAME_FILE_NAME = "ghost-eaten-up-frame.png";
    public static final String GHOST_EATEN_RIGHT_FRAME_FILE_NAME = "ghost-eaten-right-frame.png";
    public static final String GHOST_EATEN_DOWN_FRAME_FILE_NAME = "ghost-eaten-down-frame.png";
    public static final String GHOST_EATEN_LEFT_FRAME_FILE_NAME = "ghost-eaten-left-frame.png";

    // Inky, chaser and scatter
    public static final String INKY_FOLDER = "inky";
    public static final String INKY_FRAME_1_FILE_NAME = "inky-frame-1.png";
    public static final String INKY_FRAME_2_FILE_NAME = "inky-frame-2.png";

    // Pinky, chaser and scatter
    public static final String PINKY_FOLDER = "pinky";
    public static final String PINKY_FRAME_1_FILE_NAME = "pinky-frame-1.png";
    public static final String PINKY_FRAME_2_FILE_NAME = "pinky-frame-2.png";
}
