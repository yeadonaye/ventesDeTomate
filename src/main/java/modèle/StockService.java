package modèle;

public class StockService {

    public boolean retirerStock(Tomate tomate, int quantite) {
        int stock = tomate.getStock();
        if (stock >= quantite) {
            tomate.setStock(stock - quantite);
            return true;
        }
        return false;
    }

    public void remettreStock(Tomate tomate, int quantite) {
        tomate.setStock(tomate.getStock() + quantite);
    }
}
