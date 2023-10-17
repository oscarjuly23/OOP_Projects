package vista;

import controlador.Controller;

import javax.swing.*;

public class ConfigWindow extends JFrame {

    private static final String SAVE    = "Save";
    private static final String N_MOLES = "Number of Moles: ";
    private static final String BG_SIZE = "Board game size: ";

    private static final int TF_SIZE    = 10;
    private static final int MIN_SIZE   = 200;

    private JTextField  jtfMoles;   //TextField where the user will input the number of moles
    private JTextField  jtfSize;    //TextField where the user will input the desired board size
    private JButton     jbSave;     //Button to save all the info introduced and start playing

    public ConfigWindow() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //First section: Number of moles
        JPanel jpMole = new JPanel();           //We create a panel in order to have the label and the input together

        JLabel jlMoles = new JLabel(N_MOLES);   //Create the label with its name
        jpMole.add(jlMoles);

        this.jtfMoles = new JTextField(TF_SIZE);//Create the text input
        jpMole.add(jtfMoles);

        getContentPane().add(jpMole);           //We add the panel to the view


        //Second section: Board Game size
        JPanel jpSize = new JPanel();           //We create a panel in order to have the label and the input together

        JLabel jlSize = new JLabel(BG_SIZE);    //Create the label with its name
        jpSize.add(jlSize);

        this.jtfSize = new JTextField(TF_SIZE); //Create the text input
        jpSize.add(jtfSize);

        getContentPane().add(jpSize);           //We add the panel to the view


        //Third section: Save button
        this.jbSave = new JButton(SAVE);
        getContentPane().add(jbSave);


        setSize(MIN_SIZE, MIN_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("AC5 Config");
    }

    public void registerController(Controller controller){
        this.jbSave.addMouseListener(controller);
    }

    public String getJtfMolesNumber(){
        return jtfMoles.getText();
    }

    public String getJtfBoardSize(){
        return jtfSize.getText();
    }
}
