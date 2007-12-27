/*
 * @(#)RubyTheme.java	1.0 12/19/06
 */

package hoipolloi;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This class describes a theme using red colors.
 *
 * @version 1.1 12/27/07
 * @author  Brandon Buck
 * @since   12/09/06
 */
public class RubyTheme extends DefaultMetalTheme {

    /** Gets the theme name */
    @Override public String getName() { return "Ruby"; }

    /** Menu Outline */
    private final ColorUIResource primary1 = new ColorUIResource(55, 13, 13);
    
    /** Button Outline */
    private final ColorUIResource primary2 = new ColorUIResource(117, 115, 130);
    
    /** Menu Selection Color */
    private final ColorUIResource primary3 = new ColorUIResource(189, 72, 72);
    
    /** Widget Outline */
    private final ColorUIResource secondary1 = new ColorUIResource(185, 71, 71);
    
    /** Menu Outline, Disabled Color */
    private final ColorUIResource secondary2 = new ColorUIResource(187, 96, 96);
    
    /** Window Background */
    private final ColorUIResource secondary3 = new ColorUIResource(130, 43, 43);
    
    /** Active Text Foreground */
    private final ColorUIResource black = new ColorUIResource(222, 222, 222);
    
    /** Menu Separator, Check box/Radio Buttons backgrounds, Text Area Backgrounds */
    private final ColorUIResource white = new ColorUIResource(91, 35, 35);
    
    @Override protected ColorUIResource getPrimary1() { return primary1; }
    @Override protected ColorUIResource getPrimary2() { return primary2; }
    @Override protected ColorUIResource getPrimary3() { return primary3; }

    @Override protected ColorUIResource getSecondary1() { return secondary1; }
    @Override protected ColorUIResource getSecondary2() { return secondary2; }
    @Override protected ColorUIResource getSecondary3() { return secondary3; }

    @Override protected ColorUIResource getBlack() { return black; }
    @Override protected ColorUIResource getWhite() { return white; }

}
