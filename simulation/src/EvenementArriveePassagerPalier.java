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
        echeancier.ajouter(new EvenementFermeturePorteCabine(this.date + tempsPourEntrerOuSortirDeLaCabine + tempsPourOuvrirOuFermerLesPortes));
        if (étage == c.étage && c.porteOuverte) {
            if (c.faireMonterPassager(p)) {
              echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.étage.arrivéeSuivante(), this.étage));
            }
        }else{
          étage.ajouter(p);
        }
        // Cas Normal
      }else{
        if (étage == c.étage && c.porteOuverte) {
            if (c.faireMonterPassager(p)) {
              echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.étage.arrivéeSuivante(), this.étage));
              echeancier.decalerFPC();
            }
        }else{
          étage.ajouter(p);
        }
      }

    }


}
