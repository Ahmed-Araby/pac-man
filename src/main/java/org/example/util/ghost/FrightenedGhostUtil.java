package org.example.util.ghost;

import org.example.entity.CanvasCoordinate;
import org.example.entity.MazeCell;
import org.example.entity.Vector;
import org.example.maze.MazeMatrix;
import org.example.util.CanvasUtil;
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
                    final CanvasCoordinate newCord = GhostUtil.move(cord, allowedDir);
                    if (!CanvasUtil.inCanvas(newCord)) {
                        return false;
                    }
                    final List<MazeCell> interestingMazeCells = CanvasUtil.getIntersectingMazeCells(newCord);
                    return interestingMazeCells
                            .stream()
                            .noneMatch(MazeMatrix::isWall);
                })
                .toList();
    }
}
