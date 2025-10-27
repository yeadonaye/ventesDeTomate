package modèle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modèle.Couleur;
import modèle.StockService;
import modèle.Tomate;
import modèle.TypeTomate;

public class testStockService {

    private StockService stockService;
    private Tomate tomate;

    @Before
    public void setUp() {
        stockService = new StockService();
        tomate = new Tomate(TypeTomate.TOMATES_CERISES, Couleur.ROUGE, "Cerise", "", "", "", 10, 40, 3.00f);
    }

    @Test
    public void testRetirerStock_Success() {
        boolean result = stockService.retirerStock(tomate, 5);
        assertTrue(result);
        assertEquals(5, tomate.getStock());
    }

    @Test
    public void testRetirerStock_Failure() {
        boolean result = stockService.retirerStock(tomate, 15);
        assertFalse(result);
        assertEquals(10, tomate.getStock()); // stock unchanged
    }

    @Test
    public void testRemettreStock() {
        stockService.retirerStock(tomate, 3); // now 7
        stockService.remettreStock(tomate, 5); // should be 12
        assertEquals(12, tomate.getStock());
    }
}
