public class EvenementPassageCabinePalier extends Evenement {
    /* PCP: Passage Cabine Palier
       L'instant précis où la cabine passe juste en face d'un étage précis.
       Vous pouvez modifier cette classe comme vous voulez (ajouter/modifier des méthodes etc.).
    */
    
    private Etage étage;
    
    public EvenementPassageCabinePalier(long d, Etage e) {
	super(d);
	étage = e;
    }
    
    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("PCP ");
	buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	Cabine cabine = immeuble.cabine;
	assert ! cabine.porteOuverte;
	assert étage.numéro() != cabine.étage.numéro();
	cabine.étage = étage;

	if (cabine.passagersVeulentDescendre()){
		echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
	}
	else {
		bouger(immeuble, echeancier, cabine, date + Global.tempsPourBougerLaCabineDUnEtage);
	}

    }

	static void bouger(Immeuble immeuble, Echeancier echeancier, Cabine cabine, long l) {
		Etage suiv = null;

		if (cabine.intention() == '^'){
			suiv = immeuble.étage(cabine.étage.numéro()+1);
		}
		else if (cabine.intention() == 'v'){
			suiv = immeuble.étage(cabine.étage.numéro()-1);
		}

		echeancier.ajouter(new EvenementPassageCabinePalier(l, suiv));
	}
}
