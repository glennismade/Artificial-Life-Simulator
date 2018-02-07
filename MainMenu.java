package glenn.healy;


import javafx.application.Application;//calls the application method.
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Application { // main menu for running the GUI.

	/**
	 * this is my mainmenu class, this contains my main method and the start method for the program.
	 * @param args  augments.
     */

	public static void main(String[] args) {
		launch(args);
	}

	Scene scene;
	Animation sim;
	int screenHeight = 400;
	int screenWidth = 600;

	private boolean isPause = false;
	private static int gridSize = 25;
	public boolean CloseEarly = false;
	AWorld AWorld;

	public MainMenu() { //mainmenu being defined in mainmenu class


	}

		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// Background work
					int tempCycleCount = 0;
					for (int i = 0; i < AWorld.GetCycles(); i++) {
						if (CloseEarly) {
							tempCycleCount = i;
							break;
						}
						while (isPause) {
							AWorld.PrintMap();
							try {
								Thread.sleep(200); // sleep
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							if (CloseEarly) {
								break;
							}
						}
						AWorld.WorldCycle(AWorld);
					}
					AWorld.FinalPrint();
					return null;
				}
			};
		}

	/**
	 * the start method for the main menu, starts the menubar.
	 * @param stage // add it to the stage.
     */
	@Override
	public void start(final Stage stage) {


		MenuBar menuBar = new MenuBar();

		final VBox vbox = new VBox();

		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(100, 10, 100, 10));
		stage.setTitle("Menu Sample");


		scene = new Scene(new VBox(), screenWidth, screenHeight);
		scene.setFill(Color.YELLOWGREEN);
		//scene.setFill(new imagepattern);


		// /--------- file menu

		// ---------- view menu
		MenuItem displayConfig = new MenuItem("Display Config");
		displayConfig.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

			}
		});

		MenuItem mapInfo = new MenuItem("Print map info");
		mapInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// display info about map
				System.out.println("Grid Size = " + gridSize);
			}
		});
		// -------- edit menu
		MenuItem decBugs = new MenuItem("Remove Bug");
		decBugs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				AWorld.BugsMinus();
				System.out.println("Num bugs = " + AWorld.GetNumBugs());
			}
		});
		MenuItem incBugs = new MenuItem("Add Bug");
		incBugs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				AWorld.BugsPlus();
				System.out.println("Num bugs = " + AWorld.GetNumBugs());
			}
		});
		// simulation menu
		MenuItem run = new MenuItem("Start");
		run.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //run glenn.healy.AWorld here
				//RunSim(glenn.healy.AWorld);

				sim = new Animation(stage, scene, screenHeight, screenWidth);


			}
		});
		MenuItem pause = new MenuItem("Play/Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //pause .AWorld here

				if (!sim.isPause) {
					sim.isPause = true;
				} else {
					sim.isPause = false;
				}
			}
		});
		MenuItem stop = new MenuItem("Quit");
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //end sim
				stage.close();
			}
		});


		// --- Menu File
	    Menu menuFile = new Menu("File"); //not implemented

		// --- Menu View
		Menu menuView = new Menu("View"); //unfinished

		// --- Menu Edit
		Menu menuEdit = new Menu("Edit"); //doesn't work.



		// --- Menu Animation
		Menu menuSim = new Menu("Run"); // works, but controls are limited.



		// edit menu
		menuEdit.getItems().addAll(incBugs, decBugs,
				new SeparatorMenuItem());

		// add to sim menu
		menuSim.getItems().addAll(run, pause, stop, new SeparatorMenuItem());


		// add to master menu
		menuBar.getMenus().addAll(menuFile, menuView, menuEdit, menuSim);

		((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);
		stage.setScene(scene);
		stage.show();
	}

}
