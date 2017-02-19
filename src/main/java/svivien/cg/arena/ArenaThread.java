package svivien.cg.arena;

import svivien.cg.arena.engine.Engine;
import svivien.cg.arena.util.Mutable;
import javafx.collections.ObservableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.List;

public class ArenaThread extends Thread {
    private static final Log LOG = LogFactory.getLog(ArenaThread.class);

    private int id;
    private List<String> commandLines;
    private Mutable<Integer> count;
    private ObservableList<PlayerStats> stats;
    private ObservableList<GameResult> results;
    private int n;
    private Class<Engine> clazz;
    private volatile Thread currentThread = null;

    public ArenaThread(int id, List<String> commandLines, Mutable<Integer> count, ObservableList<PlayerStats> stats, ObservableList<GameResult> results, Class<Engine> clazz, int n) {
        this.id = id;
        this.commandLines = commandLines;
        this.count = count;
        this.stats = stats;
        this.results = results;
        this.n = n;
        this.clazz = clazz;
    }

    public void cancel() {
        currentThread.interrupt();
    }

    @Override
    public void run() {
        currentThread = Thread.currentThread();

        while (true) {
            if (currentThread.isInterrupted()) {
                LOG.info("Thread " + id + " has been stopped");
                break;
            }

            int game = 0;
            synchronized (count) {
                if (count.getValue() < n) {
                    game = count.getValue() + 1;
                    count.setValue(game);
                }
            }

            if (game == 0) {
                // End of the program
                break;
            }

            try {
                Engine engine = clazz.newInstance();
                engine.setCommandLines(commandLines);
                GameResult result = engine.start();

                List<Integer> positions = result.getPositions();
                Collections.reverse(positions);

                synchronized (stats) {
                    for (int j = 0; j < positions.size(); ++j) {
                        stats.get(positions.get(j)).add(j);
                    }
                }

                synchronized (results) {
                    result.setId(results.size());
                    results.add(result);
                }

                LOG.info("Thread " + id + " : end of the game " + game);

                if (game == n) {
                    for (int i = 0; i < stats.size(); ++i) {
                        LOG.info("Player " + (i + 1) + " stats : " + stats.get(i));
                    }
                }
            } catch (Exception exception) {
                LOG.error("Exception in an ArenaThread " + id, exception);
            }
        }
    }

}
