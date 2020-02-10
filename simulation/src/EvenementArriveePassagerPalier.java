public class EvenementArriveePassagerPalier extends Evenement {
    /* APP: Arrivée Passager Palier
       L'instant précis ou un nouveau passager arrive sur un palier donné.
    */

    private Etage étage;

    public EvenementArriveePassagerPalier(long d, Etage edd) {
	super(d);
	étage = edd;
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("APP ");
	buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
    	assert étage != null;
    	assert immeuble.étage(étage.numéro()) == étage;
    	Passager p = new Passager(date, étage, immeuble);

      Cabine c = immeuble.cabine;

      //INITIALISATION
      if (c.intention() == '-') {
        c.changerIntention(p.sens());
        //premier FPC
        echeancier.ajouter(new EvenementFermeturePorteCabine(this.date + tempsPourEntrerOuSortirDeLaCabine + tempsPourOuvrirOuFermerLesPortes));
        //vérification pour rentrer instantanément
        //vérification de l'etage
        if (étage == c.étage && c.porteOuverte) {
            //vérification du sens et fait monter passager
            if (c.faireMonterPassager(p)) {
              //on ajoute un nouvel APP dans l'echeancier
              echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.étage.arrivéeSuivante(), this.étage));
            }
        }else{
          //ajout du passager à son étage de départ
          étage.ajouter(p);
        }


        //CAS NORMAL
      }else{
        //vérification pour rentrer instantanément
        //vérification de l'etage
        if (étage == c.étage && c.porteOuverte) {
            //vérification du sens et fait monter passager
            if (c.faireMonterPassager(p)) {
              //on ajoute un nouvel APP dans l'echeancier
              echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.étage.arrivéeSuivante(), this.étage));

              //on décale la fermeture des portes
              echeancier.decalerFPC();
            }
        }else{
          //ajout du passager à son étage de départ
          étage.ajouter(p);
        }
      }

    }


}
