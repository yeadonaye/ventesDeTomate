package modèle;

import modèle.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class testPanierService {

    private PanierService panierService;
    private Tomate tomate;

    @Before
    public void setUp() {
        panierService = new PanierService();
        tomate = new Tomate(
                TypeTomate.TOMATES_CERISES,
                Couleur.ROUGE,
                "Tomate cerise",
                "Petite et sucrée",
                "cerise.jpg",
                "Idéale pour les salades.",
                10,
                30,
                2.50f
        );
    }

    @Test
    public void testPanierVideAuDébut() {
        assertTrue(panierService.getPanier().isEmpty());
    }

    @Test
    public void testAjoutArticle() {
        panierService.ajouter(tomate, 1);
        List<ElementPanier> articles = panierService.getPanier();
        assertEquals(1, articles.size());
        assertEquals(tomate, articles.get(0).getTomate());
        assertEquals(1, articles.get(0).getQuantite());
    }

    @Test
    public void testAjoutQuantiteCumulée() {
        panierService.ajouter(tomate, 2);
        panierService.ajouter(tomate, 3);
        List<ElementPanier> articles = panierService.getPanier();
        assertEquals(1, articles.size());
        assertEquals(5, articles.get(0).getQuantite());
    }

    @Test
    public void testAjoutTomatesDifferentes() {
        Tomate tomate2 = new Tomate(
                TypeTomate.TOMATES,
                Couleur.JAUNE,
                "Roma",
                "Sucrée",
                "roma.jpg",
                "Bonne pour les sauces.",
                15,
                40,
                3.50f
        );
        panierService.ajouter(tomate, 1);
        panierService.ajouter(tomate2, 2);
        assertEquals(2, panierService.getPanier().size());
    }

    @Test
    public void testViderPanier() {
        panierService.ajouter(tomate, 1);
        panierService.vider();
        assertTrue(panierService.getPanier().isEmpty());
    }

    @Test
    public void testModifierQuantite() {
        panierService.ajouter(tomate, 2);
        panierService.modifierQuantite(0, 5);
        assertEquals(5, panierService.getPanier().get(0).getQuantite());
    }

    @Test
    public void testCalculerSousTotal() {
        Tomate t2 = new Tomate(
                TypeTomate.TOMATES,
                Couleur.JAUNE,
                "Roma",
                "Sucrée",
                "roma.jpg",
                "Bonne pour les sauces.",
                10,
                25,
                3.00f
        );
        panierService.ajouter(tomate, 4); 
        panierService.ajouter(t2, 2);    
        assertEquals(16.00, panierService.calculerSousTotal(), 0.001);
    }

    @Test
    public void testCalculerSousTotalInexact() {
        panierService.ajouter(tomate, 5);
        assertNotEquals(20.0, panierService.calculerSousTotal(), 0.001); 
    }
}
