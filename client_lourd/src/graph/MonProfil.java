package graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Profil de l'utilisateur de l'application.
 */
public class MonProfil extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8305703908523268258L;

	/**
     * Homme.
     */
    public static final int HOMME = 0;
    
    /**
     * Femme.
     */
    public static final int FEMME = 1;

    /**
     * Nom du compte.
     */
    public final String NOM;
    
    // Email
    private String email;
    
    // Sexe
    private int sexe;
    
    // Description
    private String description;
    
    /**
     * Date d'inscription.
     */
    public final String DATE_INSC;
    
    // Labels et champs de saisies
    private JLabel labelUtilisateur = new JLabel("Utilisateur:  ");
    private JLabel labelNouveauMotDePasse  = new JLabel("Nouveau mot de passe:  ");
    private JLabel labelReNouveauMotDePasse  = new JLabel("ReNouveau mot de passe:  ");
    private JLabel labelMail  = new JLabel("E-mail:  ");
    private JLabel labelDate  = new JLabel("Date d'inscription:  ");
    private JLabel labelDescription  = new JLabel("Description:  ");

    private JTextField jtfUtilisateur = new JTextField();
    private JTextField jtfMail = new JTextField();    
    private JTextField jtfDate = new JTextField();
    private JTextField jtfDescription = new JTextField();
    private JPasswordField jtfNouveauMotDePasse  = new JPasswordField();
    private JPasswordField jtfReNouveauMotDePasse  = new JPasswordField();

    // Boutons et radios
    private ButtonGroup boutonSexe = new ButtonGroup();
    private JRadioButton bHomme = new JRadioButton("Homme");
    private JRadioButton bFemme = new JRadioButton("Femme");
    
    // Sous-conteneur
    private Box box = new Box(BoxLayout.Y_AXIS);

    // Bouton de validation des changements et Reset
    private JButton boutonEnvoyer = new JButton ("Envoyer");

    /**
     * Constructeur d'un profil
     * @param nom Nom de l'utilisateur de l'app
     * @param email Email de l'utilisateur
     * @param description Description personnelle
     * @param date_insc Date d'inscription du membre
     * @param sexe Sexe de l'utilisateur
     */
    public MonProfil(String nom, String email, String description, String date_insc, int sexe)
    {
        this.NOM = nom;
        this.email = email;
        this.sexe = sexe;
       
        this.DATE_INSC = date_insc;
        this.description = description;
        
        // Conteneur
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(new MonProfil.Infos(nom, email, sexe));
        box.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.add("Center", box);      
    }
    
    /**
     * Mail utilisateur
     * @return Le mail du membre
     */
    public String getMail()
    {
        return this.email;
    }
    
    /**
     * Sexe du profil
     * @return Le sexe du membre
     */
    public int getSexe()
    {
        return this.sexe;
    }
    
    /**
     * Description du profil
     * @return La description utilisateur
     */
    public String getDescription()
    {
        return this.description;
    }
    
    public class Infos extends JPanel
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 5015368444659113730L;
		// Largeur et Hauteur du conteneur
        private final int LARGEUR = 440;
        private final int HAUTEUR = 200;
        
        public Infos(String nom, String mail, int sexe)
        {
            Font police = new Font("Arial", Font.PLAIN, 12);

            // Définition des fonts des champs
            jtfUtilisateur.setFont(police);
            jtfMail.setFont(police);
            jtfNouveauMotDePasse.setFont(police);
            jtfReNouveauMotDePasse.setFont(police);
            jtfDate.setFont(police);
            jtfDescription.setFont(police);

            // Définition des tailles des champs
            jtfUtilisateur.setPreferredSize(new Dimension(150, 20));
            jtfMail.setPreferredSize(new Dimension(150, 20));
            jtfNouveauMotDePasse.setPreferredSize(new Dimension(150, 20));
            jtfReNouveauMotDePasse.setPreferredSize(new Dimension(150, 20));
            jtfDate.setPreferredSize(new Dimension(150, 20));
            jtfDescription.setPreferredSize(new Dimension(150, 80));

            // Bouton sexe 
            boutonSexe.add(bHomme);
            boutonSexe.add(bFemme);

            // Valeurs définies
            jtfUtilisateur.setText(nom);
            jtfUtilisateur.setEditable(false);
            jtfMail.setText(mail);
            jtfNouveauMotDePasse.setText("");
            jtfReNouveauMotDePasse.setText("");
            jtfDate.setText(DATE_INSC);
            jtfDate.setEditable(false);
            jtfDescription.setText(description);
            
            if(sexe == MonProfil.HOMME)
                boutonSexe.setSelected(bHomme.getModel(), true);
            else
                boutonSexe.setSelected(bFemme.getModel(), true);

            // Action bouton
            boutonEnvoyer.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Mauvaise correspondance de mots de passe
                    if(!Arrays.toString(jtfNouveauMotDePasse.getPassword()).equals(
                       Arrays.toString(jtfReNouveauMotDePasse.getPassword())))
                    {
                        String message = "Les mots de passe ne correspondent pas !";
                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    // Adresse email invalide
                    else if(!Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$", jtfMail.getText()))
                    {
                        String message = "Adresse mail invalide !";
                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                    // Communication serveur
                    else
                    {
                        // Maj locale
                        MonProfil.this.description = jtfDescription.getText();
                        MonProfil.this.sexe = boutonSexe.isSelected(bHomme.getModel()) ? HOMME : FEMME;
                        MonProfil.this.email = jtfMail.getText();
                        
                        // #PASFAIT
                        
                    }
                }
            });
            
            // Conteneur
            this.setLayout(new GridLayout(8, 2));

            this.add(labelUtilisateur);
            this.add(jtfUtilisateur);
            this.add(labelNouveauMotDePasse);
            this.add(jtfNouveauMotDePasse);
            this.add(labelReNouveauMotDePasse);
            this.add(jtfReNouveauMotDePasse);
            this.add(labelMail);
            this.add(jtfMail);
            this.add(labelDescription);
            this.add(jtfDescription);
            this.add(labelDate);
            this.add(jtfDate);
            this.add(bHomme);
            this.add(bFemme);
            this.add(new JLabel());
            this.add(boutonEnvoyer);
            
            this.setBorder(BorderFactory.createTitledBorder("Modifier mon profil"));
        }
        
        @Override
        public Dimension getMinimumSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }

        @Override
        public Dimension getMaximumSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }

        @Override
        public Dimension getPreferredSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }
    }
}
