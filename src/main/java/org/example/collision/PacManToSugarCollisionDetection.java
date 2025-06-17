package org.example.collision;

import org.example.constant.Dimensions;
import org.example.constant.SpriteE;
import org.example.entity.Coordinate;
import org.example.entity.Rect;
import org.example.event.*;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.PacManCurrentLocationEvent;
import org.example.event.PacManSugarCollisionEvent;
import org.example.event.manager.EventManager;
import org.example.util.RectUtils;
import org.example.util.sugar.SugarUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PacManToSugarCollisionDetection implements Subscriber {
    private SpriteE[][] maze;
    private EventManager eventManager;

    public PacManToSugarCollisionDetection(SpriteE[][] maze, EventManager eventManager) {
        this.maze = maze;
        this.eventManager = eventManager;
    }

    public boolean isEatingSugar(Coordinate pacManCanvasTopLeftCorner) {
        final Rect pacManCanvasRect = new Rect(pacManCanvasTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final List<Coordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasRect);
        final List<Coordinate> canvasSugarToBeEaten = pacManRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .filter(rectTopLeftCornerCanvas -> SugarUtil.isCanvasCellHasSugar(maze, rectTopLeftCornerCanvas))
                .map(SugarUtil::getSugarTopLeftCornerCanvas)
                .filter(sugarCanvasRectTopLeftCorner -> isPacManRectContainsSugarRect(pacManCanvasTopLeftCorner, sugarCanvasRectTopLeftCorner))
                .collect(Collectors.toUnmodifiableList());

        if (!canvasSugarToBeEaten.isEmpty()) {
            eventManager.notifySubscribers(new PacManSugarCollisionEvent(EventType.PAC_MAN_SUGAR_COLLISION, canvasSugarToBeEaten));
        }

        return !canvasSugarToBeEaten.isEmpty();
    }

    private Coordinate toTopLeftCornerOfRectContainingPoint(Coordinate point) {
        return RectUtils.getTopLeftCornerOfRectContainingPoint(Dimensions.CANVAS_CELL_SIZE_PIXELS, Dimensions.CANVAS_CELL_SIZE_PIXELS, point);
    }

    private boolean isPacManRectContainsSugarRect(Coordinate pacManCanvasRectTopLeftCorner, Coordinate sugarCanvasRectTopLeftCorner) {
        final Rect pacManCanvasRect = new Rect(pacManCanvasRectTopLeftCorner, Dimensions.PAC_MAN_DIAMETER_PIXELS, Dimensions.PAC_MAN_DIAMETER_PIXELS);
        final Rect sugarCanvasRect = new Rect(sugarCanvasRectTopLeftCorner, Dimensions.SUGAR_CELL_SIZE_PIXELS, Dimensions.SUGAR_CELL_SIZE_PIXELS);
        return RectUtils.isRectContainsRect(pacManCanvasRect, sugarCanvasRect);
    }

    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_CURRENT_LOCATION -> isEatingSugar(((PacManCurrentLocationEvent)event).getPacManCanvasRectTopLeftCorner());
            default -> throw new IllegalArgumentException();
        }
    }
}
