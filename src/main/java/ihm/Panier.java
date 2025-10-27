package ihm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

import modèle.ElementPanier;
import modèle.Tomate;
import modèle.PanierService;

public class Panier extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel subTtlLbl;
	private ListeTomates fenPrecedente;
	private DefaultTableModel tableModel;
	private PanierService panierService;

	public Panier(ListeTomates previousWindow) {
		this.fenPrecedente = previousWindow;
		this.panierService = new PanierService();
		this.panierService.getPanier().addAll(ListeTomates.panier);
		initialize();
		setVisible(true);
	}

	private void initialize() {
		setTitle("Ô'Tomates");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		setLocationRelativeTo(null);

		contentPane = new JPanel(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		initHeaderPanel();
		initTable();
		initFooterPanel();

		refreshTable();
	}

	private void initHeaderPanel() {
		JPanel panelHaut = new JPanel(new BorderLayout());
		panelHaut.setBorder(new EmptyBorder(10, 10, 10, 10));

		ImageIcon panierIcon = new ImageIcon(getClass().getResource("/images/tomaticon.png"));
		Image scaledPanier = panierIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		JLabel title = new JLabel("Votre panier", new ImageIcon(scaledPanier), JLabel.LEFT);
		title.setFont(new Font("Poor Richard", Font.BOLD, 22));
		title.setIconTextGap(10);
		panelHaut.add(title, BorderLayout.WEST);

		JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton validBtn = new JButton("Valider");
		validBtn.setBackground(new Color(0, 153, 0));
		validBtn.setForeground(Color.WHITE);
		validBtn.addActionListener(e -> {
			new FicheClient(Panier.this);
			ListeTomates.panier = panierService.getPanier();
			dispose();
		});

		JButton viderBtn = new JButton("Vider");
		viderBtn.setBackground(new Color(204, 0, 0));
		viderBtn.setForeground(Color.WHITE);
		viderBtn.addActionListener(e -> {
			int result = JOptionPane.showOptionDialog(
					Panier.this,
					"Voulez-vous vraiment vider le panier ?",
					"Confirmation",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					new Object[]{"Oui", "Non"},
					"Non"
			);
			if (result == JOptionPane.YES_OPTION) {
				panierService.vider();
				ListeTomates.panier = panierService.getPanier();
				refreshTable();
				mettreAJourSousTotal();
			}
		});

		topRight.add(validBtn);
		topRight.add(viderBtn);
		panelHaut.add(topRight, BorderLayout.EAST);
		contentPane.add(panelHaut, BorderLayout.NORTH);
	}

	private void initTable() {
		String[] columns = {"Image", "Produit", "Prix", "Quantité", "Total"};
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) return ImageIcon.class;
				if (columnIndex == 3) return Integer.class;
				return String.class;
			}
		};

		tableModel.addTableModelListener(e -> {
			if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
				int row = e.getFirstRow();
				if (row >= 0 && row < panierService.getPanier().size()) {
					int nvlleQt = (Integer) tableModel.getValueAt(row, 3);
					panierService.setQuantite(row, nvlleQt);
					int actualQty = panierService.getPanier().get(row).getQuantite();
					tableModel.setValueAt(actualQty, row, 3);
					double total = panierService.getPanier().get(row).getTomate().getPrixTTC() * actualQty;
					tableModel.setValueAt(String.format("%.2f €", total), row, 4);
				}
			}
		});

		table = new JTable(tableModel);
		DefaultTableCellRenderer centreRendre = new DefaultTableCellRenderer();
		centreRendre.setHorizontalAlignment(SwingConstants.CENTER);

		table.getColumnModel().getColumn(2).setCellRenderer(centreRendre);
		table.getColumnModel().getColumn(3).setCellRenderer(centreRendre);
		table.getColumnModel().getColumn(4).setCellRenderer(centreRendre);

		table.setRowHeight(45);
		table.getColumnModel().getColumn(3).setCellEditor(new SpinnerEditor());

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	private void initFooterPanel() {
		JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER));

		subTtlLbl = new JLabel("Sous-total : 0.00 €");

		JButton recalcBtn = new JButton("Recalculer");
		recalcBtn.addActionListener(e -> {
			mettreAJourQtte();
			mettreAJourSousTotal();
		});

		JButton cntnueBtn = new JButton("Continuer les achats");
		cntnueBtn.addActionListener(e -> {
			ListeTomates.panier = panierService.getPanier();
			dispose();
			if (fenPrecedente != null) {
				fenPrecedente.getFrame().setVisible(true);
				fenPrecedente.mettreAJourPrixPanier();
			}
		});

		panelBas.add(subTtlLbl);
		panelBas.add(recalcBtn);
		panelBas.add(cntnueBtn);
		contentPane.add(panelBas, BorderLayout.SOUTH);
	}

	private void refreshTable() {
		tableModel.setRowCount(0);

		for (ElementPanier item : panierService.getPanier()) {
			Tomate t = item.getTomate();
			int qte = item.getQuantite();
			double prix = t.getPrixTTC();
			double total = prix * qte;

			ImageIcon icon;
			try {
				icon = new ImageIcon(getClass().getResource("/images/Tomates40x40/" + t.getNomImage() + ".jpg"));
				Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				icon = new ImageIcon(img);
			} catch (Exception e) {
				icon = new ImageIcon();
			}

			tableModel.addRow(new Object[]{
					icon,
					t.getDésignation(),
					String.format("%.2f €", prix),
					qte,
					String.format("%.2f €", total)
			});
		}
		mettreAJourSousTotal();
	}

	private void mettreAJourQtte() {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			int newQty = (Integer) tableModel.getValueAt(i, 3);
			panierService.setQuantite(i, newQty);
		}
		refreshTable();
	}

	private void mettreAJourSousTotal() {
		double total = panierService.calculerSousTotal();
		subTtlLbl.setText(String.format("Sous-total : %.2f €", total));
	}

	class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
		private final JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			int currentQty = panierService.getPanier().get(row).getQuantite();
			Tomate t = panierService.getPanier().get(row).getTomate();
			int max = Math.max(1, currentQty + t.getStock());
			spinner.setModel(new SpinnerNumberModel(currentQty, 1, max, 1));
			spinner.setValue(value);
			return spinner;
		}

		@Override
		public Object getCellEditorValue() {
			return spinner.getValue();
		}
	}
}
