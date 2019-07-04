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
public class AVLStablo<E extends Comparable<E>> {

    private CvorAVLStabla<E> koren;
    private int size = 0;

    public AVLStablo() {
    }

    public boolean pronadji(E element) {
        CvorAVLStabla<E> trenutniCvor = koren; // Pretraga pocinje od koren

        while (trenutniCvor != null) {
            if (element.compareTo(trenutniCvor.vratiVrednost()) < 0) {
                trenutniCvor = trenutniCvor.vratiLevoDete();
            } else if (element.compareTo(trenutniCvor.vratiVrednost()) > 0) {
                trenutniCvor = trenutniCvor.vratiDesnoDete();
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean ubaci(E noviElement) {
        if (koren == null) {
            koren = new CvorAVLStabla<>(noviElement);
            koren.postaviVisinuCvora(1);
            size++;
            return true;
        } else {
            CvorAVLStabla<E> roditelj = null;
            CvorAVLStabla<E> trenutniCvor = koren;
            while (trenutniCvor != null) {
                if (noviElement.compareTo(trenutniCvor.vratiVrednost()) < 0) {
                    roditelj = trenutniCvor;
                    trenutniCvor = trenutniCvor.vratiLevoDete();
                } else if (noviElement.compareTo(trenutniCvor.vratiVrednost()) > 0) {
                    roditelj = trenutniCvor;
                    trenutniCvor = trenutniCvor.vratiDesnoDete();
                } else {
                    return false;
                }
            }
            CvorAVLStabla<E> noviCvor = new CvorAVLStabla<>(noviElement);
            noviCvor.azurirajVisinu();
            if (noviElement.compareTo(roditelj.vratiVrednost()) < 0) {
                roditelj.postaviLevoDete(noviCvor);
            } else {
                roditelj.postaviDesnoDete(noviCvor);
            }

            BalanserAVLStabla<E> balanser = new BalanserAVLStabla<>();
            balanser.izbalansiraj(this, koren);

            size++;
            return true;
        }
    }

    public void postorder() {
        postorder(koren);
    }

    protected void preorder(CvorAVLStabla<E> cvor) {
        if (cvor == null) {
            return;
        }
        postorder(cvor.vratiLevoDete());
        postorder(cvor.vratiDesnoDete());
        System.out.print(cvor.vratiVrednost() + " ");
        System.out.println("");
    }

    public void preorder() {
        preorder(koren);
    }

    protected void postorder(CvorAVLStabla<E> cvor) {
        if (cvor == null) {
            return;
        }
        System.out.print(cvor.vratiVrednost() + " ");
        preorder(cvor.vratiLevoDete());
        preorder(cvor.vratiDesnoDete());
    }

    public int vratiBrojElemenata() {
        return size;
    }

    public CvorAVLStabla<E> vratiKoren() {
        return koren;
    }

    public ArrayList<CvorAVLStabla<E>> vratiPutanjuDoElementa(E element) {
        ArrayList<CvorAVLStabla<E>> putanjaDoElementa = new ArrayList<>();
        CvorAVLStabla<E> trenutniCvor = koren;

        while (trenutniCvor != null) {
            putanjaDoElementa.add(trenutniCvor);
            if (element.compareTo(trenutniCvor.vratiVrednost()) < 0) {
                trenutniCvor = trenutniCvor.vratiLevoDete();
            } else if (element.compareTo(trenutniCvor.vratiVrednost()) > 0) {
                trenutniCvor = trenutniCvor.vratiDesnoDete();
            } else {
                break;
            }
        }

        return putanjaDoElementa;
    }

    public boolean delete(E element) {
        if (koren == null) {
            return false; // Ako je koren null, celo stablo je null, samim tim nema sta da se brise iz stabla
        }
        // Potrebno je da se locira element za brisanje, kao i njegov roditelj
        CvorAVLStabla<E> roditelj = null;
        CvorAVLStabla<E> trenutni = koren;
        while (trenutni != null) {
            if (element.compareTo(trenutni.vratiVrednost()) < 0) {
                roditelj = trenutni;
                trenutni = trenutni.vratiLevoDete();
            } else if (element.compareTo(trenutni.vratiVrednost()) > 0) {
                roditelj = trenutni;
                trenutni = trenutni.vratiDesnoDete();
            } else {
                break; // Trenutni element pokazuje na vrednost za brisanje
            }
        }

        if (trenutni == null) {
            return false; // Element za brisanje ne postoji u stablu
        }
        // Slucaj 1: trenutni cvor = cvor za brisanje nema levo dete
        if (trenutni.vratiLevoDete() == null) {
            // Povezi roditelja sa desnim detetom
            // Ako je roditelj elementa za brisanje == null, onda je trenutni koren i on treba da se izbaci
            // Novi koren postaje desno dete
            if (roditelj == null) {
                koren = trenutni.vratiDesnoDete();
            } // Ako postoji roditelj elementa za brisanje
            else {
                if (element.compareTo(roditelj.vratiVrednost()) < 0) {
                    roditelj.postaviLevoDete(trenutni.vratiDesnoDete());
                } else {
                    roditelj.postaviDesnoDete(trenutni.vratiDesnoDete());
                }

                // Balansiranje stabla
                //izbalansiraj(roditelj.vratiVrednost());
            }
        } else {
            // Slucaj 2: trenutni cvor = cvor za brisanje ima levo dete 
            // Pronadji najdesnije dete u levom podstablu trenutnog cvora, odnosno cvora za brisanje
            // kao i njegovog roditelja
            CvorAVLStabla<E> roditeljNajdesnijegULevomPodstablu = trenutni;
            CvorAVLStabla<E> najdesnijiULevomPodstablu = trenutni.vratiLevoDete();

            while (najdesnijiULevomPodstablu.vratiDesnoDete() != null) {
                roditeljNajdesnijegULevomPodstablu = najdesnijiULevomPodstablu;
                najdesnijiULevomPodstablu = najdesnijiULevomPodstablu.vratiDesnoDete(); // Ide se do kraja na desno
            }

            // Vrednost trenutnog postaje vrednost najdesnijiULevomPodstablu
            trenutni.postaviVrednost(najdesnijiULevomPodstablu.vratiVrednost());

            // najdesnijiULevomPodstablu se brise iz stabla zato sto mu je vrednost preneta u trenutni
            if (roditeljNajdesnijegULevomPodstablu.vratiDesnoDete() == najdesnijiULevomPodstablu) {
                roditeljNajdesnijegULevomPodstablu.postaviDesnoDete(najdesnijiULevomPodstablu.vratiLevoDete());
            } else // Slucaj kada je roditeljNajdesnijegULevomPodstablu == trenutni
            {
                roditeljNajdesnijegULevomPodstablu.postaviLevoDete(najdesnijiULevomPodstablu.vratiLevoDete());
            }

            // Balansiranje stabla
            //izbalansiraj(roditeljNajdesnijegULevomPodstablu.vratiVrednost());
        }

        size--;
        return true; // Element deleted
    }

    // Ciscenje stabla
    public void ocistiStablo() {
        koren = null;
        size = 0;
    }

    // Da li stablo ima unetih elemenata
    // size se koristi da bi se znalo koliko ima elemenata
    public boolean daLiJePrazno() {
        return size == 0;
    }

    public void postaviKoren(CvorAVLStabla<E> koren) {
        this.koren = koren;
    }

}
