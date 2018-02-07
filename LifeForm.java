package glenn.healy;

public abstract class LifeForm {

	/**
	 * this is the LifeForm class and is @abstract and contains the LifeForm method.
	 * @param _gridSize // the size of the grid
	 * @param _id // the object ID
     */


	public LifeForm(int _gridSize, int _id) {
		gridSize = _gridSize;
	}
	//all variables common to life forms
	//life form details
	String species;
	char symbol;
	int energy;
	int ID;
	boolean isDead = false;
	int gridSize;
	//map location
	int x;
	int y;
	//  object collision detection
	boolean nBlock = false;
	boolean eBlock = false;
	boolean sBlock = false;
	boolean wBlock = false;
	boolean skipMove = false;
	public boolean isScanTwo = false;


	public char GetSymbol() {
		return this.symbol;
	}

	public void ResetBools() {
		nBlock = false;
		eBlock = false;
		sBlock = false;
		wBlock = false;
		skipMove = false;
	}
}
