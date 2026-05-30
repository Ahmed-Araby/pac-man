package com.ahmedaraby.game.pacman.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class Line {
    CanvasCoordinate start;
    CanvasCoordinate end;


    public double slope() {
        return (end.getRow() - start.getRow()) / (end.getCol() - start.getCol());
    }

    public Optional<CanvasCoordinate> intersectAtWithVLine(Line line) {
        if (Double.isInfinite(slope())) {
            return Optional.empty();
        }
        return Optional.of(getPointGivenX(line.getStart().getCol()));
    }

    public Optional<CanvasCoordinate> intersectAtWithHLine(Line line) {
        if (slope() == 0) {
            return Optional.empty();
        }
        return Optional.of(getPointGivenY(line.getStart().getRow()));
    }

    public CanvasCoordinate getPointGivenX(double x) {
        double y = slope() * (x - start.getCol()) + start.getRow();
        y = Math.round(y);
        return new CanvasCoordinate(y, x);
    }

    public CanvasCoordinate getPointGivenY(double y) {
        double x = (y - start.getRow()) / slope() + start.getCol();
        x = Math.round(x);
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

    public Line trim(Rectangle enclosingRect) {
        List<CanvasCoordinate> intersectionPoints = new ArrayList<>();

        if (end.getCol() > enclosingRect.rightEdgeCol()) {
            intersectAtWithVLine(enclosingRect.rightSide()).ifPresent(intersectionPoints::add);
        }
        if (end.getCol() < enclosingRect.leftEdgeCol()) {
            intersectAtWithVLine(enclosingRect.leftSide()).ifPresent(intersectionPoints::add);
        }
        if (end.getRow() > enclosingRect.bottomEdgeRow()) {
            intersectAtWithHLine(enclosingRect.bottomSide()).ifPresent(intersectionPoints::add);
        }
        if (end.getRow() < enclosingRect.topEdgeRow()) {
            intersectAtWithHLine(enclosingRect.topSide()).ifPresent(intersectionPoints::add);
        }

        Optional<CanvasCoordinate> newEndCord = intersectionPoints.stream()
                .filter(cord -> cord.within(enclosingRect))
                .findFirst();

        if (newEndCord.isPresent()) {
            return new Line(start, newEndCord.get());
        }

        return this;
    }

}
