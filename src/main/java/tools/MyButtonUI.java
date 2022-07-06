package tools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyButtonUI extends BasicButtonUI implements java.io.Serializable,
        MouseListener, KeyListener {

    private final static MyButtonUI m_buttonUI = new MyButtonUI();

    protected Border m_borderRaised = UIManager.getBorder("Button.border");

    protected Border m_borderLowered = UIManager
            .getBorder("Button.borderPressed");

    protected Color m_backgroundNormal = UIManager
            .getColor("Button.background");

    protected Color m_backgroundPressed = new Color(0, 135, 144 );

    protected Color m_foregroundNormal = UIManager
            .getColor("Button.foreground");

    protected Color m_foregroundActive = UIManager
            .getColor("Button.activeForeground");

    protected Color m_focusBorder = UIManager.getColor("Button.focusBorder");

    public static ComponentUI createUI(JComponent c) {
        return m_buttonUI;
    }

    public void installUI(JComponent c) {
        super.installUI(c);

        c.addMouseListener(this);
        c.addKeyListener(this);
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.removeMouseListener(this);
        c.removeKeyListener(this);
    }

    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Dimension d = b.getSize();

        g.setFont(c.getFont());
        FontMetrics fm = g.getFontMetrics();

        g.setColor(b.getForeground());
        String caption = b.getText();
        int x = (d.width - fm.stringWidth(caption)) / 2;
        int y = (d.height + fm.getAscent()) / 2;
        g.drawString(caption, x, y);

    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        if (m_borderRaised != null) {
            Insets ins = m_borderRaised.getBorderInsets(c);
            d.setSize(d.width + ins.left + ins.right, d.height + ins.top
                    + ins.bottom);
        }
        return d;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setBorder(m_borderLowered);
        c.setBackground(m_backgroundPressed);
    }

    public void mouseReleased(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setBorder(m_borderRaised);
        c.setBackground(m_backgroundNormal);
    }

    public void mouseEntered(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setForeground(m_foregroundActive);
        c.repaint();
    }

    public void mouseExited(MouseEvent e) {
        JComponent c = (JComponent) e.getComponent();
        c.setForeground(m_foregroundNormal);
        c.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent) e.getComponent();
            c.setBorder(m_borderLowered);
            c.setBackground(m_backgroundPressed);
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent) e.getComponent();
            c.setBorder(m_borderRaised);
            c.setBackground(m_backgroundNormal);
        }
    }

}