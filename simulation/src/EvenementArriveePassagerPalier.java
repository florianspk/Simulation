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
	if (immeuble.cabine.porteOuverte && (immeuble.cabine.intention() == p.sens() || immeuble.cabine.intention() == '-') && immeuble.cabine.cabinePlein()){
		immeuble.cabine.faireMonterPassager(p);
		immeuble.cabine.changerIntention(p.sens());
		// TODO temps
	}else {
		notYetImplemented();
	}
	}

}
