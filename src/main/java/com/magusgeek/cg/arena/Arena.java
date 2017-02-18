package com.magusgeek.cg.arena;

import com.magusgeek.cg.arena.engine.Engine;
import com.magusgeek.cg.arena.util.Mutable;
import javafx.collections.ObservableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private static final Log LOG = LogFactory.getLog(Arena.class);

    static List<ArenaThread> threads = new ArrayList<>();

    public static void launch(int gamesNumber, int threadsNumber, String engineClassName, List<String> playersCommandLines, ObservableList<PlayerStats> stats, ObservableList<GameResult> results) {
        try {
            LOG.info("Number of games to play : " + gamesNumber);
            LOG.info("Number of thread to spawn : " + threadsNumber);

            // Spawn arena threads
            Mutable<Integer> count = new Mutable<>(0);
            Class<Engine> clazz = (Class<Engine>) Class.forName(engineClassName);
            for (int i = 0; i < threadsNumber; ++i) {
                ArenaThread thread = new ArenaThread(i + 1, playersCommandLines, count, stats, results, clazz, gamesNumber);
                threads.add(thread);
                thread.start();
            }
        } catch (Exception exception) {
            LOG.fatal("cg-arena fail to start", exception);
            System.exit(1);
        }
    }

    public static void stop() {
        threads.forEach(ArenaThread::cancel);
        threads.clear();
    }

    public boolean isRunning() { // TODO utiliser pour disable les boutons
        for (ArenaThread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
