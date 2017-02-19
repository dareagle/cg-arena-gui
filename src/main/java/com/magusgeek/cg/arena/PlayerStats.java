package com.magusgeek.cg.arena;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerStats {

    private int[] stats = new int[4];
    private IntegerProperty total = new SimpleIntegerProperty();

    public PlayerStats() {
        total.set(0);

        for (int i = 0; i < 4; ++i) {
            stats[i] = 0;
        }
    }

    public void add(int position) {
        stats[position] += 1;
        total.set(total.get() + 1);
    }

    public int[] getStats() {
        return stats;
    }

    public IntegerProperty getTotal() {
        return total;
    }

    public String toString() {
        String result = " | ";

        for (int i = 0; i < 4; ++i) {
            result += " Position " + (i + 1) + " : " + stats[i] + " (" + (stats[i] * 100 / total.get()) + "%) | ";
        }

        return result;
    }
}
