public class EvenementFermeturePorteCabine extends Evenement {
    /* FPC: Fermeture Porte Cabine
       L'instant précis ou la porte termine de se fermer.
       Tant que la porte n'est pas complètement fermée, il est possible pour un passager
       de provoquer la réouverture de la porte. Dans ce cas le FPC est décalé dans le temps
       selon la méthode decalerFPC qui est dans l'échéancier.
    */

    public EvenementFermeturePorteCabine(long d) {
	super(d);
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("FPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	Cabine cabine = immeuble.cabine;
	assert cabine.porteOuverte;
	cabine.porteOuverte = false;

        assert ! cabine.porteOuverte;

        //Faire aller la cabine au prochain étage
        Etage destination = null;
        if(cabine.estPlein()) {
            if(cabine.intention() == '^') {
                if(cabine.étage.numéro() == immeuble.étageLePlusHaut().numéro()) {
                    cabine.changerIntention('v');
                    destination = immeuble.étage(cabine.étage.numéro()-1);
                } else {
                    destination = immeuble.étage(cabine.étage.numéro()+1);
                }
            } else {
                if(cabine.étage.numéro() == immeuble.étageLePlusBas().numéro()) {
                    cabine.changerIntention('^');
                    destination = immeuble.étage(cabine.étage.numéro()+1);
                } else {
                    destination = immeuble.étage(cabine.étage.numéro()-1);
                }
            }
        } else {
            if(cabine.étage.aDesPassagers()) {
                destination = immeuble.étage(cabine.numEtageDestinationCabine());
                cabine.updateSens();
            } else {
                if(immeuble.passagerAuDessus(cabine.etage)) {
                    destination = immeuble.etage(cabine.etage.numero()+1);
                    cabine.changerIntention('^');
                } else if(immeuble.passagerEnDessous(cabine.etage)) {
                    destination = immeuble.etage(cabine.etage.numero()-1);
                    cabine.changerIntention('v');
                }
            }
        }
        //Etage destination = immeuble.etage(cabine.numEtageDestinationCabine());
        Evenement e = new EvenementPassageCabinePalier(this.date + tempsPourBougerLaCabineDUnEtage, destination);
        echeancier.ajouter(e);
    }


    public void setDate(long d){
	this.date = d;
    }

}
