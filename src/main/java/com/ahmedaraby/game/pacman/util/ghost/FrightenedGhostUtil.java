package com.ahmedaraby.game.pacman.util.ghost;

import com.ahmedaraby.jengine.collision.sprite.M2SSpriteCollisionDetector;
import com.ahmedaraby.game.pacman.constant.DimensionsC;
import com.ahmedaraby.game.pacman.constant.SpriteE;
import com.ahmedaraby.game.pacman.entity.CanvasCoordinate;
import com.ahmedaraby.game.pacman.entity.CanvasRect;
import com.ahmedaraby.game.pacman.entity.Vector;
import com.ahmedaraby.game.pacman.model.CollisionReport;
import com.ahmedaraby.game.pacman.util.SpriteUtil;
import com.ahmedaraby.game.pacman.constant.DirectionsE;
import com.ahmedaraby.game.pacman.util.VectorUtil;

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
