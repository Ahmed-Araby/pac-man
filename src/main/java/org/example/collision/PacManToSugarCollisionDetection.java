package org.example.collision;

import org.example.constant.DimensionsC;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
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

    public boolean isEatingSugar(CanvasCoordinate pacManCanvasTopLeftCorner) {
        final Rect pacManCanvasRect = new Rect(pacManCanvasTopLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
        final List<CanvasCoordinate> pacManRect4Corners = RectUtils.get4Corners(pacManCanvasRect);
        final List<CanvasCoordinate> canvasSugarToBeEaten = pacManRect4Corners.stream()
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

    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return RectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }

    private boolean isPacManRectContainsSugarRect(CanvasCoordinate pacManCanvasRectTopLeftCorner, CanvasCoordinate sugarCanvasRectTopLeftCorner) {
        final Rect pacManCanvasRect = new Rect(pacManCanvasRectTopLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
        final Rect sugarCanvasRect = new Rect(sugarCanvasRectTopLeftCorner, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
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
