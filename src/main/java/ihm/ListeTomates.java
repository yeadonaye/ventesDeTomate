package ihm;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import modèle.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ListeTomates {

	private JFrame frame;
	private JList<String> listeTomates;
	private DefaultListModel<String> listeModel;
	private JComboBox<String> comboBoxType;
	private JComboBox<String> comboBoxCouleur;
	private JButton btnPanier;

	private List<Tomate> toutesLesTomates;
	public static List<ElementPanier> panier = new ArrayList<>();

	public ListeTomates() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("Ô'Tomates");
		frame.setBounds(100, 100, 600, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

		ImageIcon titreIcon = new ImageIcon(getClass().getResource("/images/tomatoicon.png"));
		Image scaledTitleImage = titreIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JLabel lblTitre = new JLabel("Nos graines de tomates", new ImageIcon(scaledTitleImage), JLabel.CENTER);
		lblTitre.setHorizontalTextPosition(SwingConstants.LEFT);
		lblTitre.setFont(new Font("Poor Richard", Font.BOLD, 20));
		lblTitre.setForeground(new Color(0, 128, 0));
		headerPanel.add(lblTitre, BorderLayout.CENTER);

		ImageIcon iconPanier = new ImageIcon(getClass().getResource("/images/pngtree-shopping-cart-convenient-icon-image_1287807.jpg"));
		Image scaledCartImage = iconPanier.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		btnPanier = new JButton("0,00 €", new ImageIcon(scaledCartImage));
		btnPanier.setFont(new Font("Arial", Font.PLAIN, 14));
		headerPanel.add(btnPanier, BorderLayout.EAST);

		listeModel = new DefaultListModel<>();
		listeTomates = new JList<>(listeModel);
		listeTomates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(listeTomates);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel filterPanel = new JPanel(new GridLayout(1, 2));
		filterPanel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(new Color(0, 128, 0)),
			"Filtres",
			0,
			0,
			new Font("Arial", Font.BOLD, 12),
			new Color(0, 128, 0)
		));

		comboBoxType = new JComboBox<>();
		comboBoxType.addItem("Toutes les tomates");
		for (TypeTomate t : TypeTomate.values()) {
			comboBoxType.addItem(t.getDénomination());
		}

		comboBoxCouleur = new JComboBox<>();
		comboBoxCouleur.addItem("Toutes les couleurs");
		for (Couleur c : Couleur.values()) {
			comboBoxCouleur.addItem(c.getDénomination());
		}

		JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		typePanel.add(new JLabel(new ImageIcon(
			new ImageIcon(getClass().getResource("/images/tomatohat.jpg"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))));
		typePanel.add(comboBoxType);

		JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		colorPanel.add(new JLabel(new ImageIcon(
			new ImageIcon(getClass().getResource("/images/color-wheel-icon.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))));
		colorPanel.add(comboBoxCouleur);

		filterPanel.add(typePanel);
		filterPanel.add(colorPanel);

		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(filterPanel, BorderLayout.CENTER);

		JButton btnDeco = new JButton(new ImageIcon(
			new ImageIcon(getClass().getResource("/images/planicon.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		btnDeco.setBorderPainted(false);
		btnDeco.setContentAreaFilled(false);
		southPanel.add(btnDeco, BorderLayout.EAST);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

		addListeners();
		loadTomates();
		filtrerTomates();
	}

	private void addListeners() {
		btnPanier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				new Panier(ListeTomates.this);
			}
		});

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrerTomates();
			}
		});

		comboBoxCouleur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrerTomates();
			}
		});

		listeTomates.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String selected = listeTomates.getSelectedValue();
					if (selected != null) {
						for (Tomate t : toutesLesTomates) {
							if (t.getDésignation().equals(selected)) {
								new DetailUnTomate(t, ListeTomates.this);
								frame.dispose();
								break;
							}
						}
					}
				}
			}
		});
	}

	private void loadTomates() {
-		Tomates base = OutilsBaseDonneesTomates.générationBaseDeTomates("src/main/resources/data/tomates.json");
+		String savePath = "src/main/resources/data/tomatesSauvegarde.json";
+		String defaultPath = "src/main/resources/data/tomates.json";
+		String loadPath = Files.exists(Paths.get(savePath)) ? savePath : defaultPath;
+		Tomates base = OutilsBaseDonneesTomates.générationBaseDeTomates(loadPath);
		toutesLesTomates = base.getTomates();
	}

	private void filtrerTomates() {
		String typeSel = (String) comboBoxType.getSelectedItem();
		String couleurSel = (String) comboBoxCouleur.getSelectedItem();

		listeModel.clear();

		Tomates base = new Tomates();
		base.addTomates(toutesLesTomates);

		List<Tomate> filtrées;
		boolean allTypes = typeSel.equals("Toutes les tomates");
		boolean allColors = couleurSel.equals("Toutes les couleurs");

		if (allTypes && allColors) {
			filtrées = toutesLesTomates;
		} else if (!allTypes && allColors) {
			filtrées = base.tomatesDeType(TypeTomate.getTypeTomate(typeSel));
		} else if (allTypes && !allColors) {
			filtrées = base.tomatesDeCouleur(Couleur.getCouleur(couleurSel));
		} else {
			filtrées = base.tomatesDetypeDeCouleur(
				TypeTomate.getTypeTomate(typeSel),
				Couleur.getCouleur(couleurSel)
			);
		}

		for (Tomate t : filtrées) {
			listeModel.addElement(t.getDésignation());
		}
	}

	public void mettreAJourPrixPanier() {
		double total = 0;
		for (ElementPanier item : panier) {
			total += item.getTomate().getPrixTTC() * item.getQuantite();
		}
		btnPanier.setText(String.format("%.2f €", total));
	}

	public JFrame getFrame() {
		return frame;
	}
}
