package modèle;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import modèle.Couleur;
import modèle.ElementPanier;
import modèle.FactureService;
import modèle.Tomate;
import modèle.TypeTomate;

public class testFactureService {

    private FactureService factureService;

    @Before
    public void setUp() {
        factureService = new FactureService();
    }

    @Test
    public void testGetTotalCommande() {
        Tomate t1 = new Tomate(TypeTomate.TOMATES_CERISES, Couleur.ROUGE, "Cerise", "", "", "", 10, 50, 2.00f);
        Tomate t2 = new Tomate(TypeTomate.TOMATES, Couleur.ROUGE, "Autre", "", "", "", 5, 60, 3.00f);

        ElementPanier ep1 = new ElementPanier(t1, 2); 
        ElementPanier ep2 = new ElementPanier(t2, 1);

        factureService.setPanier(Arrays.asList(ep1, ep2));

        assertEquals(7.00, factureService.getTotalCommande(), 0.001);
    }

    @Test
    public void testGetFraisPort() {
        assertEquals(5.50, factureService.getFraisPort(), 0.001);
    }

    @Test
    public void testGetTotalTTC() {
        Tomate t = new Tomate(TypeTomate.TOMATES, Couleur.JAUNE, "Tomate Jaune", "", "", "", 8, 30, 4.00f);
        ElementPanier ep = new ElementPanier(t, 2); 

        factureService.setPanier(Arrays.asList(ep));

        double expectedTotal = 8.00 + 5.50;
        assertEquals(expectedTotal, factureService.getTotalTTC(), 0.001);
    }
}
