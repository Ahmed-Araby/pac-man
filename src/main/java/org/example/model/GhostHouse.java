package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class GhostHouse {
    private final int sCol;
    private final int eCol;
    private final int sRow;
    private final int eRow;

    public int calcDoorRow() {
        return sRow;
    }

    public int calcDoorCol() {
        return (eCol - sCol + 1) / 2 + sCol;
    }
}
