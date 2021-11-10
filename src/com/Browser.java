package com;
import javafx.application.Platform;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Browser {
	static JFrame frame;
	
    private static void initAndShowGUI() {
        // This method is invoked on the EDT thread
        frame = new JFrame("Online Exam");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(true);
        frame.setUndecorated(true);
        ImageIcon img = new ImageIcon("Image/gpjlogo.png");
        frame.setIconImage(img.getImage());
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(true);
        
        

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
         BorderPane root = new BorderPane();
		        WebView webView = new WebView();
		        webView.setZoom(1);
		        webView.setContextMenuEnabled(false);
		        WebEngine webEngine = webView.getEngine();
		        webEngine.setUserAgent("SoftwareHub1.0");
		        //webEngine.load("http://192.168.43.146/online_exam/index.php");
		        webEngine.load("https://google.com/");
		        webEngine.setJavaScriptEnabled(true);
		        
		        webEngine.setOnAlert(event->{
		        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	alert.setContentText(event.getData());
		        	alert.showAndWait();
		        	frame.setAlwaysOnTop(true);
		        	
		        });
		        WebView popupView = new WebView();
                WebEngine tp = popupView.getEngine();
                webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
		            public WebEngine call(PopupFeatures param) {

		                Stage stage = new Stage(StageStyle.UTILITY);
		                BorderPane rootChild = new BorderPane(); 
		                rootChild.setCenter(popupView);
		                stage.setScene(new Scene(rootChild));
		                
		                Button b1 = new Button("PRINT");
				        b1.setStyle("-fx-background-color:#ff0000;-fx-text-fill:#ffffff;-fx-font-weight:bold;");
				        b1.setMinSize(100,40);
				        b1.setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent event) {
				            	PrinterJob job = PrinterJob.createPrinterJob();
				                if (job != null) {
				                    tp.print(job);
				                    job.endJob();
				                    frame.setAlwaysOnTop(true);
				                }
				            }

				        });
				        rootChild.setBottom(b1);
				        rootChild.setAlignment(b1, Pos.TOP_CENTER);
				        stage.show();
		                return tp;
		             }

		        });
		        	       
		        
		       
		        Button b = new Button("EXIT");
		        b.setStyle("-fx-background-color:#ff0000;-fx-text-fill:#ffffff;-fx-font-weight:bold;");
		        b.setMinSize(100,40);
		        b.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(0);
		            }

        });
        root.setCenter(webView);
        root.setTop(b);
        Scene scene = new Scene(root);
        return (scene);
    }

    public static void main(String[] args) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    	
    	
    }
}
