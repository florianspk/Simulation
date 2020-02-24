public class EvenementPietonArrivePalier extends Evenement {
    /* PAP: Pieton Arrive Palier
       L'instant précis ou un passager qui à décidé de continuer à pieds arrive sur un palier donné.
       Classe à faire complètement par vos soins.
    */

    public Passager piéton;

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
        buffer.append("PAP ");
        buffer.append(piéton.étageDépart().numéro());
        buffer.append(" #");
        buffer.append(piéton.numéroDeCréation());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	notYetImplemented();
    }

    public EvenementPietonArrivePalier(long d, Passager p) {
	// Signature approximative et temporaire... juste pour que cela compile.
	super(d);
	piéton = p;
    }
    
}
