package modèle;

import java.util.ArrayList;
import java.util.List;

public class FactureService {

    private List<ElementPanier> panier;
    private double fraisPort = 5.50;

    public FactureService() {
        panier = new ArrayList<>();
    }

    public void setPanier(List<ElementPanier> elements) {
        this.panier = new ArrayList<>(elements);
    }

    public List<ElementPanier> getPanier() {
        return panier;
    }

    public double getTotalCommande() {
        double total = 0.0;
        for (ElementPanier item : panier) {
            total += item.getQuantite() * item.getTomate().getPrixTTC();
        }
        return total;
    }

    public double getFraisPort() {
        return fraisPort;
    }

    public double getTotalTTC() {
        return getTotalCommande() + getFraisPort();
    }
}
