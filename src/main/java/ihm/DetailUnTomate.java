package ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import modèle.ElementPanier;
import modèle.Tomate;
import modèle.StockService;
import modèle.OutilsBaseDonneesTomates;

public class DetailUnTomate {

	private JFrame frame;
	private JPanel panelDroite, panelGauche, panelBtn, contentPanel, prixPanel;
	private JLabel nomTomateLbl, imageTomateLabel, stockLabel, seedLabel, prixUnitLabel, valeurPrixUnitLabel;
	private JTextArea desc;
	private JScrollPane scrollPane;
	private JSpinner quantitySpinner;
	private JButton ajtPanierBtn, annulBtn;
	private JComboBox<String> comboBoxApparents;

	private List<Tomate> relatedTomates;
	private ListeTomates fenPrecedent;
	private Tomate tomate;

	public DetailUnTomate(Tomate tomate, ListeTomates previousWindow) {
		this.tomate = tomate;
		this.fenPrecedent = previousWindow;
		initialize();
		frame.setVisible(true);

		nomTomateLbl.setText(tomate.getDésignation());
		nomTomateLbl.setForeground(new Color(0, 128, 0));

		boolean enStock = tomate.getStock() > 0;
		stockLabel.setText(enStock ? "En Stock" : "Rupture de stock");
		stockLabel.setForeground(enStock ? new Color(0, 128, 0) : Color.RED);

		desc.setText(tomate.getDescription());
		seedLabel.setText("Nombre de graines : " + tomate.getNbGrainesParSachet());
		valeurPrixUnitLabel.setText(String.format("%.2f€", tomate.getPrixTTC()));
		quantitySpinner.setModel(new SpinnerNumberModel(1, 1, Math.max(tomate.getStock(), 1), 1));
		quantitySpinner.setEnabled(enStock);
		ajtPanierBtn.setEnabled(enStock);

		if (!enStock && tomate.getTomatesApparentées() != null && !tomate.getTomatesApparentées().isEmpty()) {
			relatedTomates = tomate.getTomatesApparentées();
			comboBoxApparents.removeAllItems();
			comboBoxApparents.addItem("Choisir une autre variété");
			for (Tomate related : relatedTomates) {
				comboBoxApparents.addItem(related.getDésignation());
			}
			comboBoxApparents.setVisible(true);
		} else {
			comboBoxApparents.setVisible(false);
		}

		try {
			ImageIcon icon = new ImageIcon("src/main/resources/images/Tomates200x200/" + tomate.getNomImage() + ".jpg");
			Image scaled = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			imageTomateLabel.setIcon(new ImageIcon(scaled));
		} catch (Exception e) {
			imageTomateLabel.setText("Image non trouvée");
		}
	}

	private void initialize() {
		frame = new JFrame("Ô'Tomates");
		frame.setSize(700, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		contentPanel = new JPanel(new GridLayout(1, 2));
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

		// === Panel Droite ===
		panelDroite = new JPanel();
		panelDroite.setLayout(new BoxLayout(panelDroite, BoxLayout.Y_AXIS));
		panelDroite.setBorder(new EmptyBorder(10, 10, 10, 10));

		nomTomateLbl = new JLabel("Nom de la tomate");
		nomTomateLbl.setFont(new Font("Poor Richard", Font.BOLD, 18));
		nomTomateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDroite.add(nomTomateLbl);

		imageTomateLabel = new JLabel();
		imageTomateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		imageTomateLabel.setPreferredSize(new Dimension(180, 180));
		panelDroite.add(imageTomateLabel);

		stockLabel = new JLabel();
		stockLabel.setFont(new Font("Arial", Font.BOLD, 14));
		stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDroite.add(stockLabel);

		comboBoxApparents = new JComboBox<>();
		comboBoxApparents.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboBoxApparents.setMaximumSize(new Dimension(200, 25));
		comboBoxApparents.setVisible(false);
		panelDroite.add(comboBoxApparents);

		contentPanel.add(panelDroite);

		comboBoxApparents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = comboBoxApparents.getSelectedIndex() - 1;
				if (index >= 0 && index < relatedTomates.size()) {
					new DetailUnTomate(relatedTomates.get(index), fenPrecedent);
					frame.dispose();
				}
			}
		});

		// === Panel Gauche ===
		panelGauche = new JPanel();
		panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
		panelGauche.setBorder(new EmptyBorder(10, 10, 10, 10));

		desc = new JTextArea();
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);

		scrollPane = new JScrollPane(desc);
		scrollPane.setPreferredSize(new Dimension(250, 100));
		scrollPane.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(new Color(0, 128, 0), 1),
			"Description",
			TitledBorder.LEFT,
			TitledBorder.TOP,
			new Font("Arial", Font.BOLD, 13),
			new Color(0, 128, 0)
		));
		panelGauche.add(scrollPane);

		seedLabel = new JLabel("Nombre de graines :");
		seedLabel.setFont(new Font("Arial", Font.BOLD, 14));
		seedLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panelGauche.add(seedLabel);

		prixPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		prixUnitLabel = new JLabel("Prix : ");
		prixUnitLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		valeurPrixUnitLabel = new JLabel("0,00€");
		valeurPrixUnitLabel.setFont(new Font("Arial", Font.BOLD, 14));

		quantitySpinner = new JSpinner();

		prixPanel.add(prixUnitLabel);
		prixPanel.add(valeurPrixUnitLabel);
		prixPanel.add(quantitySpinner);
		panelGauche.add(prixPanel);

		contentPanel.add(panelGauche);

		// === Panel Bas ===
		panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ajtPanierBtn = new JButton("Ajouter au panier");
		ajtPanierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantity = (int) quantitySpinner.getValue();
				StockService stockService = new StockService();
				if (!stockService.retirerStock(tomate, quantity)) {
					JOptionPane.showMessageDialog(frame, "Stock insuffisant pour la quantité demandée.", "Stock", JOptionPane.WARNING_MESSAGE);
					return;
				}
-				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomates.json", tomate);
+				OutilsBaseDonneesTomates.mettreAJourStockTomateDansJson("src/main/resources/data/tomatesSauvegarde.json", tomate);
				boolean found = false;
				for (ElementPanier item : ListeTomates.panier) {
					if (item.getTomate().equals(tomate)) {
						item.setQuantite(item.getQuantite() + quantity);
						found = true;
						break;
					}
				}
				if (!found) {
					ListeTomates.panier.add(new ElementPanier(tomate, quantity));
				}

				boolean enStock = tomate.getStock() > 0;
				stockLabel.setText(enStock ? "En Stock" : "Rupture de stock");
				stockLabel.setForeground(enStock ? new Color(0, 128, 0) : Color.RED);
				quantitySpinner.setModel(new SpinnerNumberModel(1, 1, Math.max(tomate.getStock(), 1), 1));
				ajtPanierBtn.setEnabled(enStock);

				fenPrecedent.mettreAJourPrixPanier();
				JOptionPane.showMessageDialog(frame, "Ajouté au panier !");
				frame.dispose();
				fenPrecedent.getFrame().setVisible(true);
			}
		});

		annulBtn = new JButton("Annuler");
		annulBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				if (fenPrecedent != null) {
					new ListeTomates();
				}
			}
		});

		panelBtn.add(ajtPanierBtn);
		panelBtn.add(annulBtn);
		frame.getContentPane().add(panelBtn, BorderLayout.SOUTH);
	}
}
