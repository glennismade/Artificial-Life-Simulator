package glenn.healy;

import javafx.scene.shape.Circle;

/**
 * this is my ALieForm class and is @abstract
 */
public abstract class ALifeForm {

	double stageWidth = Animation.screenWidth;
	double stageHeight = Animation.screenHeight;
	double x;
	double y;
	//int rad = 5;
	float deltaX = -1.5f, deltaY = -1.5f;
	boolean isRandMove = true;
	Circle circle;
	int energy;
	boolean isAlive = true;
	public ALifeForm() {

	}

	public Circle GetCircle() {
		return circle;
	}

	public float GetDX() {
		return deltaX;
	}

	public float GetDY() {
		return deltaY;
	}

	public void SetDX(float DX) {
		deltaX = DX;
	}

	public void SetDY(float DY) {
		deltaY = DY;
	}



}