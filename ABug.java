package glenn.healy;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ABug extends ALifeForm {



	int bugRad = 5;
	int scanDistance = 75;

	public ABug() {
		x = stageWidth / 2;
		y = stageHeight / 2;
		circle = new Circle(x, y, bugRad);
		circle.setFill(Color.BLUE);
		//bug energy level to random int between 50 and 100
		energy = (int)((Math.random() * 50) + 50);
		RandMoveX();
		RandMoveY();
		RandomTranslate();
	}

	/**
	 * this is the ABug class and contains the getcircle method and the random x and Y functions.
	 * @return returns the like get circle.  methods.
     */

	public Circle GetCircle() {
		return circle;
	} //new Circle instance


	//Random Move functions for the bug

	public void RandMoveX() {
		float randVel = (float) Math.random();
		if (deltaX < 0) {
			deltaX = -randVel;
		} else {
			deltaX = randVel;
		}
	}
	public void RandMoveY() {
		float randVel = (float) Math.random();
		if (deltaY < 0) {
			deltaY = -randVel;
		} else {
			deltaY = randVel;
		}
	}




	public void RandomTranslate() {
		double randX = Math.random() * stageWidth;
		double randY = Math.random() * stageHeight;

		randX -= circle.getCenterX() - 25;
		randY -= circle.getCenterY() - 25;

		circle.setTranslateX(randX);
		circle.setTranslateY(randY);
	}

}