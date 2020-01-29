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
		this.date = this.date +étage.arrivéeSuivante();
		echeancier.ajouter(this);
		if (c.isPorteOuverte() && (c.intention() == p.sens() || c.intention() == '-') && !c.cabinePlein()){
			c.personneCabine(p);
			c.étage.supprimerPassager(p);
			echeancier.decalerFPC();
		}
		assert echeancier.contient(this);
	}

}
