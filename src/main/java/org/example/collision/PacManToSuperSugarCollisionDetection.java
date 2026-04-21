package org.example.collision;

import org.example.constant.DimensionsC;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.event.*;
import org.example.event.Event;
import org.example.event.EventType;
import org.example.event.PacManCurrentLocationEvent;
import org.example.event.PacManSugarCollisionEvent;
import org.example.event.manager.EventManager;
import org.example.util.canvas.CanvasRectUtils;
import org.example.util.SugarUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PacManToSuperSugarCollisionDetection implements Subscriber {
    private EventManager eventManager;

    public PacManToSuperSugarCollisionDetection(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public boolean isEatingSuperSugar(CanvasCoordinate pacManCanvasRectTopLeftCorner) {
        final CanvasRect pacManCanvasCanvasRect = new CanvasRect(pacManCanvasRectTopLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
        final List<CanvasCoordinate> pacManRect4Corners = CanvasRectUtils.get4Corners(pacManCanvasCanvasRect);
        final List<CanvasCoordinate> canvasSuperSugarToBeEaten = pacManRect4Corners.stream()
                .map(this::toTopLeftCornerOfRectContainingPoint)
                .filter(rectTopLeftCornerCanvas -> SugarUtil.isCanvasCellHasSuperSugar(rectTopLeftCornerCanvas))
                .map(SugarUtil::getSuperSugarTopLeftCornerCanvas)
                .filter(superSugarCanvasRectTopLeftCorner -> collide(pacManCanvasRectTopLeftCorner, superSugarCanvasRectTopLeftCorner))
                .collect(Collectors.toUnmodifiableList());

        if (!canvasSuperSugarToBeEaten.isEmpty()) {
            eventManager.notifySubscribers(new PacManSugarCollisionEvent(EventType.PAC_MAN_SUPER_SUGAR_COLLISION, canvasSuperSugarToBeEaten));
        }

        return !canvasSuperSugarToBeEaten.isEmpty();
    }

    private CanvasCoordinate toTopLeftCornerOfRectContainingPoint(CanvasCoordinate point) {
        return CanvasRectUtils.getTopLeftCornerOfRectContainingPoint(DimensionsC.MAZE_CELL_SIZE_PIXELS, DimensionsC.MAZE_CELL_SIZE_PIXELS, point);
    }

    private boolean collide(CanvasCoordinate pacManTopLeftCorner, CanvasCoordinate superSugerTopLeftCorner) {
        final CanvasRect pacManCanvasCanvasRect = new CanvasRect(pacManTopLeftCorner, DimensionsC.PAC_MAN_DIAMETER_PIXELS, DimensionsC.PAC_MAN_DIAMETER_PIXELS);
        final CanvasRect superSugarCanvasCanvasRect = new CanvasRect(superSugerTopLeftCorner, DimensionsC.SUGAR_CELL_SIZE_PIXELS, DimensionsC.SUGAR_CELL_SIZE_PIXELS);
        return Rect2RectCollisionDetectorUtil.collide(pacManCanvasCanvasRect, superSugarCanvasCanvasRect);
    }


    @Override
    public void update(Event event) {
        switch (event.getType()) {
            case PAC_MAN_CURRENT_LOCATION -> isEatingSuperSugar(((PacManCurrentLocationEvent)event).getPacManCanvasRectTopLeftCorner());
            default -> throw new IllegalArgumentException();
        }
    }
}
