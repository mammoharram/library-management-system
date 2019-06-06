package ui;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Start extends Application {
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	private static Stage primStage = null;
	private BorderPane rootLayout;

	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}

	private static Stage[] allWindows = { LoginWindow.INSTANCE, AllMembersWindow.INSTANCE, AllBooksWindow.INSTANCE,
			LoginController.INSTANCE };

	public static void hideAllWindows() {
		primStage.hide();
		for (Stage st : allWindows) {
			st.hide();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		primaryStage.setTitle("Main Page");

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/library.jpg", 400, 300, false, false);

		// simply displays in ImageView the image as is
		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label("The Library System");
		splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);

		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);

		Menu optionsMenu = new Menu("Options");
		MenuItem login = new MenuItem("Login");

		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
//    			if(!LoginController.INSTANCE.isInitialized()) {
//    				LoginController.INSTANCE.init();
//    			}
//    			LoginWindow.INSTANCE.clear();
//    			LoginWindow.INSTANCE.show();
				showLoginPane();
			}

		});

		MenuItem bookIds = new MenuItem("All Book Ids");
		bookIds.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!AllBooksWindow.INSTANCE.isInitialized()) {
					AllBooksWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				AllBooksWindow.INSTANCE.setData(sb.toString());
				AllBooksWindow.INSTANCE.show();
			}
		});

		MenuItem memberIds = new MenuItem("All Member Ids");
		memberIds.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!AllMembersWindow.INSTANCE.isInitialized()) {
					AllMembersWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allMemberIds();
				Collections.sort(ids);
				System.out.println(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				System.out.println(sb.toString());
				AllMembersWindow.INSTANCE.setData(sb.toString());
				AllMembersWindow.INSTANCE.show();
			}
		});
		optionsMenu.getItems().addAll(login, bookIds, memberIds);

		mainMenu.getMenus().addAll(optionsMenu);
		Scene scene = new Scene(topContainer, 420, 375);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

	public boolean showLoginPane() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Start.class.getResource("../ui/Login.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage primStage = new Stage();
			primStage.setTitle("Login");
			primStage.initModality(Modality.WINDOW_MODAL);
			primStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			primStage.setScene(scene);

			// Set the person into the controller.
			LoginController controller = loader.getController();
			controller.setDialogStage(primStage);
//			controller.setPerson(person);

			// Show the dialog
			primStage.show();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void showBooksListWindow(MainManuController mainManuController) {
		hideAllWindows();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Start.class.getResource("./BooksList.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);

			primStage.setScene(scene);
			scene.getStylesheets().add(mainManuController.getClass().getResource("library.css").toExternalForm());
			primStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
