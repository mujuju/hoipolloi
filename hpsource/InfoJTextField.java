package hoipolloi;

import javax.swing.JTextField;
import java.awt.*;
/**
 * A Text Field that displays a light gray message in the background of an empty
 * Text Field, when text is entered it
 * @author Brandon Buck
 */
public class InfoJTextField extends JTextField {
    private String infoMessage;
    private Color textColor = new Color(183, 183, 183);

    public InfoJTextField() {
        super();
        this.infoMessage = "No message has been set...";
    }

    public InfoJTextField(String infoMessage) {
        super();
        this.infoMessage = infoMessage;
    }

    public InfoJTextField(int columns) {
        super(columns);
        this.infoMessage = "No message has been set...";
    }

    public InfoJTextField(int columns, String infoMessage) {
        super(columns);
        this.infoMessage = infoMessage;
    }

    public String getInfoMessage() {
        return this.infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.getText().equals("")) {
            g.setColor(textColor);
            g.setFont(super.getFont().deriveFont(Font.BOLD + Font.ITALIC, 14));
            java.awt.geom.Rectangle2D bounds = g.getFontMetrics().getStringBounds(infoMessage, g);
            int x = 5;
            int y = ((int)super.getHeight() / 2) - ((int)bounds.getHeight() / 2);

            g.drawString(infoMessage, x, y + g.getFont().getSize());
        }
    }
}
