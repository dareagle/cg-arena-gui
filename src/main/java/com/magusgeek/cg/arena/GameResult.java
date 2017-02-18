package com.magusgeek.cg.arena;

import java.util.ArrayList;
import java.util.List;

public class GameResult {

    private int id;

    private List<Integer> positions;

    public GameResult() {
        positions = new ArrayList<>();
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
