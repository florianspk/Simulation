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
        Cabine cabine = immeuble.cabine;
        echeancier.ajouter(new EvenementArriveePassagerPalier(date + étage.arrivéeSuivante(), étage));
        if (cabine.porteOuverte && cabine.étage == étage){
            if (cabine.intention() == '-'){
                cabine.changerIntention(p.sens());
            }
			if (cabine.faireMonterPassager(p)){
			    echeancier.ajouter(new EvenementFermeturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes+tempsPourEntrerOuSortirDeLaCabine));
            }
			else {
                étage.ajouter(p);
            }
		}
		else {
            étage.ajouter(p);

            echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p));
            echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
            if(cabine.étage.numéro() > étage.numéro())
                cabine.changerIntention('v');
            else
                cabine.changerIntention('^');

        }
    }

}
