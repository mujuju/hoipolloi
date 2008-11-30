package hoipolloi;

import java.awt.*;
import javax.swing.*;

public class CategoryBox extends JFrame {
    
    ReportingListTransferHandler arrayListHandler = new ReportingListTransferHandler();

    public CategoryBox(JFrame owner, Person person) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.getContent());
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
    private JPanel getContent() {
        JPanel panel = new JPanel(new GridLayout(1,0));
        panel.add(getListComponent("left"));
        panel.add(getListComponent("right"));
        return panel;
    }

    private JScrollPane getListComponent(String s) {
        DefaultListModel model = new DefaultListModel();
        for(int j = 0; j < 5; j++)
            model.addElement(s + " " + (j+1));
        JList list = new JList(model);
        list.setName(s);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setTransferHandler(arrayListHandler);
        list.setDragEnabled(true);
        return new JScrollPane(list);
    }

}