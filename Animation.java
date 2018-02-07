package glenn.healy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.WritableValue;

public class Animation {

	/**
	 * this is the animation class and contains the methods or
	 */

	//variables for the main simulation system

	ABug[] bugArray;
	Food[] FoodArray;
	double startTime;
	double startFoodTime;
	Timeline anim;
	public int numBugs = 5;
	int numFood = 10;
	static double screenWidth;
	static double screenHeight;
	double x = screenWidth / 2;
	double y = screenHeight / 2;
	double x2 = screenWidth / 2;
	double y2 = screenHeight / 2;


	boolean isPause = false;
	// keyframe animator controller.
	KeyFrame frame;


	/**
	 * this is the aniamtion method and contains the implimentation od the animation class
	 * @param stage // the window
	 * @param scene // the container for the objects
	 * @param sHeight // the scene height
     * @param sWidth // the scene width
     */
	public Animation(Stage stage, Scene scene, int sHeight, int sWidth) {


		bugArray = new ABug[numBugs];
		FoodArray = new Food[numFood];
		screenWidth = sWidth;
		screenHeight = sHeight;
		// /get start time
		startTime = System.currentTimeMillis();
		startFoodTime = System.currentTimeMillis();
		start(stage, scene);
	}

	/**
	 * this is the start method and is used to start the stage.
	 * @param primaryStage the main stage in the program
	 * @param scene the scene (act) of the window that contains the objects.
     */

	public void start(Stage primaryStage, Scene scene) {
		final ABug singleBug = new ABug();
		final StackPane root = new StackPane();


		for (int i = 0; i < numBugs; i++) {
			bugArray[i] = new ABug();
			root.getChildren().addAll(bugArray[i].GetCircle());
		}
		// now do same for vegetables
		for (int i = 0; i < numFood; i++) {
			FoodArray[i] = new Food();
			root.getChildren().addAll(FoodArray[i].GetCircle());
		}
		((VBox) scene.getRoot()).getChildren().addAll(root);
		frame = new KeyFrame(Duration.millis(27),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {

						if (!isPause) {

							for (int i = 0; i < numBugs; i++) {
								if (bugArray[i].GetCircle().getCenterX()
										+ bugArray[i].GetCircle()
										.getTranslateX() < bugArray[i]
										.GetCircle().getRadius()
										|| bugArray[i].GetCircle().getCenterX()
										+ bugArray[i].GetCircle()
										.getTranslateX() > screenWidth
										- bugArray[i].GetCircle()
										.getRadius()) {
									float temp = bugArray[i].GetDX();
									temp *= -1;
									bugArray[i].SetDX(temp);

								}
								if (bugArray[i].GetCircle().getCenterY()
										+ bugArray[i].GetCircle()
										.getTranslateY() < bugArray[i]
										.GetCircle().getRadius() + 5
										|| bugArray[i].GetCircle().getCenterY()
										+ bugArray[i].GetCircle()
										.getTranslateY() > screenHeight
										- bugArray[i].GetCircle()
										.getRadius() - 25) {
									float temp = bugArray[i].GetDY();
									temp *= -1;
									bugArray[i].SetDY(temp);
								}
							}

							for (int i = 0; i < numBugs; i++) {

								double bugX = bugArray[i].GetCircle()
										.getTranslateX()
										+ bugArray[i].GetCircle().getCenterX();
								double bugY = bugArray[i].GetCircle()
										.getTranslateY()
										+ bugArray[i].GetCircle().getCenterY();

								for (int j = 0; j < numFood; j++) {
									double foodX = FoodArray[j].GetCircle()
											.getTranslateX()
											+ FoodArray[j].GetCircle()
											.getCenterX();
									double foodY = FoodArray[j].GetCircle()
											.getTranslateY()
											+ FoodArray[j].GetCircle()
											.getCenterY();
									if (dist(bugX, foodX, bugY, foodY) < bugArray[i].scanDistance
											&& FoodArray[j].isAlive) {

										singleBug.isRandMove = false;
										if (bugX > foodX) {
											bugArray[i].deltaX -= 0.05f;
										} else {
											bugArray[i].deltaX += 0.05f;
										}
										if (bugY > foodY) {
											bugArray[i].deltaY -= 0.05f;
										} else {
											bugArray[i].deltaY += 0.05f;
										}
									} else {
										bugArray[i].isRandMove = true;
									}

									if (dist(bugX, foodX, bugY, foodY) < bugArray[i]
											.GetCircle().getRadius() * 2
											&& FoodArray[j].isAlive) {
										// collision with food
										FoodArray[j].isAlive = false;
										root.getChildren().remove(
												FoodArray[j].GetCircle());
										// here after eating food, the bug
										// stands still till next random move
										bugArray[i].SetDX(0f);
										bugArray[i].SetDY(0f);
									}
								}
							}

							// choose random direction and speed every 5 seconds
							if (System.currentTimeMillis() > startTime + 5000) {
								startTime = System.currentTimeMillis();
								for (int i = 0; i < numBugs; i++) {
									if (bugArray[i].isRandMove) {
										bugArray[i].RandMoveX();
										bugArray[i].RandMoveY();
									}
								}
							}


							// set translate x y for all bugs
							for (int i = 0; i < numBugs; i++) {
								bugArray[i].GetCircle().setTranslateX(
										bugArray[i].GetCircle().getTranslateX()
												+ bugArray[i].GetDX());
								bugArray[i].GetCircle().setTranslateY(
										bugArray[i].GetCircle().getTranslateY()
												+ bugArray[i].GetDY());
							}
						}
					}

					/**
					 *this is the dist method, it contains the functions of double and is used to calcualate distance between objects.
					 * @param posBall1x
					 * @param posBall2x
                     * @param posBall1y
                     * @param posBall2y
                     * @return
                     */
					private double dist(double posBall1x, double posBall2x,
										double posBall1y, double posBall2y) {
						// calc euclidean distance
						double diffX = posBall1x - posBall2x;
						double diffY = posBall1y - posBall2y;

						double distance = (diffX * diffX) + (diffY * diffY);
						distance = Math.sqrt(distance);
						// return euclidean distance
						return distance;
					}

				});

		WritableValue<Number> startXVal = new SimpleDoubleProperty(100.0);
		anim = TimelineBuilder
				.create()
				.autoReverse(false)
				.keyFrames(
						frame,
						new KeyFrame(new Duration(13), new KeyValue(startXVal,
								300.0, Interpolator.LINEAR)))
				.cycleCount(Timeline.INDEFINITE).build();
		anim.play();
		primaryStage.setTitle("Animation Running");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		primaryStage.show();
	}

}
