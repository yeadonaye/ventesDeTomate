
package ihm;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Imprimant {

    private JFrame frame;
    private JLabel lblNewLabel;
    private JTabbedPane tabbedPane;
    private JPanel generatePanel, gridHaut, servPanel, gridMilieu;
    private JComboBox<String> comboBox;
    private JCheckBox printToFile;
    private JButton propertiesButton;
    private JPanel rangePanel, cpPnl, rangePage;
    private JRadioButton allBtn, pageBtn;
    private ButtonGroup rangeGroup;
    private JLabel nbCopiesLbl;
    private JSpinner cpSpinner, debut, fin;
    private JCheckBox collateBox;
    private JPanel apparencePanel, appMainPanel, panelClr;
    private JLabel lblQualite, lblColorMode, lblSpacer;
    private JComboBox<String> qltComBox;
    private JRadioButton clrRdioBtn, bwRdioBtn;
    private JCheckBox boiteDplex, boiteCmmnt, boiteArrierPlan;
    private JPanel pnlLayout, formatPapierPnl, panelHautLay, pnlChoix, layoutMarginsContainer, marginsTitlePanel, marginsGridPanel, marginLeftPanel, marginRightPanel;
    private JLabel lblPaperFormat, titreLayLbl, choixAffichLbl, lblBlank;
    private JComboBox<String> formatPapierComBox;
    private JRadioButton portraitRadio, landscapeRadio;
    private JLabel lblMargins, lblRight, lblTop, lblLeft, lblBottom;
    private JSpinner spinRight, spinTop, spinLeft, spinBottom;
    private JPanel panelBas;
    private JButton imprimeBtn, annulBtn;

    public Imprimant() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Imprimante");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        lblNewLabel = new JLabel("Imprimer");
        lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.ITALIC, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        generatePanel = new JPanel(new BorderLayout());
        gridHaut = new JPanel();
        gridHaut.setLayout(new BoxLayout(gridHaut, BoxLayout.Y_AXIS));

        servPanel = new JPanel();
        servPanel.setLayout(new BoxLayout(servPanel, BoxLayout.Y_AXIS));
        servPanel.setBorder(BorderFactory.createTitledBorder("Service d'impression"));

        JPanel rowNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nomLbl = new JLabel("Nom :");
        comboBox = new JComboBox<>(new String[] {
            "HP DeskJet 2720e", "HP OfficeJet Pro 9020e", "HP LaserJet Pro M404dn"
        });
        propertiesButton = new JButton("Propriétés");
        rowNom.add(nomLbl);
        rowNom.add(comboBox);
        rowNom.add(propertiesButton);
        servPanel.add(rowNom);

        JPanel rowStatut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statutLbl = new JLabel("Statut : Acceptation des tâches");
        rowStatut.add(statutLbl);
        servPanel.add(rowStatut);

        JPanel rowType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLbl = new JLabel("Type : Imprimantes grand format");
        rowType.add(typeLbl);
        servPanel.add(rowType);

        JPanel rowInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        printToFile = new JCheckBox("Imprimer dans un fichier");
        rowInfo.add(printToFile);
        servPanel.add(rowInfo);

        gridHaut.add(servPanel);

        gridMilieu = new JPanel(new GridLayout(1, 2, 10, 0));

        rangePanel = new JPanel();
        rangePanel.setLayout(new BoxLayout(rangePanel, BoxLayout.Y_AXIS));
        rangePanel.setBorder(BorderFactory.createTitledBorder("Plage d'impression"));

        allBtn = new JRadioButton("Tout", true);
        pageBtn = new JRadioButton("Pages");
        rangeGroup = new ButtonGroup();
        rangeGroup.add(allBtn);
        rangeGroup.add(pageBtn);

        rangePanel.add(allBtn);
        rangePanel.add(pageBtn);

        rangePage = new JPanel(new FlowLayout(FlowLayout.LEFT));
        debut = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        fin = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        rangePage.add(debut);
        rangePage.add(new JLabel("à"));
        rangePage.add(fin);
        rangePanel.add(rangePage);

        gridMilieu.add(rangePanel);

        cpPnl = new JPanel();
        cpPnl.setLayout(new BoxLayout(cpPnl, BoxLayout.Y_AXIS));
        cpPnl.setBorder(BorderFactory.createTitledBorder("Copie"));

        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));  // horizontal
        JLabel copiesLabel = new JLabel("Nombre de copies :");

        cpSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        cpSpinner.setPreferredSize(new Dimension(60, 25)); // smaller size

        linePanel.add(copiesLabel);
        JPanel cpWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cpSpinner.setPreferredSize(new Dimension(60, 25));
        cpWrap.add(cpSpinner);
        linePanel.add(cpWrap);


        collateBox = new JCheckBox("Collationner", true);

        cpPnl.add(linePanel);
        cpPnl.add(collateBox);

        gridMilieu.add(cpPnl);

        gridHaut.add(gridMilieu);
        generatePanel.add(gridHaut, BorderLayout.CENTER);

        tabbedPane.addTab("Générer", generatePanel);

        apparencePanel = new JPanel(new BorderLayout());
        appMainPanel = new JPanel();
        appMainPanel.setLayout(new BoxLayout(appMainPanel, BoxLayout.Y_AXIS));
        appMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblQualite = new JLabel("Qualité d'impression :");
        qltComBox = new JComboBox<>(new String[] {"Brouillon", "Normal", "Optimal", "Meilleur"});
        lblQualite.setAlignmentX(Component.LEFT_ALIGNMENT);
        qltComBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        appMainPanel.add(lblQualite);
        appMainPanel.add(Box.createVerticalStrut(5));
        appMainPanel.add(qltComBox);
        appMainPanel.add(Box.createVerticalStrut(10));

        panelClr = new JPanel(new GridLayout(2, 2, 5, 5));
        lblColorMode = new JLabel("Mode de couleur :");
        lblSpacer = new JLabel("");
        clrRdioBtn = new JRadioButton("Couleur");
        bwRdioBtn = new JRadioButton("Black or White");
        panelClr.add(lblColorMode);
        panelClr.add(lblSpacer);
        panelClr.add(clrRdioBtn);
        panelClr.add(bwRdioBtn);
        appMainPanel.add(panelClr);
        appMainPanel.add(Box.createVerticalStrut(10));

        boiteDplex = new JCheckBox("Recto/Verso");
        boiteCmmnt = new JCheckBox("Imprimer les commentaires");
        boiteArrierPlan = new JCheckBox("Imprimer les arrières plans");

        appMainPanel.add(boiteDplex);
        appMainPanel.add(boiteCmmnt);
        appMainPanel.add(boiteArrierPlan);

        apparencePanel.add(appMainPanel, BorderLayout.NORTH);
        tabbedPane.addTab("Apparence", apparencePanel);

        // === Mise en page Tab ===
        pnlLayout = new JPanel(new BorderLayout());

        formatPapierPnl = new JPanel();
        formatPapierPnl.setLayout(new BoxLayout(formatPapierPnl, BoxLayout.Y_AXIS));
        
        lblPaperFormat = new JLabel("Format du papier");
        
        formatPapierComBox = new JComboBox<>(new String[] {"A4", "A2", "A3"});
        formatPapierComBox.setPreferredSize(new Dimension(60, 25));
        
        formatPapierPnl.add(lblPaperFormat);
        JPanel comboWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        formatPapierComBox.setPreferredSize(new Dimension(60, 25));
        comboWrap.add(formatPapierComBox);
        formatPapierPnl.add(comboWrap);
        pnlLayout.add(formatPapierPnl, BorderLayout.WEST);

        panelHautLay = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titreLayLbl = new JLabel("FAITES VOTRE MISE EN PAGE");
        panelHautLay.add(titreLayLbl);
        pnlLayout.add(panelHautLay, BorderLayout.NORTH);

        pnlChoix = new JPanel(new GridLayout(2, 2, 5, 5));
        choixAffichLbl = new JLabel("Options d'affichage");
        lblBlank = new JLabel("");
        portraitRadio = new JRadioButton("Portrait");
        landscapeRadio = new JRadioButton("Paysage");
        pnlChoix.add(choixAffichLbl);
        pnlChoix.add(lblBlank);
        pnlChoix.add(portraitRadio);
        pnlChoix.add(landscapeRadio);
        pnlLayout.add(pnlChoix, BorderLayout.EAST);

        layoutMarginsContainer = new JPanel(new GridLayout(2, 1, 0, 5));
        marginsTitlePanel = new JPanel(new BorderLayout());
        lblMargins = new JLabel("MARGES (en mm) :", SwingConstants.CENTER);
        lblMargins.setFont(new Font("SimSun", Font.PLAIN, 20));
        marginsTitlePanel.add(lblMargins, BorderLayout.CENTER);
        marginsGridPanel = new JPanel(new GridLayout(1, 2, 10, 5));

        marginLeftPanel = new JPanel(new GridLayout(2, 1));
        
        lblRight = new JLabel("Droite");
        
        spinRight = new JSpinner();
        JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinRight.setPreferredSize(new Dimension(60, 25));
        rightWrap.add(spinRight);
        
        lblTop = new JLabel("Haut");
        spinTop = new JSpinner();
        JPanel topWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinTop.setPreferredSize(new Dimension(60, 25));
        topWrap.add(spinTop);

       
        marginLeftPanel.add(lblRight); marginLeftPanel.add(rightWrap);
        marginLeftPanel.add(lblTop); marginLeftPanel.add(topWrap);

        marginRightPanel = new JPanel(new GridLayout(2, 1));
        lblLeft = new JLabel("Gauche");
        spinLeft = new JSpinner();
        JPanel leftWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinLeft.setPreferredSize(new Dimension(60, 25));
        leftWrap.add(spinLeft);

        lblBottom = new JLabel("Bas");
        spinBottom = new JSpinner();
        JPanel bottomWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinBottom.setPreferredSize(new Dimension(60, 25));
        bottomWrap.add(spinBottom);
        
        marginRightPanel.add(lblLeft); marginRightPanel.add(leftWrap);
        marginRightPanel.add(lblBottom); marginRightPanel.add(bottomWrap);


        marginsGridPanel.add(marginLeftPanel);
        marginsGridPanel.add(marginRightPanel);

        layoutMarginsContainer.add(marginsTitlePanel);
        layoutMarginsContainer.add(marginsGridPanel);
        pnlLayout.add(layoutMarginsContainer, BorderLayout.CENTER);

        tabbedPane.addTab("Mise en page", pnlLayout);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        imprimeBtn = new JButton("Imprimer");
        imprimeBtn.setBackground(Color.GREEN);
        imprimeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Impression en cours...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        annulBtn = new JButton("Annuler");
        annulBtn.setBackground(Color.RED);
        annulBtn.setForeground(Color.WHITE);
        annulBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panelBas.add(imprimeBtn);
        panelBas.add(annulBtn);
        frame.getContentPane().add(panelBas, BorderLayout.SOUTH);
    }
}
