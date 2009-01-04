package hoipolloi;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * This class describes a theme using orange colors.
 *
 * @version 1.1 12/27/07 16:49
 * @author  Brandon Tanner
 * @since   Dec 14, 2007
 */
public class DarudeTheme extends DefaultMetalTheme {

    /** Gets the theme name */
    @Override public String getName() { return "Darude"; }

    /** Menu Outline */
    private final ColorUIResource primary1 = new ColorUIResource(255, 255, 255);
    
    /** Button Outline */
    private final ColorUIResource primary2 = new ColorUIResource(0, 255, 255);
    
    /** Menu Selection Color */
    private final ColorUIResource primary3 = new ColorUIResource(255, 255, 0);
    
    /** Widget Outline */
    private final ColorUIResource secondary1 = new ColorUIResource(0, 0, 0);
    
    /** Menu Outline, Disabled Color */
    private final ColorUIResource secondary2 = new ColorUIResource(255, 255, 0);
    
    /** Window Background */
    private final ColorUIResource secondary3 = new ColorUIResource(255, 153, 0);
    
    /** Active Text Foreground */
    private final ColorUIResource black = new ColorUIResource(0, 0, 0);
    
    /** Menu Separator, Check box/Radio Buttons backgrounds, Text Area Backgrounds */
    private final ColorUIResource white = new ColorUIResource(255, 255, 0);
    
    @Override protected ColorUIResource getPrimary1() { return primary1; }
    @Override protected ColorUIResource getPrimary2() { return primary2; }
    @Override protected ColorUIResource getPrimary3() { return primary3; }

    @Override protected ColorUIResource getSecondary1() { return secondary1; }
    @Override protected ColorUIResource getSecondary2() { return secondary2; }
    @Override protected ColorUIResource getSecondary3() { return secondary3; }

    @Override protected ColorUIResource getBlack() { return black; }
    @Override protected ColorUIResource getWhite() { return white; }

}
