package glenn.healy;

//this is the bug class's parameter storage section - initialises bug variables
public class Bug extends LifeForm {

	/**
	 * this is the bug class and contains the methods scan world, and the move function
	 * @param _MapSize // the size of the array map
	 * @param _id // the Object ID
     */
	public Bug(int _MapSize, int _id) {
		super(_MapSize, _id);
		species = "Bug";
		energy = 10;
		symbol = 'b';
		ID = _id;
	}

	/**
	 * this is the scan world method.
	 * @param AWorld //the world Instance
     */

	public void ScanWorld(AWorld AWorld) {
		if (!isDead) {
			// scan north
			if (y > 0 && !skipMove) {
				if (AWorld.ChecksGrid(x, y - 1) != ' ') {
					if (Character.isDigit(AWorld.ChecksGrid(x, y - 1))) {
						energy += Character.getNumericValue(AWorld.ChecksGrid(x,
								y - 1));
						AWorld.ChangesGrid(x, y, ' ');
						y--;
						AWorld.ChangesGrid(x, y, symbol);
						skipMove = true;
					} else {
						nBlock = true;
					}
				}
			}
			// scan east
			if (x < gridSize - 1 && !skipMove) {
				if (AWorld.ChecksGrid(x + 1, y) != ' ') {
					if (Character.isDigit(AWorld.ChecksGrid(x + 1, y))) {
						energy += Character.getNumericValue(AWorld.ChecksGrid(
								x + 1, y));
						AWorld.ChangesGrid(x, y, ' ');
						x++;
						AWorld.ChangesGrid(x, y, symbol);
						skipMove = true;
					}
					else {
						eBlock = true;
					}
				}
			}
			// scan south
			if (y < gridSize - 1 && !skipMove) {
				if (AWorld.ChecksGrid(x, y + 1) != ' ') {
					if (Character.isDigit(AWorld.ChecksGrid(x, y + 1))) {

						energy += Character.getNumericValue(AWorld.ChecksGrid(x,
								y + 1));
						AWorld.ChangesGrid(x, y, ' ');
						y++;
						AWorld.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

					else {
						sBlock = true;
					}

				}
			}
			// scan west
			if (x > 0 && !skipMove) {
				if (AWorld.ChecksGrid(x - 1, y) != ' ') {
					if (Character.isDigit(AWorld.ChecksGrid(x - 1, y))) {

						energy += Character.getNumericValue(AWorld.ChecksGrid(
								x - 1, y));
						AWorld.ChangesGrid(x, y, ' ');
						x--;
						AWorld.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

					else {
						wBlock = true;
					}
				}
			}
			if (isScanTwo){
				ScanWorld(AWorld);
			}
			else {
				Move(AWorld);
			}

		}
	}

	/**
	 * the Move function,
	 * give the bug objects movement
	 * @param AWorld the world object class
     */
	public void Move(AWorld AWorld) {

		if (!isDead) {
			boolean myMove = true;
			if (!skipMove) {
				while (myMove) {
					double ranFloat = Math.random();
					if (!nBlock && y > 0 && ranFloat < 0.25) {
						AWorld.ChangesGrid(x, y, ' ');
						y--;
						AWorld.ChangesGrid(x, y, symbol);
						myMove = false;
					}
					// west
					else if (!wBlock && x > 0 && ranFloat < 0.5) {
						AWorld.ChangesGrid(x, y, ' ');
						x--;
						AWorld.ChangesGrid(x, y, symbol);
						myMove = false;
					}
					// east
					else if (!eBlock && x < gridSize - 1 && ranFloat < 0.75) {
						AWorld.ChangesGrid(x, y, ' ');
						x++;
						AWorld.ChangesGrid(x, y, symbol);
						myMove = false;
					}
					// south
					else if (!sBlock && y < gridSize - 1 && ranFloat > 0.75) {
						AWorld.ChangesGrid(x, y, ' ');
						y++;
						AWorld.ChangesGrid(x, y, symbol);
						myMove = false;
					} else {
						myMove = false;
					}
				}
			}
			ResetBools();
		}
	}
	
	public void ToggleScanTwo (){
		if (isScanTwo){
			isScanTwo = false;
		}
		else {
			isScanTwo = true;
		}
	}
}
