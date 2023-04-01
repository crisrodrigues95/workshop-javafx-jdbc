package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListControler controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	private void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListControler controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}


	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	
	private synchronized <T> void loadView(String absolutName, Consumer<T> inializingAction) {
		try {
			var loader = new FXMLLoader(getClass().getResource(absolutName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = ((VBox)((ScrollPane) mainScene.getRoot()).getContent());
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			inializingAction.accept(controller);
			
			
		
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	

}
