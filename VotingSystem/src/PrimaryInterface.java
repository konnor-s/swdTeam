import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryInterface extends JFrame {
    PrimaryInterface(){
        super("National Voting System");
        setLayout(new GridLayout(5,1,10,20));


        //Auditor login stuff
        JLabel auditorLogin = new JLabel("County Auditor Login");
        JLabel pswrd = new JLabel("Password");
        JTextField pswrField = new JTextField("");
        JLabel countyA = new JLabel("County");
        JTextField countyAField = new JTextField("");
        JLabel stateA = new JLabel("State");
        JTextField stateAField = new JTextField("");
        JButton loginA = new JButton();
        loginA.setText("Auditor Login");
        loginA.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        CreateBallot bGui = new CreateBallot(countyA.getText(),stateA.getText());
                        // bGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        bGui.setSize(1200,800);
                        bGui.setVisible(true);

                    }
                }
        );

        //voter login stuff
        JLabel voterLogin = new JLabel("Registered Voter Login");
        JLabel name = new JLabel("First and Last name");
        JTextField nameField = new JTextField("");
        JLabel countyV = new JLabel("County");
        JTextField countyVField = new JTextField("");
        JLabel stateV = new JLabel("State");
        JTextField stateVField = new JTextField("");
        JButton vLogin = new JButton();
        vLogin.setText("Voter Login");
        vLogin.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Ballot vGui = new Ballot(countyVField.getText(),stateVField.getText(),true);
                        vGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        vGui.setSize(600,800);
                        vGui.setVisible(true);
                    }
                }
        );

        //voter registration stuff
        JButton vReg = new JButton();
        vReg.setText("Voter Registration");
        vReg.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        VoterRegistration rGui = new VoterRegistration();
                        rGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        rGui.setSize(600,400);
                        rGui.setVisible(true);
                    }
                }
        );

        //election results stuff
        JButton electionResults = new JButton();
        electionResults.setText("Election Results");
        electionResults.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ElectionResults rGui = new ElectionResults();
                        //vGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        rGui.setSize(600,400);
                        rGui.setVisible(true);
                    }
                }
        );

        JPanel auditorLoginPanel1 = new JPanel();
        auditorLoginPanel1.setLayout(new GridLayout(1,5));
        auditorLoginPanel1.add(auditorLogin);
        auditorLoginPanel1.add(pswrd);
        auditorLoginPanel1.add(countyA);
        auditorLoginPanel1.add(stateA);
        auditorLoginPanel1.add(new JPanel());

        JPanel auditorLoginPanel2 = new JPanel();
        auditorLoginPanel2.setLayout(new GridLayout(1,5));
        auditorLoginPanel2.add(new JPanel());
        auditorLoginPanel2.add(pswrField);
        auditorLoginPanel2.add(countyAField);
        auditorLoginPanel2.add(stateAField);
        auditorLoginPanel2.add(loginA);

        JPanel auditorLoginPanel = new JPanel();
        auditorLoginPanel.setLayout(new GridLayout(2,1));
        auditorLoginPanel.add(auditorLoginPanel1);
        auditorLoginPanel.add(auditorLoginPanel2);

        JPanel electionResultsPanel1 = new JPanel();
        electionResultsPanel1.setLayout(new GridLayout(1,5));
        for(int i = 0;i<4;i++){
            electionResultsPanel1.add(new JPanel());

        }
        electionResultsPanel1.add(electionResults);

        JPanel electionResultsPanel = new JPanel();
        electionResultsPanel.setLayout(new GridLayout(2,1));
        electionResultsPanel.add(electionResultsPanel1);
        electionResultsPanel.add(new JPanel());

        JPanel voterLoginPanel1 = new JPanel();
        voterLoginPanel1.setLayout(new GridLayout(1,5));
        voterLoginPanel1.add(voterLogin);
        voterLoginPanel1.add(name);
        voterLoginPanel1.add(countyV);
        voterLoginPanel1.add(stateV);
        voterLoginPanel1.add(new JPanel());

        JPanel voterLoginPanel2 = new JPanel();
        voterLoginPanel2.setLayout(new GridLayout(1,5));
        voterLoginPanel2.add(new JPanel());
        voterLoginPanel2.add(nameField);
        voterLoginPanel2.add(countyVField);
        voterLoginPanel2.add(stateVField);
        voterLoginPanel2.add(vLogin);

        JPanel voterLoginPanel = new JPanel();
        voterLoginPanel.setLayout(new GridLayout(2,1));
        voterLoginPanel.add(voterLoginPanel1);
        voterLoginPanel.add(voterLoginPanel2);

        JPanel voterRegistrationPanel1 = new JPanel();
        voterRegistrationPanel1.setLayout(new GridLayout(1,5));
        for(int i = 0;i<4;i++){
            voterRegistrationPanel1.add(new JPanel());
        }
        voterRegistrationPanel1.add(vReg);

        JPanel voterRegistrationPanel = new JPanel();
        voterRegistrationPanel.setLayout(new GridLayout(2,1));
        voterRegistrationPanel.add(voterRegistrationPanel1);
        voterRegistrationPanel.add(new JPanel());

        add(auditorLoginPanel);
        add(electionResultsPanel);
        add(voterLoginPanel);
        add(voterRegistrationPanel);
    }
}
