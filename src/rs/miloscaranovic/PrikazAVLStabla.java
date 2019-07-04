/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.miloscaranovic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

/**
 *
 * @author Milos PC
 */
public class PrikazAVLStabla extends Pane {

    private AVLStablo<Integer> stablo = new AVLStablo<>();
    private final double precnikCvora = 15;
    private final double vertikalniRazmak = 50;

    PrikazAVLStabla(AVLStablo<Integer> stablo) {
        this.stablo = stablo;
        setStatus("Stablo je prazno!");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));   // Prikaz prosledjene poruke na ekranu
    }

    public void prikaziAVLStablo() {
        this.getChildren().clear(); // Ocisti pane
        if (stablo.vratiKoren() != null) {
            // Stablo se ispisuje rekurzivno ako stablo (koren) postoji
            prikaziAVLStablo(stablo.vratiKoren(), getWidth() / 2, vertikalniRazmak, getWidth() / 4);
        }
        // Preorder stavljen zbog ispisa
        stablo.preorder();
    }

    private void prikaziAVLStablo(CvorAVLStabla<Integer> cvor,  // Prikaz staabla
            double xKoordinata, double yKoordinata, double horizontalniRazmak) {
        if (cvor.vratiLevoDete() != null) {
            // Linija do levog deteta
            getChildren().add(new Line(xKoordinata - horizontalniRazmak, yKoordinata + vertikalniRazmak, xKoordinata, yKoordinata));
            // Ispis levog stabla rekurzivno
            prikaziAVLStablo(cvor.vratiLevoDete(), xKoordinata - horizontalniRazmak, yKoordinata + vertikalniRazmak, horizontalniRazmak / 2);
        }

        if (cvor.vratiDesnoDete() != null) {
            // Linija do desnog deteta
            getChildren().add(new Line(xKoordinata + horizontalniRazmak, yKoordinata + vertikalniRazmak, xKoordinata, yKoordinata));
            // Ispis desnog stabla rekurzivno
            prikaziAVLStablo(cvor.vratiDesnoDete(), xKoordinata + horizontalniRazmak, yKoordinata + vertikalniRazmak, horizontalniRazmak / 2);
        }

        // Prikaz cvora
        Circle circle = new Circle(xKoordinata, yKoordinata, precnikCvora);
        circle.setFill(Color.CYAN);
        circle.setStroke(Color.BLACK);
        getChildren().addAll(circle,
                new Text(xKoordinata - 4, yKoordinata + 4, cvor.vratiVrednost() + ""));
    }

}
