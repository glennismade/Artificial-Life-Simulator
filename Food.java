package glenn.healy;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends ALifeForm {

	/**
	 * this is the food class and is an inheritance class of ALifeForm
	 */
	int VegNum = 7;

	public Food(){
		x = stageWidth / 2;
		y = stageHeight / 2;
		circle = new Circle(x, y, VegNum);

		circle.setFill(Color.DARKRED);
		energy = (int) ((Math.random() * 9) + 1);

		RandomTranslate();
	}
	public void RandomTranslate(){
		double randX = Math.random() * stageWidth;
		double randY = Math.random() * stageHeight;

		randX -= circle.getCenterX() - 25;
		randY -= circle.getCenterY() - 25;

		circle.setTranslateX(randX);
		circle.setTranslateY(randY);
	}
}
