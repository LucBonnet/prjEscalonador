package Telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JCheckBox;

public class JCheckBoxCustom extends JCheckBox{
    private final int border = 8;
    private final int cbSize = 16;    

    
    public JCheckBoxCustom() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
    }
    
    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        
        Graphics2D g2 = (Graphics2D) gr;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ly = (getHeight() - cbSize) /2;
        
        if(isSelected()) {
            if(isEnabled()) {
                g2.setColor(getBackground());
            } else {
                g2.setColor(Color.GRAY);
            }
            g2.fillRoundRect(1, ly, cbSize, cbSize, border, border);
            
            int px[] = {4, 8, 14, 12, 8, 6};
            int py[] = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(px, py, px.length);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRoundRect(1, ly, cbSize, cbSize, border, border);
        }
    }
}
