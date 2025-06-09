package org.example.constant;

public class Dimensions {

    // measurements are in pixels, otherwise it will be explicitly specified.
    // canvas
    public static double CANVAS_WIDTH = 1000;
    public static double CANVAS_HEIGHT = 1000;

    // maze
    public static double MAZE_CELL_SIZE = 30;
    public static double MAZE_CHAMBER_MIN_WIDTH = 100;
    public static double MAZE_CHAMBER_MIN_HEIGHT = 100;

    // pac man
    public static double PAC_MAN_REDIUS = 50;
    public static double PAC_MAN_STRIDE = MAZE_CELL_SIZE;
    public static double PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES = 270;
    public static double PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES = 360;
    public static double PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES = 0;
    public static double PAC_MAN_RIGHT_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 45;
    public static double PAC_MAN_UP_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 135;
    public static double PAC_MAN_LEFT_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 225;
    public static double PAC_MAN_DOWN_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 315;
}
