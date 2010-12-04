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

    private UpdateSearchListThread updateThread;

    public HPSearchPanel(MainMenu menuFrame) {
        super();
        this.menuFrame = menuFrame;
        this.updateThread = new UpdateSearchListThread(this);
        this.updateThread.start();

        this.setLayout(new GridLayout(1, 1));
        this.add(buildPanel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                searchField.requestFocusInWindow();
            }
        });
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

    private JPanel getNewMiniProfilePanel() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));

        return returnPanel;
    }

    private JPanel buildSouthPanel() {
        this.miniProfilePanel = getNewMiniProfilePanel();

        JScrollPane miniScrollPane = new JScrollPane(miniProfilePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        miniScrollPane.getVerticalScrollBar().setUnitIncrement(30);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 1));
        southPanel.add(miniScrollPane);

        return southPanel;
    }

    protected void emptyResults() {
        resultsTotalLabel.setText("0");
        miniProfilePanel.removeAll();
        miniProfilePanel.validate();
        miniProfilePanel.updateUI();
    }

    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox)e.getSource();

        if (source.equals(firstNameBox)) {
            if (firstNameBox.isSelected())
                updateThread.getSearch().searchFirstName(true);
            else
                updateThread.getSearch().searchFirstName(false);
        }
        else if (source.equals(lastNameBox)) {
            if (lastNameBox.isSelected())
                updateThread.getSearch().searchLastName(true);
            else
                updateThread.getSearch().searchLastName(false);
        }
        else if (source.equals(nickNameBox)) {
            if (nickNameBox.isSelected())
                updateThread.getSearch().searchNickName(true);
            else
                updateThread.getSearch().searchNickName(false);
        }
        else if (source.equals(descriptionBox)) {
            if (descriptionBox.isSelected())
                updateThread.getSearch().searchDescription(true);
            else
                updateThread.getSearch().searchDescription(false);
        }
        else if (source.equals(contactsBox)) {
            if (contactsBox.isSelected())
                updateThread.getSearch().searchContacts(true);
            else
                updateThread.getSearch().searchContacts(false);
        }
        else if (source.equals(addressesBox)) {
            if (addressesBox.isSelected())
                updateThread.getSearch().searchAddresses(true);
            else
                updateThread.getSearch().searchAddresses(false);
        }

        updateThread.updateResults(searchField.getText());
    }

    protected JPanel getMiniProfilPanel() {
        return miniProfilePanel;
    }

    protected void setNumResults(int numResults) {
        resultsTotalLabel.setText("" + numResults);
    }

    protected int getSearchFieldLength() {
        return searchField.getText().length();
    }

    private class SearchFieldDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            Debug.startTimer("minisearch-update");
            new Thread() {
                public void run() {
                    updateThread.interrupt();
                    updateThread.updateResults(searchField.getText());
                }
            }.start();
            Debug.endTimer("minisearch-update");
        }

        public void removeUpdate(DocumentEvent e) {
            Debug.startTimer("minisearch-remove");
            new Thread() {
                public void run() {
                    updateThread.interrupt();
                    updateThread.updateResults(searchField.getText());
                }
            }.start();
            Debug.endTimer("minisearch-remove");
        }

        public void changedUpdate(DocumentEvent e) { }
    }

    private class UpdateSearchListThread extends Thread {
        private HPSearchPanel searchPanel;
        private ArrayList<KeyValue> results;
        private boolean updating;
        private Search search;
        
        public UpdateSearchListThread(HPSearchPanel searchPanel) {
            this.searchPanel = searchPanel;
            results = new ArrayList<KeyValue>();
            updating = false;
            search = new Search();
        }

        public void run() {
            try {
                if (updating) {
                    int resultsLength;
                    JPanel miniPanel = searchPanel.getMiniProfilPanel();

                    if (results == null)
                        resultsLength = 0;
                    else
                        resultsLength = results.size();

                    if (resultsLength == 0 || getSearchFieldLength() == 0)
                        searchPanel.emptyResults();
                    else {
                        searchPanel.emptyResults();
                        searchPanel.setNumResults(resultsLength);

                        for (KeyValue kv: results) 
                            miniPanel.add(new HPMiniProfilePanel(new Person(kv.getKey()), menuFrame));

                        System.out.println("\n\n" + resultsLength + "\n\n");
                        searchPanel.validate();
                        searchPanel.updateUI();
                        updating = false;
                    }
                }
            } catch (Exception e) {
                Debug.print(e.getMessage());
                updating = false;
                results = null;
            }
        }

        private void updateResults(String query) {
            if (updating)
                interrupt();

            results = search.performSearch(query);
            
            updating = true;
            run();
        }

        public Search getSearch() {
            return search;
        }
    }
}
