package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class Line {
    CanvasCoordinate start;
    CanvasCoordinate end;


    public double slope() {
        return (end.getRow() - start.getRow()) / (end.getCol() - start.getCol());
    }

    public Optional<CanvasCoordinate> intersectAt(Line line2) {
        // assume this line is called line1
        final double slope1 = slope();
        final double slope2 = line2.slope();

        if (slope1 == slope2) { // an error margin should be taken into account
            return Optional.empty();
        }

        // the following math is derived from the solving the slope equation for both lines together
        // in the following equations, c stands for common
        // slope equation for the 1st line : slope1 = (Yc - Y1) / (Xc - X1)
        // slope equation for the 2nd line : slope2 = (Yc - Y2) / (Xc - X2)
        final double line1EqPart = slope1 * start.getCol() - start.getRow();
        final double line2EqPart = - slope2 * line2.getStart().getCol() + line2.getStart().getRow();
        final double xCommon = (line1EqPart + line2EqPart) / (slope1 - slope2);

        return Optional.of(getPointGivenX(xCommon));
    }

    public CanvasCoordinate getPointGivenX(double x) {
        final double y = slope() * (x - start.getCol()) + start.getRow();
        return new CanvasCoordinate(y, x);
    }

    public Line scale(double scalar) {
        final Vector endV = new Vector(end);
        final Vector startV = new Vector(start);
        final Vector newEndV = endV
                .sub(startV)
                .scale(scalar)
                .add(startV);
        final CanvasCoordinate newEnd = new CanvasCoordinate(newEndV);
        return new Line(start, newEnd);
    }

    public Line trim(CanvasRect enclosingRect) {
        Optional<CanvasCoordinate> intersectionPoint = Optional.empty();

        for(Line side: enclosingRect.getSides()) {
            intersectionPoint = intersectAt(side);
            if (intersectionPoint.isPresent()) {
                break;
            }
        }

        if (intersectionPoint.isPresent()) {
            return new Line(start, intersectionPoint.get());
        }

        return this;
    }

}
