package application;
import net.andrewewhite.slidersolver.Goal;
import net.andrewewhite.slidersolver.Puzzle;
import net.andrewewhite.slidersolver.SliderSolver;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;


/**
 * class to load the solver for the default configuration
 */
public class Loader {

    public static void solvePuzzle() throws IOException, InterruptedException {
        final String puzzleString = IOUtils.toString(new FileInputStream("klotski-puzzle.txt"));
        final String goalString   = IOUtils.toString(new FileInputStream("klotski-goal.txt"));
        final Puzzle puzzle       = new Puzzle(puzzleString);
        final Goal   goal         = new Goal(goalString);

        final SliderSolver solver   = new SliderSolver(puzzle, goal);
        final SliderSolver.Solution solution = solver.solve();

        if (solution == null) {
            System.out.println("No solutions found");
            return;
        }
        PrintStream fileStream = new PrintStream("Soluzioni.txt");
        System.setOut(fileStream);
        System.out.println(solution);
    }
}