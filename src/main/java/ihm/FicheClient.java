package ihm;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class FicheClient {

	private JFrame frame;
	private JTextField tfNom, tfPrenom, tfAdresse1, tfAdresse2, tfCodePostal, tfVille, tfTelephone, tfMail;
	private JRadioButton rbCarteCredite, rbPaypal, rbCheque;
	private JRadioButton rbNewsletterOui, rbNewsletterNon;
	private JButton btnOk, btnAnnuler;
	private Panier fenPrecedent;

	public FicheClient(Panier previousWindow) {
		this.fenPrecedent = previousWindow;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("Ô'Tomates");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		Color lightGreen = new Color(232, 255, 232);

		initHeaderPanel(lightGreen);
		initCenterPanel(lightGreen);
		initFooterPanel();
	}

	private void initHeaderPanel(Color lightGreen) {
		JPanel panelNord = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelNord.setBackground(lightGreen);

		JLabel titreLabel = new JLabel("Vos coordonnées");
		titreLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titreLabel.setForeground(new Color(0, 128, 0));

		ImageIcon icon = new ImageIcon(getClass().getResource("/images/icontomforficheclient.png"));
		Image scaled = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel iconLabel = new JLabel(new ImageIcon(scaled));

		panelNord.add(titreLabel);
		panelNord.add(iconLabel);

		frame.add(panelNord, BorderLayout.NORTH);
	}

	private void initCenterPanel(Color lightGreen) {
		JPanel centreCont = new JPanel();
		centreCont.setLayout(new BoxLayout(centreCont, BoxLayout.Y_AXIS));
		centreCont.setBackground(lightGreen);

		JPanel formPanel = new JPanel(new GridLayout(8, 2, 8, 4));
		formPanel.setBackground(lightGreen);
		formPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

		tfNom = new JTextField();
		tfPrenom = new JTextField();
		tfAdresse1 = new JTextField();
		tfAdresse2 = new JTextField();
		tfCodePostal = new JTextField();
		tfVille = new JTextField();
		tfTelephone = new JTextField();
		tfMail = new JTextField();

		formPanel.add(new JLabel("Nom :"));        formPanel.add(tfNom);
		formPanel.add(new JLabel("Prénom :"));     formPanel.add(tfPrenom);
		formPanel.add(new JLabel("Adresse 1 :"));  formPanel.add(tfAdresse1);
		formPanel.add(new JLabel("Adresse 2 :"));  formPanel.add(tfAdresse2);
		formPanel.add(new JLabel("Code postal :"));formPanel.add(tfCodePostal);
		formPanel.add(new JLabel("Ville :"));      formPanel.add(tfVille);
		formPanel.add(new JLabel("Téléphone :"));  formPanel.add(tfTelephone);
		formPanel.add(new JLabel("Mail :"));       formPanel.add(tfMail);

		JPanel paiementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		paiementPanel.setBackground(lightGreen);
		paiementPanel.setBorder(BorderFactory.createTitledBorder("Moyen de paiement"));

		rbCarteCredite = new JRadioButton("Carte de crédit");
		rbPaypal = new JRadioButton("Paypal");
		rbCheque = new JRadioButton("Chèque");

		ButtonGroup paymentGroup = new ButtonGroup();
		paymentGroup.add(rbCarteCredite);
		paymentGroup.add(rbPaypal);
		paymentGroup.add(rbCheque);

		rbCarteCredite.setBackground(lightGreen);
		rbPaypal.setBackground(lightGreen);
		rbCheque.setBackground(lightGreen);

		paiementPanel.add(rbCarteCredite);
		paiementPanel.add(rbPaypal);
		paiementPanel.add(rbCheque);

		JPanel newsletterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		newsletterPanel.setBackground(lightGreen);
		newsletterPanel.setBorder(BorderFactory.createTitledBorder("Abonnement à notre Newsletter"));

		rbNewsletterOui = new JRadioButton("Oui");
		rbNewsletterNon = new JRadioButton("Non");

		ButtonGroup newsletterGroup = new ButtonGroup();
		newsletterGroup.add(rbNewsletterOui);
		newsletterGroup.add(rbNewsletterNon);

		rbNewsletterOui.setBackground(lightGreen);
		rbNewsletterNon.setBackground(lightGreen);

		newsletterPanel.add(rbNewsletterOui);
		newsletterPanel.add(rbNewsletterNon);

		centreCont.add(formPanel);
		centreCont.add(paiementPanel);
		centreCont.add(newsletterPanel);

		frame.add(centreCont, BorderLayout.CENTER);
	}

	private void initFooterPanel() {
		JPanel panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		btnOk = new JButton("OK");
		btnOk.setBackground(Color.GREEN);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = tfNom.getText();
				String prenom = tfPrenom.getText();
				String adresse1 = tfAdresse1.getText();
				String adresse2 = tfAdresse2.getText();
				String codePostal = tfCodePostal.getText();
				String ville = tfVille.getText();
				String telephone = tfTelephone.getText();
				String mail = tfMail.getText();

				String adresseComplete = adresse1 + " " + adresse2 + ", " + codePostal + " " + ville;

				JOptionPane.showMessageDialog(frame, "Données enregistrées !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

				Facture facture = new Facture();
				facture.setClientInfo(nom + " " + prenom, adresseComplete, telephone, mail);
				frame.dispose();
			}
		});

		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBackground(Color.RED);
		btnAnnuler.setForeground(Color.WHITE);
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				if (fenPrecedent != null) {
					fenPrecedent.setVisible(true);
				}
			}
		});

		panelSud.add(btnOk);
		panelSud.add(btnAnnuler);
		frame.add(panelSud, BorderLayout.SOUTH);
	}

	public JFrame getFrame() {
		return frame;
	}
}
