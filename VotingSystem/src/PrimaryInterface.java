import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryInterface extends JFrame {
    PrimaryInterface(){
        super("National Voting System");
        setLayout(new GridLayout(8,1,10,20));


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

                        CreateBallot bGui = new CreateBallot(countyAField.getText(),stateAField.getText());
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

        JPanel auditorLoginPanel = new JPanel();
        auditorLoginPanel.setLayout(new GridLayout(1,8));
        auditorLoginPanel.add(auditorLogin);
        auditorLoginPanel.add(pswrd);
        auditorLoginPanel.add(pswrField);
        auditorLoginPanel.add(countyA);
        auditorLoginPanel.add(countyAField);
        auditorLoginPanel.add(stateA);
        auditorLoginPanel.add(stateAField);
        auditorLoginPanel.add(loginA);


        JPanel electionResultsPanel = new JPanel();
        electionResultsPanel.setLayout(new GridLayout(1,8));
        for(int i = 0;i<7;i++){
            electionResultsPanel.add(new JPanel());

        }
        electionResultsPanel.add(electionResults);


        JPanel voterLoginPanel = new JPanel();
        voterLoginPanel.setLayout(new GridLayout(1,8));
        voterLoginPanel.add(voterLogin);
        voterLoginPanel.add(name);
        voterLoginPanel.add(nameField);
        voterLoginPanel.add(countyV);
        voterLoginPanel.add(countyVField);
        voterLoginPanel.add(stateV);
        voterLoginPanel.add(stateVField);
        voterLoginPanel.add(vLogin);


        JPanel voterRegistrationPanel = new JPanel();
        voterRegistrationPanel.setLayout(new GridLayout(1,8));
        for(int i = 0;i<7;i++){
            voterRegistrationPanel.add(new JPanel());
        }
        voterRegistrationPanel.add(vReg);


        add(auditorLoginPanel);
        add(electionResultsPanel);
        add(voterLoginPanel);
        add(voterRegistrationPanel);
    }
}
