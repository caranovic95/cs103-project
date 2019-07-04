/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.miloscaranovic;

/**
 *
 * @author Milos PC
 * @param <E>
 */
public class CvorAVLStabla<E extends Comparable<E>> {   // Cvor stabla moze biti bilo koji objekat koji moze da se poredi
                                                        // (koji implementira interfejs Comparable)

    private E vrednost;                                 // Vrednost cvora (u slucaju Integer-a recimo 5, 57, 109...)
    private CvorAVLStabla<E> levoDete;                  // Levo dete cvora
    private CvorAVLStabla<E> desnoDete;                 // Desno dete cvora
    private int visinaCvora = 0;                        // Visina cvora (0 ako je cvor = list = nema dece) 
                                                        // Visina je vertikalna udaljenost cvora od korena stabla

                                                        // Get i set metode za gorenavedene atribute
    public CvorAVLStabla(E vrednost) {
        this.vrednost = vrednost;
    }

    public E vratiVrednost() {
        return vrednost;
    }

    public void postaviVrednost(E vrednost) {
        this.vrednost = vrednost;
    }

    public CvorAVLStabla<E> vratiLevoDete() {
        return levoDete;
    }

    public void postaviLevoDete(CvorAVLStabla<E> levoDete) {
        this.levoDete = levoDete;
    }

    public CvorAVLStabla<E> vratiDesnoDete() {
        return desnoDete;
    }

    public void postaviDesnoDete(CvorAVLStabla<E> desnoDete) {
        this.desnoDete = desnoDete;
    }

    public int vratiVisinuCvora() {
        return visinaCvora;
    }

    public void postaviVisinuCvora(int visinaCvora) {
        this.visinaCvora = visinaCvora;
    }                                                   // Kraj get i set metoda

    public void azurirajVisinu() {                      // Metoda izracunava visinu cvora:
                                                        // 1 - ako je cvor list = nema decu
                                                        // U ostalim slucajevima: 1 + max(visinaLevogDeteta,visinaDesnogDeteta)
        int visinaLevogDeteta = 0;
        int visinaDesnogDeteta = 0;
        
        if (vratiLevoDete() != null) {
            visinaLevogDeteta = vratiLevoDete().vratiVisinuCvora();
        }
        if (vratiDesnoDete() != null) {
            visinaDesnogDeteta = vratiDesnoDete().vratiVisinuCvora();
        }
        
        int pravaVisinaCvora = 1 + Math.max(visinaLevogDeteta,visinaDesnogDeteta);
        
        postaviVisinuCvora(pravaVisinaCvora);
    }

    public int vratiFaktorBalansiranja() {              // Metoda vraca faktor balansiranja
        int balans = 0;                                 // 0 - ako nema decu
        if (vratiLevoDete() != null) {                  // U ostalim slucajevima: visinaLevogDeteta - visinaDesnogDeteta
            balans += vratiLevoDete().vratiVisinuCvora();
        }
        if (vratiDesnoDete() != null) {
            balans -= vratiDesnoDete().vratiVisinuCvora();
        }
        return balans;
    }

}
