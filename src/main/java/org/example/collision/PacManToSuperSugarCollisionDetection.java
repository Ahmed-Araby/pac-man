package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.event.*;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.PacManCurrentLocationEvent;
import org.example.event.PacManSugarCollisionEvent;
import org.example.util.RectUtils;
import org.example.util.sugar.SugarUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PacManToSuperSugarCollisionDetection implements Subscriber {
    private SpriteE[][] maze;
    private EventManager eventManager;

    public PacManToSuperSugarCollisionDetection(SpriteE[][] maze, EventManager eventManager) {
        this.maze = maze;
        this.eventManager = eventManager;
    }

    public boolean isEatingSuperSugar(Coordinate pacManCanvasRectTopLeftCorner) {

        final List<Coordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasRectTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final List<Coordinate> canvasSuperSugarToBeEaten = pacManRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .filter(rectTopLeftCornerCanvas -> SugarUtil.isCanvasCellHasSuperSugar(maze, rectTopLeftCornerCanvas))
                .map(SugarUtil::getSuperSugarTopLeftCornerCanvas)
                .filter(superSugarCanvasRectTopLeftCorner -> isPacManRectContainsSuperSugarRect(pacManCanvasRectTopLeftCorner, superSugarCanvasRectTopLeftCorner))
                .collect(Collectors.toUnmodifiableList());

        if (!canvasSuperSugarToBeEaten.isEmpty()) {
            eventManager.notifySubscribers(new PacManSugarCollisionEvent(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, canvasSuperSugarToBeEaten));
        }

        return !canvasSuperSugarToBeEaten.isEmpty();
    }

    private Coordinate toTopLeftCornerOfRectContainingPoint(Coordinate point) {
        return RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, point);
    }

    private boolean isPacManRectContainsSuperSugarRect(Coordinate pacManCanvasRectTopLeftCorner, Coordinate sugarCanvasRectTopLeftCorner) {
        return RectUtils.isRectContainsRect(pacManCanvasRectTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS,
                sugarCanvasRectTopLeftCorner, Dimensions.SUGAR_CELL_SIZE_PIXELS, Dimensions.SUGAR_CELL_SIZE_PIXELS);
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_CURRENT_LOCATION -> isEatingSuperSugar(((PacManCurrentLocationEvent)event).getPacManCanvasRectTopLeftCorner());
            default -> throw new IllegalArgumentException();
        }
    }
}
