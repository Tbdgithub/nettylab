package com.saturn.test.lab.jcip.PuzzleTest;

import com.saturn.lab.jcip.ApplyingThreadPools.Puzzle;
import com.saturn.lab.jcip.ApplyingThreadPools.PuzzleSolver;
import com.saturn.lab.jcip.ComposingObjects.Point;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzleSolverTest {

    @Test
    public void test1()
    {
        Puzzle<Point,Movement> puzzle=new Puzzle<Point, Movement>() {
            @Override
            public Point initialPosition() {
                return new Point(2,2);
            }

            @Override
            public boolean isGoal(Point position) {
                return false;
            }

            @Override
            public Set<Movement> legalMoves(Point position) {
                return new HashSet<>();
            }

            @Override
            public Point move(Point position, Movement move) {
                return new Point(1,2);
            }
        };

        PuzzleSolver ps=new PuzzleSolver(puzzle);

        try {
            List<Movement> result= ps.solve();
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
