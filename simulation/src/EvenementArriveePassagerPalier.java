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
		Cabine c = immeuble.cabine;
		assert immeuble.étage(étage.numéro()) == étage;
		Passager p = new Passager(date, étage, immeuble);
		étage.ajouter(p);
		long oldDate = this.date;
		this.date = this.date +étage.arrivéeSuivante();
		echeancier.ajouter(this);
		//EvenementPietonArrivePalier e = new EvenementPietonArrivePalier(oldDate+délaiDePatienceAvantSportif,étage,p);
		//echeancier.ajouter(e);
		if ( (c.étage == étage) && c.isPorteOuverte() && (c.intention() == p.sens()) && !c.cabinePlein()){
			c.personneCabine(p);
			echeancier.decalerFPC();
		}else {
			notYetImplemented();
		}

	}

}
