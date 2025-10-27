package modèle;

public class ElementPanier {
	private Tomate tomate;
	private int quantite;

	public ElementPanier(Tomate tomate, int quantite) {
		this.tomate = tomate;
		this.quantite = quantite;
	}

	public Tomate getTomate() {
		return tomate;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
}
