package ihm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modèle.ElementPanier;
import modèle.FactureService;

public class Facture {

	private JFrame frame;
	private JLabel lblNom, lblAdresse, lblTel, lblMail;
	private JLabel lblDateHeure, lblTtlCommande, lblFraisPort, lblTotalTTC;
	private JTable factureTable;
	private JPanel factureContent, headerPanel, panelClient, btnPanel;
	private JScrollPane scrollPane;
	private JButton btnImprimer, btnQuitter;
	private FactureService factureService;

	public Facture() {
		this.factureService = new FactureService();
		this.factureService.setPanier(ihm.ListeTomates.panier);
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("Ô'Tomates");
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		initHeader();
		initFactureContent();
		initButtons();
	}

	private void initHeader() {
		headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		ImageIcon icon = new ImageIcon(getClass().getResource("/images/factureicon.png"));
		Image scaled = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel lblIcon = new JLabel(new ImageIcon(scaled));

		JLabel lblTitle = new JLabel("Votre facture");
		lblTitle.setFont(new Font("Poor Richard", Font.BOLD, 24));
		lblTitle.setForeground(new Color(0, 128, 0));

		headerPanel.add(lblIcon);
		headerPanel.add(lblTitle);
		frame.add(headerPanel, BorderLayout.NORTH);
	}

	private void initFactureContent() {
		factureContent = new JPanel();
		factureContent.setLayout(new BoxLayout(factureContent, BoxLayout.Y_AXIS));
		factureContent.setBorder(new EmptyBorder(10, 20, 10, 20));

		JLabel lblThanks = new JLabel("Merci de votre visite !");
		lblThanks.setFont(new Font("Arial", Font.PLAIN, 16));
		lblThanks.setForeground(new Color(0, 128, 0));
		factureContent.add(lblThanks);

		JLabel lblSub = new JLabel("Ô'Tomates, redécouvrez le goût des vraies tomates !");
		lblSub.setFont(new Font("Arial", Font.ITALIC, 14));
		factureContent.add(lblSub);

		lblDateHeure = new JLabel("Date : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		lblDateHeure.setFont(new Font("Arial", Font.PLAIN, 13));
		factureContent.add(lblDateHeure);
		factureContent.add(Box.createVerticalStrut(10));

		initClientPanel();
		initTable();
		initTotals();

		scrollPane = new JScrollPane(factureContent);
		frame.add(scrollPane, BorderLayout.CENTER);
	}

	private void initClientPanel() {
		panelClient = new JPanel();
		panelClient.setLayout(new BoxLayout(panelClient, BoxLayout.Y_AXIS));
		panelClient.setBorder(BorderFactory.createTitledBorder("Informations client"));

		lblNom = new JLabel("Nom : ");
		lblAdresse = new JLabel("Adresse : ");
		lblTel = new JLabel("Téléphone : ");
		lblMail = new JLabel("Email : ");

		panelClient.add(lblNom);
		panelClient.add(lblAdresse);
		panelClient.add(lblTel);
		panelClient.add(lblMail);

		factureContent.add(panelClient);
		factureContent.add(Box.createVerticalStrut(10));
	}

	private void initTable() {
		String[] columns = { "Produit", "Quantité", "Prix Unitaire", "Total" };
		List<ElementPanier> panier = factureService.getPanier();
		Object[][] data = new Object[panier.size()][4];

		for (int i = 0; i < panier.size(); i++) {
			ElementPanier item = panier.get(i);
			data[i][0] = item.getTomate().getDésignation();
			data[i][1] = item.getQuantite();
			data[i][2] = String.format("%.2f €", item.getTomate().getPrixTTC());
			data[i][3] = String.format("%.2f €", item.getQuantite() * item.getTomate().getPrixTTC());
		}

		factureTable = new JTable(data, columns);
		factureTable.setFillsViewportHeight(true);
		factureTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < factureTable.getColumnCount(); i++) {
			factureTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane tableScroll = new JScrollPane(factureTable);
		tableScroll.setPreferredSize(new Dimension(600, 120));
		factureContent.add(tableScroll);
		factureContent.add(Box.createVerticalStrut(10));
	}

	private void initTotals() {
		double ttlCommande = factureService.getTotalCommande();
		double fraisPort = factureService.getFraisPort();
		double totalTTC = factureService.getTotalTTC();

		lblTtlCommande = new JLabel("TOTAL TTC COMMANDE :    " + String.format("%.2f €", ttlCommande));
		lblFraisPort = new JLabel("FORFAIT FRAIS DE PORT :    " + String.format("%.2f €", fraisPort));
		lblTotalTTC = new JLabel("PRIX TOTAL TTC :    " + String.format("%.2f €", totalTTC));

		lblTtlCommande.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFraisPort.setFont(new Font("Arial", Font.PLAIN, 13));
		lblTotalTTC.setFont(new Font("Arial", Font.BOLD, 14));

		factureContent.add(lblTtlCommande);
		factureContent.add(lblFraisPort);
		factureContent.add(lblTotalTTC);
	}

	private void initButtons() {
		btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		btnImprimer = new JButton("Imprimer");
		btnImprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Imprimant();
				frame.dispose();
			}
		});

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		btnPanel.add(btnImprimer);
		btnPanel.add(btnQuitter);
		frame.add(btnPanel, BorderLayout.SOUTH);
	}

	public void setClientInfo(String nom, String adresse, String tel, String email) {
		lblNom.setText("Nom : " + nom);
		lblAdresse.setText("Adresse : " + adresse);
		lblTel.setText("Téléphone : " + tel);
		lblMail.setText("Email : " + email);
	}

	public JFrame getFrame() {
		return frame;
	}
}
