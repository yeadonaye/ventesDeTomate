package modèle;

import java.util.ArrayList;
import java.util.List;

public class PanierService {
    private List<ElementPanier> panier;

    public PanierService() {
        this.panier = new ArrayList<>();
    }

    public List<ElementPanier> getPanier() {
        return panier;
    }

    public void ajouter(Tomate tomate, int quantite) {
        for (ElementPanier item : panier) {
            if (item.getTomate().equals(tomate)) {
                item.setQuantite(item.getQuantite() + quantite);
                return;
            }
        }
        panier.add(new ElementPanier(tomate, quantite));
    }

    public void modifierQuantite(int index, int nouvelleQuantite) {
        if (index >= 0 && index < panier.size()) {
            panier.get(index).setQuantite(nouvelleQuantite);
        }
    }

    public void vider() {
        panier.clear();
    }
    
	public void setQuantite(int index, int quantite) {
		if (index >= 0 && index < panier.size()) {
			ElementPanier item = panier.get(index);
			int oldQty = item.getQuantite();
			int delta = quantite - oldQty;
			Tomate tomate = item.getTomate();
			StockService stockService = new StockService();
			if (delta > 0) {
				// Try to take the requested additional stock; if not enough, take what's available
				if (stockService.retirerStock(tomate, delta)) {
					item.setQuantite(quantite);
				} else {
					int available = tomate.getStock();
					if (available > 0) {
						stockService.retirerStock(tomate, available);
						item.setQuantite(oldQty + available);
					}
					// if no stock available, leave quantity unchanged
				}
-				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomates.json", tomate);
+				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomatesSauvegarde.json", tomate);
			} else if (delta < 0) {
				// Returning stock when reducing quantity
				stockService.remettreStock(tomate, -delta);
				item.setQuantite(quantite);
-				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomates.json", tomate);
+				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomatesSauvegarde.json", tomate);
			}
		}
	}

    public double calculerSousTotal() {
        double total = 0;
        for (ElementPanier item : panier) {
            total += item.getQuantite() * item.getTomate().getPrixTTC();
        }
        return total;
    }
    
}
