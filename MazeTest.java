package WuNg.cs146.project3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeTest {

	@Test
	void test() {
		for (int i = 3; i <= 8; i++) {
			Maze m = new Maze(i);
			m.Generate(0);
			System.out.println(i + "x" + i + " Maze: ");
			System.out.println(m.printMaze());
			long time = System.nanoTime();
			System.out.println(m.BFS());
			time = System.nanoTime();
			System.out.println("Time for BFS: " + (System.nanoTime() - time));
			System.out.println(m.DFS());
			time = System.nanoTime();
			System.out.println("Time for DFS: " + (System.nanoTime() - time));
			System.out.println();
		}
	}
}
