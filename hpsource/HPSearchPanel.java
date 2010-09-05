package hoipolloi;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.awt.event.*;
/**
 *
 * @author Brandon
 */
public class HPSearchPanel extends JPanel implements ActionListener {
    private InfoJTextField searchField;
    private JLabel resultsTotalLabel;
    private JPanel miniProfilePanel;
    private MainMenu menuFrame;

    private JCheckBox firstNameBox;
    private JCheckBox lastNameBox;
    private JCheckBox nickNameBox;
    private JCheckBox descriptionBox;
    private JCheckBox contactsBox;
    private JCheckBox addressesBox;

    private Search search;

    public HPSearchPanel(MainMenu menuFrame) {
        super();
        this.menuFrame = menuFrame;
        search = new Search();
        this.setLayout(new GridLayout(1, 1));
        this.add(buildPanel());
    }

    private JPanel buildPanel() {
        JPanel northPanel = buildNorthPanel();
        JPanel southPanel = buildSouthPanel();

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(northPanel, BorderLayout.NORTH);
        searchPanel.add(southPanel, BorderLayout.CENTER);

        return searchPanel;
    }

    private JPanel buildNorthPanel() {
        searchField = new InfoJTextField(66, "Enter Search Query...");
        searchField.getDocument().addDocumentListener(new SearchFieldDocumentListener());
        resultsTotalLabel = new JLabel("0");
        firstNameBox = new JCheckBox("First Name");
        firstNameBox.setSelected(true);
        firstNameBox.addActionListener(this);
        lastNameBox = new JCheckBox("Last Name");
        lastNameBox.setSelected(true);
        lastNameBox.addActionListener(this);
        nickNameBox = new JCheckBox("Nick Name");
        nickNameBox.setSelected(true);
        nickNameBox.addActionListener(this);
        descriptionBox = new JCheckBox("Description");
        descriptionBox.setSelected(true);
        descriptionBox.addActionListener(this);
        contactsBox = new JCheckBox("Contacts");
        contactsBox.setSelected(true);
        contactsBox.addActionListener(this);
        addressesBox = new JCheckBox("Addresses");
        addressesBox.setSelected(true);
        addressesBox.addActionListener(this);

        JPanel queryFieldPanel = new JPanel();
        queryFieldPanel.add(searchField);
        queryFieldPanel.setPreferredSize(new Dimension(500, 32));

        JLabel resultsLabel = new JLabel("Results:");
        JPanel resultsLabelPanel = new JPanel();
        resultsLabelPanel.setLayout(new BorderLayout());
        resultsLabelPanel.add(resultsLabel, BorderLayout.EAST);
        JPanel resultsTotalPanel = new JPanel();
        resultsTotalPanel.setPreferredSize(new Dimension(50, 20));
        resultsTotalPanel.setLayout(new BorderLayout());
        resultsTotalPanel.add(resultsTotalLabel, BorderLayout.WEST);
        JPanel resultsPanel = new JPanel();
        resultsPanel.add(resultsLabelPanel);
        resultsPanel.add(resultsTotalPanel);
        resultsPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(queryFieldPanel, BorderLayout.CENTER);
        topPanel.add(resultsPanel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(firstNameBox);
        optionsPanel.add(lastNameBox);
        optionsPanel.add(nickNameBox);
        optionsPanel.add(descriptionBox);
        optionsPanel.add(contactsBox);
        optionsPanel.add(addressesBox);
        optionsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(optionsPanel, BorderLayout.SOUTH);

        return northPanel;
    }

    private JPanel buildSouthPanel() {
        miniProfilePanel = new JPanel();
        miniProfilePanel.setLayout(new BoxLayout(miniProfilePanel, BoxLayout.Y_AXIS));

        JScrollPane miniScrollPane = new JScrollPane(miniProfilePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        miniScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 1));
        southPanel.add(miniScrollPane);

        return southPanel;
    }

    private void updateResults(ArrayList<KeyValue> searchResults) {
        Debug.startTimer("update");
        int results;

        if (searchResults == null)
            results = 0;
        else
            results = searchResults.size();
        if (results == 0)
            emptyResults();
        else if (searchField.getText().length() > 0) {
            miniProfilePanel.removeAll();
            resultsTotalLabel.setText("" + results);
            for (KeyValue kv: searchResults) {
                try {
                    miniProfilePanel.add(new HPMiniProfilePanel(new Person(kv.getKey()), menuFrame));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    private void emptyResults() {
        resultsTotalLabel.setText("0");
        miniProfilePanel.removeAll();
        miniProfilePanel.updateUI();
    }

    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox)e.getSource();

        if (source.equals(firstNameBox)) {
            if (firstNameBox.isSelected())
                search.searchFirstName(true);
            else
                search.searchFirstName(false);
        }
        else if (source.equals(lastNameBox)) {
            if (lastNameBox.isSelected())
                search.searchLastName(true);
            else
                search.searchLastName(false);
        }
        else if (source.equals(nickNameBox)) {
            if (nickNameBox.isSelected())
                search.searchNickName(true);
            else
                search.searchNickName(false);
        }
        else if (source.equals(descriptionBox)) {
            if (descriptionBox.isSelected())
                search.searchDescription(true);
            else
                search.searchDescription(false);
        }
        else if (source.equals(contactsBox)) {
            if (contactsBox.isSelected())
                search.searchContacts(true);
            else
                search.searchContacts(false);
        }
        else if (source.equals(addressesBox)) {
            if (addressesBox.isSelected())
                search.searchAddresses(true);
            else
                search.searchAddresses(false);
        }

        updateResults(search.performSearch(searchField.getText()));
    }

    class SearchFieldDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            Debug.startTimer("minisearch");
            updateResults(search.performSearch(searchField.getText()));
            Debug.endTimer("minisearch");
        }

        public void removeUpdate(DocumentEvent e) {
            Debug.startTimer("minisearch");
            try {
                if (e.getDocument().getText(0, e.getDocument().getLength()).equals(""))
                    emptyResults();
                else {
                    updateResults(search.performSearch(searchField.getText()));
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            Debug.endTimer("minisearch");
        }

        public void changedUpdate(DocumentEvent e) { }

    }
}
