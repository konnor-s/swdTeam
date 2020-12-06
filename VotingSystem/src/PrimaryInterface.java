import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;


public class PrimaryInterface extends JFrame {
    PrimaryInterface(){
        super("National Voting System");
        setLayout(new GridLayout(5,1,10,20));


        //Auditor login stuff
        JLabel auditorLogin = new JLabel("County Auditor Login");
        JLabel pswrd = new JLabel("Password");
        JPasswordField pswrField = new JPasswordField();
        JLabel countyA = new JLabel("County");
        JTextField countyAField = new JTextField("");
        JLabel stateA = new JLabel("State");
        JTextField stateAField = new JTextField("");
        JLabel errorA = new JLabel("");
        JButton loginA = new JButton();
        loginA.setText("Auditor Login");
        loginA.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (countyAField.getText().equals("") || stateAField.getText().equals("")) {
                            errorA.setText("All fields must be filled out!");
                        }
                        else{
                            //Password will be CountyState + 123123123... in ASCII. PolkIowa is QqolKrxc and JohnsonIowa is KqkouroKrxc
                            //See PasswordTest class
                            String a = countyAField.getText()+stateAField.getText();
                            byte[] bytes = new byte[0];
                            try {
                                bytes = a.getBytes("US-ASCII");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            byte[] nArray = new byte[]{1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3};
                            byte[] bytes2 = new byte[a.length()];
                            for(int i = 0; i < bytes.length;i++){
                                bytes2[i] = (byte) (bytes[i]+nArray[i]);
                            }
                            String password ="";
                            for(int i: bytes2) {
                                password += Character.toString((char) i);
                            }

                            if(password.equals(pswrField.getText())) {
                                //check if ballot is already finalized
                                try {

                                    Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                                    Statement statement = connection.createStatement();
                                    ResultSet rs = statement.executeQuery("SELECT Finalized FROM Ballot WHERE County = '" + countyAField.getText() + "' AND State = '" + stateAField.getText() + "'");
                                    boolean finalized = true;
                                    finalized = rs.getBoolean(1);
                                    if(!finalized){
                                        CreateBallot bGui = new CreateBallot(countyAField.getText(), stateAField.getText());
                                        bGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                        bGui.setSize(1200, 800);
                                        bGui.setVisible(true);
                                    }
                                    else{
                                        errorA.setText("Ballot already Finalized");
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }



                            }
                            else{
                                errorA.setText("Invalid Password");
                            }
                        }
                    }
                }
        );

        //voter login stuff
        JLabel voterLogin = new JLabel("Registered Voter Login");
        JLabel id = new JLabel("Driver's License ID #");
        JTextField idField = new JTextField();
        JLabel name = new JLabel("First and Last name");
        JTextField nameField = new JTextField();
        JLabel countyV = new JLabel("County");
        JTextField countyVField = new JTextField();
        JLabel stateV = new JLabel("State");
        JTextField stateVField = new JTextField();
        JLabel errorV = new JLabel("");
        JButton vLogin = new JButton();
        vLogin.setText("Voter Login");
        vLogin.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        try {
                            //Connect to server
                            Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                            Statement statement = connection.createStatement();

                            //Get list of license numbers in the registry
                            ResultSet rs = statement.executeQuery("SELECT License FROM VoterRegistry WHERE Name = '" + nameField.getText() +"' AND County = '"+countyVField.getText()+"' AND State = '"+stateVField.getText()+"'");
                            ArrayList ids = new ArrayList<String>();
                            while (rs.next()) {
                                ids.add(rs.getString(1));
                            }
                            boolean exists = false;
                            //Check if this license is already in the database
                            for (Object i : ids) {
                                if (idField.getText().equals(i)) {
                                    exists = true;
                                }
                            }
                           //check if voted already
                            boolean voted = true;
                            if(exists) {
                                ResultSet rs2 = statement.executeQuery("SELECT Voted FROM VoterRegistry WHERE License = '" + idField.getText() + "' AND Name = '" + nameField.getText() + "' AND County = '" + countyVField.getText() + "' AND State = '" + stateVField.getText() + "'");
                                rs2.next();
                                voted = rs2.getBoolean(1);

                            }
                            connection.close();
                            if(!voted){
                                Ballot vGui = new Ballot(countyVField.getText(), stateVField.getText(), true, idField.getText());
                                vGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                vGui.setSize(600, 800);
                                vGui.setVisible(true);
                            }
                            else {
                                if (!exists) {
                                    errorV.setText("Voter not in Registry");
                                } else {
                                    errorV.setText("Vote already Cast!");
                                }
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        //voter registration stuff
        JButton vReg = new JButton();
        vReg.setText("Click Here to Open Registration Window");
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
                        rGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        for(int i = 0;i<3;i++){
            electionResultsPanel1.add(new JPanel());

        }
        electionResultsPanel1.add(errorA);
        electionResultsPanel1.add(electionResults);

        JPanel electionResultsPanel = new JPanel();
        electionResultsPanel.setLayout(new GridLayout(2,1));
        electionResultsPanel.add(electionResultsPanel1);
        electionResultsPanel.add(new JPanel());

        JPanel voterLoginPanel1 = new JPanel();
        voterLoginPanel1.setLayout(new GridLayout(1,6));
        voterLoginPanel1.add(voterLogin);
        voterLoginPanel1.add(id);
        voterLoginPanel1.add(name);
        voterLoginPanel1.add(countyV);
        voterLoginPanel1.add(stateV);
        voterLoginPanel1.add(new JPanel());

        JPanel voterLoginPanel2 = new JPanel();
        voterLoginPanel2.setLayout(new GridLayout(1,6));
        voterLoginPanel2.add(new JPanel());
        voterLoginPanel2.add(idField);
        voterLoginPanel2.add(nameField);
        voterLoginPanel2.add(countyVField);
        voterLoginPanel2.add(stateVField);
        voterLoginPanel2.add(vLogin);

        JPanel voterLoginPanel = new JPanel();
        voterLoginPanel.setLayout(new GridLayout(2,1));
        voterLoginPanel.add(voterLoginPanel1);
        voterLoginPanel.add(voterLoginPanel2);

        JPanel voterRegistrationPanel1 = new JPanel();
        voterRegistrationPanel1.add(vReg);

        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(1,6));
        for(int i = 0;i<4;i++){
            errorPanel.add(new JPanel());
        }
        errorPanel.add(errorV);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,1));
        bottomPanel.add(errorPanel);
        bottomPanel.add(voterRegistrationPanel1);

        add(auditorLoginPanel);
        add(electionResultsPanel);
        add(voterLoginPanel);
        add(bottomPanel);
    }
}
