package glenn.healy;

import java.util.Random;


public class AWorld {

	/**
	 * this is the AWorld Class and contains methods like printMap and generateMap
	 */

	char[][] AGrid;

	int MapSize;

	private int numCycles = 100;
	private int pauseTime = 100; // 1000 milliseconds is one second. ms.

	private int numBugs = 0;
	private int numFood = 0;
	private int numObs = 0;

	private boolean isShowCycles = true;
	private boolean isPrint = true;

	Bug[] bugArray;
	public void PopulateWorld() { // populate world function.

		GenerateMap();


		bugArray = new Bug[numBugs]; //adds bugs to a free position in the array, then the next and so on.
		for (int i = 0; i < numBugs; i++) {
			bugArray[i] = new Bug(MapSize, i);
			if (AGrid[bugArray[i].x][bugArray[i].y] == ' ') {
				AGrid[bugArray[i].x][bugArray[i].y] = bugArray[i].GetSymbol();
			} else {
				i--;
			}
		}
		for (int i = 0; i < numFood; i++) { //adds food to the next position in the array.

			int ranX = RandomCoord();
			int ranY = RandomCoord();
			if (AGrid[ranX][ranY] == ' ') {
				// randomises the bonus value of food, 1..5
				AGrid[ranX][ranY] = Character.forDigit(
						((RandomCoord() % 8) + 1), 5);
			} else {
				i--;
			}
		}
		for (int i = 0; i < numObs; i++) {
			int ranX = RandomCoord();
			int ranY = RandomCoord();
			if (AGrid[ranX][ranY] == ' ') {
				AGrid[RandomCoord()][RandomCoord()] = 'X';
			} else {
				i--;
			}
		}
	}

	public void GenerateMap() {
		// passed in from main class, the map size of glenn.healy.AWorld;
		AGrid = new char[MapSize][MapSize];

		for (int x = 0; x < MapSize; x++) { // adds black space to the array.
			for (int y = 0; y < MapSize; y++) {
				AGrid[x][y] = ' ';
			}
		}
	}

	public void PrintMap() { //prints the map to the scene view

		if (isShowCycles) {
			System.out.print(" ");
			for (int x = 0; x < MapSize; x++) {
				System.out.print("--");
			}
			System.out.println();

			for (int x = 0; x < MapSize; x++) {
				System.out.print("|");
				for (int y = 0; y < MapSize; y++) {
					System.out.print(AGrid[x][y] + " ");
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.print(" ");
			for (int x = 0; x < MapSize; x++) {
				System.out.print("--");
			}
			System.out.println();
		}
	}

	public void PrintGrid() { //prints the map to the console.

		if (isShowCycles) {
			System.out.print(" ");
			for (int x = 0; x < MapSize; x++) {
				System.out.print("--");
			}
			System.out.println();
			// print grid row by c
			for (int x = 0; x < MapSize; x++) {
				System.out.print("|");
				for (int y = 0; y < MapSize; y++) {
					System.out.print(AGrid[x][y] + " ");
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.print(" ");
			for (int x = 0; x < MapSize; x++) {
				System.out.print("--");
			}
			System.out.println();
		}
	}

	public void ClearGrid() {
		for (int i = 0; i < MapSize; i++) { // clears map
			System.out.println();
		}
	}

	public int RandomCoord() {
		Random randomGen = new Random();
		int maxRange = MapSize - 1;
		int minRange = 1;
		int ranInt = randomGen.nextInt((maxRange - minRange) + 1) + minRange;
		return ranInt;
	}

	public char ChecksGrid(int x, int y) {
		return AGrid[x][y];
	}

	public void ChangesGrid(int x, int y, char sym) {
		AGrid[x][y] = sym;
	}

	public void FinalPrint() {
		if (isPrint) {
			for (int i = 0; i < numBugs; i++) {
				System.out.println("glenn.healy.Bug num: " + Integer.toString(i)
						+ "       Final energy lvl: "
						+ Integer.toString(bugArray[i].energy));
			}
			System.out.println();
			System.out.println(Integer.toString(numCycles) + " cycles" + "\n"
					+ Integer.toString(numFood) + " init food" + "\n"
					+ Integer.toString(numObs) + " obs");
		}
	}

	/**
	 * this cycles through a clock for the world
	 * @param AWorld // an instance of the world class.
     */
	public void WorldCycle(AWorld AWorld) {
		// for (int i = 0; i < numCycles; i++) {
		AWorld.PrintGrid();
		for (int j = 0; j < numBugs; j++) { // bugs scan and move
			bugArray[j].ScanWorld(AWorld);
		}
		// Sleep();
		Sleep();
		AWorld.ClearGrid();
		// }
		// FinalPrint(); // prints some final details about simulation
	}

	public int GetCycles() {
		return numCycles;
	}

	private void Sleep() {
		if (isShowCycles) {
			try {
				Thread.sleep(pauseTime); // sleep
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public int GetNumBugs() {
		return numBugs;
	}


	public void BugsPlus() {
		numBugs++;
	}
	public void BugsMinus() {
		numBugs--;
	}

	public void ToggleAllBugsScan() {
		for (int i = 0; i < numBugs; i++) {
			bugArray[i].ToggleScanTwo();
		}
	}
}
