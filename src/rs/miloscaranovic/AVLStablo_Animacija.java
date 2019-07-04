/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.miloscaranovic;

import java.util.HashSet;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Milos PC
 */
public class AVLStablo_Animacija extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        AVLStablo<Integer> AVLTree = new AVLStablo<>(); // Create an AVL tree

        BorderPane pane = new BorderPane();
        PrikazAVLStabla view1 = new PrikazAVLStabla(AVLTree);
       
        pane.setCenter(view1);
       
        view1.setPrefWidth(250);
        pane.setPrefWidth(250);
       
        BorderPane.setMargin(view1, new Insets(10, 20, 10, 20));

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Ubaci element");
        Button btDelete = new Button("Obrisi element");
        Button btClear = new Button("Ocisti stablo");
        Button btExit = new Button("Exit");
        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(new Label("Unesi novu vrednost: "),
                tfKey, btInsert, btDelete, btClear, btExit);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);
        hBox.setAlignment(Pos.CENTER);
        BorderPane.setMargin(hBox, new Insets(10, 10, 10, 10));

        Validacija v = new Validacija();
        HashSet<Integer> treeVal = new HashSet<>();

        btInsert.setOnAction(e -> {
            if (v.prazanTextField(tfKey)) {
                invalidKey(tfKey, "Nije uneta vrednost!");
            } else {
                try {
                    int key = Integer.parseInt(tfKey.getText());
                    if (AVLTree.pronadji(key)) { // key is in the tree already
                        view1.prikaziAVLStablo();
                        view1.setStatus(key + " je vec u stablu!");
                    } else {
                        AVLTree.ubaci(key); // Insert a new key
                        view1.prikaziAVLStablo();
                        view1.setStatus(key + " ubacen u stablo!");
                        treeVal.add(key); // Adds value to HashSet for building AVL tree
                    }
                    tfKey.setText("");
                    tfKey.requestFocus();
                } catch (NumberFormatException ex) {
                    invalidKey(tfKey, "Vrednost mora biti Integer!");
                }
            }
        });

        btDelete.setOnAction(e -> {
            if (v.prazanTextField(tfKey)) {
                invalidKey(tfKey, "Nije uneta vrednost!");
            } else {
                try {
                    int key = Integer.parseInt(tfKey.getText());
                    if (!AVLTree.pronadji(key)) { // key is not in the tree
                        view1.prikaziAVLStablo();
                        view1.setStatus(key + " nije u stablu!");
                    } else {
                        AVLTree.delete(key); // Delete a key
                        view1.prikaziAVLStablo();
                        view1.setStatus(key + " obrisan iz stabla!");
                        treeVal.remove(key); // Removes key from HashSet for when tree is rebalanced
                        if (!AVLTree.daLiJePrazno()) { // Removes key from AVL tree if it is currently displayed
                            if (AVLTree.vratiBrojElemenata()== 1) { // Prevents NullPointerException when removing last node
                                AVLTree.ocistiStablo();
                            } else {
                                AVLTree.delete(key);
                            }
                            view1.prikaziAVLStablo();
                        }
                        AVLTree.delete(key); // Removes key from AVL tree
                    }
                    tfKey.setText("");
                } catch (NumberFormatException ex) {
                    invalidKey(tfKey, "Vrednost mora biti Integer!");
                }
            }
        });

        btClear.setOnAction(e -> {
            tfKey.clear();
            AVLTree.ocistiStablo();
            treeVal.clear();
           
            view1.prikaziAVLStablo();
            view1.setStatus("Stablo ocisceno!");
        });

        btExit.setOnAction(e -> {
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
            exit.setTitle("Dovidjenja!");
            exit.setContentText("Zavrsi rad aplikacije?");
            Optional<ButtonType> result = exit.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                System.exit(0);
            }
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 650, 350);
        primaryStage.setTitle("Milos Caranovic - animacija AVL stabla"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * Warns user if an invalid key is entered
     *
     * @param key
     * @param alertHeader
     */
    private void invalidKey(TextField key, String alertHeader) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozorenje");
        alert.setHeaderText(alertHeader);
        alert.setContentText("Unesite Integer vrednost i pokusajte ponovo!");
        key.requestFocus();
        alert.showAndWait();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
