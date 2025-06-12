package org.example.constant;

public class Dimensions {

    // measurements are in pixels, otherwise it will be explicitly specified.
    // canvas
    public static double CANVAS_WIDTH_PIXELS = 1000;
    public static double CANVAS_HEIGHT_PIXELS = 1000;
    public static double CANVAS_CELL_SIZE_PIXELS = 50;

    // maze
    public static int MAZE_WIDTH = (int) (CANVAS_WIDTH_PIXELS / CANVAS_CELL_SIZE_PIXELS);
    public static int MAZE_HEIGHT = (int) (CANVAS_HEIGHT_PIXELS / CANVAS_CELL_SIZE_PIXELS);
    public static int MAZE_CHAMBER_MIN_WIDTH = 2; // min number of cells can't be less than 2
    public static int MAZE_CHAMBER_MIN_HEIGHT = 2; // min number of cells can't be less than 2

    // pac man
    public static double PAC_MAN_DIAMETER_PIXELS = CANVAS_CELL_SIZE_PIXELS; // Pac-Man should fill a complete cell.
    public static double PAC_MAN_STRIDE_PIXELS = CANVAS_CELL_SIZE_PIXELS; // Pac-Man should move one cell at a time.

    public static double PAC_MAN_OPEN_MOUSE_ARC_EXTENT_IN_DEGREES = 270;
    public static double PAC_MAN_CLOSED_MOUSE_ARC_EXTENT_IN_DEGREES = 360;
    public static double PAC_MAN_CLOSED_MOUSE_START_ANGLE_IN_DEGREES = 0;
    public static double PAC_MAN_RIGHT_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 45;
    public static double PAC_MAN_UP_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 135;
    public static double PAC_MAN_LEFT_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 225;
    public static double PAC_MAN_DOWN_OPEN_MOUSE_START_ANGLE_IN_DEGREES = 315;


    // sugar
    public static double SUGAR_CELL_SIZE_PIXELS = 0.15 * CANVAS_CELL_SIZE_PIXELS;
    public static double SUPER_SUGAR_DIAMETER_PIXELS = 0.6 * CANVAS_CELL_SIZE_PIXELS;

}
