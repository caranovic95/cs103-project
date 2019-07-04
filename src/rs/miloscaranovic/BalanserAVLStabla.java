/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.miloscaranovic;

import java.util.ArrayList;

/**
 *
 * @author Milos PC
 * @param <E>
 */
public class BalanserAVLStabla<E extends Comparable<E>> {
    
    public void izbalansiraj(AVLStablo<E> stablo) {
        izbalansiraj(stablo, stablo.vratiKoren());
    }
    
    public void izbalansiraj(AVLStablo<E> stablo, CvorAVLStabla<E> cvor) {
        if (stablo.vratiKoren() == null) {
            System.out.println("Stablo je prazno (samim tim i balansirano)!");
            return;
        }
        if (cvor == null) {
            System.out.println("Cvor za balansiranje je NULL!");
            return;
        }
        cvor.azurirajVisinu();
        izbalansiraj(stablo, cvor.vratiLevoDete());
        izbalansiraj(stablo, cvor.vratiDesnoDete());
        
        int faktorBalansiranja = cvor.vratiFaktorBalansiranja();
        if (faktorBalansiranja > 1) {
            rotacijaCvoraUdesno(cvor, stablo);
        } else if (faktorBalansiranja < -1) {
            rotacijaCvoraUlevo(cvor, stablo);
        }
    }
    
    private CvorAVLStabla<E> rotacijaCvoraUdesno(CvorAVLStabla<E> cvor, AVLStablo<E> stablo) {
        if (cvor.vratiLevoDete() != null) {
            if (cvor.vratiLevoDete().vratiFaktorBalansiranja() < 0) {
                rotacijaCvoraUlevo(cvor.vratiLevoDete(), stablo);
            }
        }
        if (stablo.vratiKoren() == cvor) {
            stablo.postaviKoren(cvor.vratiLevoDete());
        }
        else {
            ArrayList<CvorAVLStabla<E>> putanjaDoCvora = stablo.vratiPutanjuDoElementa(cvor.vratiVrednost()); // putanja do cvora koji treba da se balansira
            CvorAVLStabla<E> roditeljCvora = putanjaDoCvora.get(putanjaDoCvora.size()-2);// roditelj cvora za balansiranje
            // -2 zato sto je roditelj cvora za balansiranje pretposlednji cvor
            if (roditeljCvora.vratiVrednost().compareTo(cvor.vratiVrednost()) < 0 ) { // da li je levo dete
                roditeljCvora.postaviDesnoDete(cvor.vratiLevoDete()); // postaje desno
            }
            else {
                roditeljCvora.postaviLevoDete(cvor.vratiLevoDete());// ako je desno, onda postaje levo
            }
            
        }
        CvorAVLStabla<E> desnoDeteLevogDeteta = cvor.vratiLevoDete().vratiDesnoDete();
        cvor.vratiLevoDete().postaviDesnoDete(cvor);
        cvor.postaviLevoDete(desnoDeteLevogDeteta);
        return cvor;
    }
    
    public CvorAVLStabla<E> rotacijaCvoraUlevo(CvorAVLStabla<E> cvor, AVLStablo<E> stablo) {
        if (cvor.vratiDesnoDete()!= null) {
            if (cvor.vratiDesnoDete().vratiFaktorBalansiranja() > 0) {// ako rotiramo u desno, tj ako je faktor balansiranja l-r = +
                // ako ima vise elemenata sa leve u odnosu na desnu stranu
                rotacijaCvoraUdesno(cvor.vratiDesnoDete(), stablo);// rotacija
            }
        }
        if (stablo.vratiKoren() == cvor) {//ako se koren rotira, neki drugi cvor postaje koren (U ovom slucaju desno dete korena)
            stablo.postaviKoren(cvor.vratiDesnoDete());
        }
        else {
            ArrayList<CvorAVLStabla<E>> putanjaDoCvora = stablo.vratiPutanjuDoElementa(cvor.vratiVrednost());
            CvorAVLStabla<E> roditeljCvora = putanjaDoCvora.get(putanjaDoCvora.size()-2);
            if (roditeljCvora.vratiVrednost().compareTo(cvor.vratiVrednost()) < 0 ) {
                roditeljCvora.postaviDesnoDete(cvor.vratiDesnoDete());// ako je manje, vraca se desno dete i postavlja se za desno dete
            }
            else {
                roditeljCvora.postaviLevoDete(cvor.vratiDesnoDete());// ako nije, postavi levo dete na desno dete cvora
            }
        }
        CvorAVLStabla<E> levoDeteDesnogDeteta = cvor.vratiDesnoDete().vratiLevoDete(); // klasicna rotacija u levo
        cvor.vratiDesnoDete().postaviLevoDete(cvor);
        cvor.postaviDesnoDete(levoDeteDesnogDeteta);
        return cvor;
    }


}
