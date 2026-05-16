package org.example.util.ghost;

import org.example.collision.sprite.M2SSpriteCollisionDetector;
import org.example.constant.DimensionsC;
import org.example.constant.DirectionsE;
import org.example.constant.SpriteE;
import org.example.entity.CanvasCoordinate;
import org.example.entity.CanvasRect;
import org.example.entity.Vector;
import org.example.model.CollisionReport;
import org.example.util.SpriteUtil;
import org.example.util.VectorUtil;

import java.util.List;

public class FrightenedGhostUtil {

    public static List<Vector> getAllowedDirections(Vector currDir) {
        return Vector.fourD
                .stream()
                .filter(dir -> !VectorUtil.isOpposite(dir, currDir))
                .toList();
    }

    public static List<Vector> getEligibleDirections(CanvasCoordinate cord, Vector dir) {
        final List<Vector> allowedDirections = getAllowedDirections(dir);
        return allowedDirections
                .stream()
                .filter(allowedDir -> {
                    final DirectionsE allowedDirectionE = VectorUtil.toDirection(allowedDir);
                    final CanvasCoordinate candidateNextCord = GhostUtil.move(cord, allowedDirectionE);
                    if (isGoingOutOfCanvas(candidateNextCord))  {
                        return false;
                    }
                    final CanvasRect rect = SpriteUtil.toRect(candidateNextCord, SpriteE.GHOST);
                    final List<CollisionReport> collisionReportOpt = M2SSpriteCollisionDetector.detect(rect, List.of(SpriteE.WALL, SpriteE.GHOST_HOUSE_WALL));
                    return collisionReportOpt.isEmpty();
                })
                .toList();
    }

    // [TODO] move this method because it doesn't fit this class
    // [TODO] move this to the Sprite class
    private static boolean isGoingOutOfCanvas(CanvasCoordinate topLeftCorner) {
        return topLeftCorner.getRow() < 0 || topLeftCorner.getRow() > DimensionsC.CANVAS_HEIGHT_PIXELS - DimensionsC.GHOST_HEIGHT_PIXELS ||
                topLeftCorner.getCol() < 0 || topLeftCorner.getCol() > DimensionsC.CANVAS_WIDTH_PIXELS - DimensionsC.GHOST_WIDTH_PIXELS;
    }

}
