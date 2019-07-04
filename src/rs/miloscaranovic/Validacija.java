package rs.miloscaranovic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Milos PC
 */
import javafx.scene.control.TextField;

public class Validacija {
    /**
     * Proverava da li je vrednost uneta u TextField prazna
     *
     * @param t
     * @return true if empty
     */
    public boolean prazanTextField(TextField t) {
        return t.getText().trim().equals("");
    }
}
