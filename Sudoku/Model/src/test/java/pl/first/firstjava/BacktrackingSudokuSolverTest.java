package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BacktrackingSudokuSolverTest {
    BacktrackingSudokuSolver bTest = new BacktrackingSudokuSolver();
    SudokuBoard sTest = new SudokuBoard(bTest);

    @Test
    void isSafeTest() {
        sTest.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int pom = j + 1; pom < 9; pom++) {
                    assertNotEquals(sTest.get(i,j), sTest.get(i,pom));
                    assertNotEquals(sTest.get(j,i), sTest.get(pom,i));
                }
            }
        }

    }

    @Test
    void IsSafeSubBox() {
        sTest.solveGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int pom1 = 0; pom1 < 9; pom1++) {
                    for (int pom2 = pom1 + 1; pom2 < 9; pom2++) {
                        assertNotEquals(sTest.get(i * 3 + (pom1 / 3),j * 3 + (pom1 % 3)), sTest.get(i * 3 + (pom2 / 3),j * 3 + (pom2 % 3)));
                    }
                }
            }
        }
    }
    @Test
    void IsSafeSubBoxNegative(){
        sTest.solveGame();
        sTest.set(0,0,5);
        sTest.set(1,1,5);
        assertFalse(bTest.isSafe(sTest,0,0,5));
    }

    @Test
    void solve() {
        sTest.solveGame();
        bTest.solve(sTest);
        assertTrue(sTest.checkBoard());
    }
}