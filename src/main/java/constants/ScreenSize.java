package constants;

import java.awt.*;

public class ScreenSize {
    static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int MAX_WIDTH = (int) size.getWidth();
    public static final int MAX_HEIGHT = (int) size.getHeight();
}
