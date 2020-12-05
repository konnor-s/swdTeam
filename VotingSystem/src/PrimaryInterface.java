import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimaryInterface extends JFrame {
    PrimaryInterface(){
        super("Voting System");
        setLayout(new FlowLayout());
        JLabel info = new JLabel("County Auditor Login.");
        JLabel pswrd = new JLabel("Password");
        JTextField pswrField = new JTextField("Password");
        JTextField county = new JTextField("County");
        JTextField state = new JTextField("State");
        JButton login = new JButton();
        login.setText("Auditor Login");
        login.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        CreateBallot bGui = new CreateBallot(county.getText(),state.getText());
                        // bGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        bGui.setSize(1200,800);
                        bGui.setVisible(true);

                    }
                }
        );
        JLabel voterLogin = new JLabel("Voter Login");
        JTextField name = new JTextField("First Last");
        JButton vLogin = new JButton();
        vLogin.setText("Voter Login");
        vLogin.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Ballot vGui = new Ballot(county.getText(),state.getText());
                        //vGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        vGui.setSize(600,800);
                        vGui.setVisible(true);
                    }
                }
        );
        add(info);
        add(pswrd);
        add(pswrField);
        add(county);
        add(state);
        add(login);
        add(voterLogin);
        add(name);
        add(vLogin);
    }
}
