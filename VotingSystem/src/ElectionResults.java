import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ElectionResults extends JFrame{

    public ElectionResults(){
        super("Election Results");

        String[] regions = {"Country", "State", "County"};
        JList regionList = new JList<String>(regions);
        regionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(regionList));
        regionList.addListSelectionListener(
                new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent event){
                        int num = regionList.getSelectedIndex();
                        switch (num){
                            case 0:
                                System.out.println("Country");
                                break;
                            case 1:
                                System.out.println("State");
                                break;
                            case 2:
                                System.out.println("County");
                                break;
                        }
                    }
                }
        );

    }
}
