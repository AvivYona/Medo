package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;
//import Settings.Settings;

public class StyledComboUI extends BasicComboBoxUI{
     @Override
      public void paint(Graphics g, JComponent c) {
          super.paint(g, c);

            // Customize the appearance of the combo box here
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = c.getWidth();
            int h = c.getHeight();

            // Draw a white background with a subtle gradient
            //GradientPaint gp = new GradientPaint(0, 0, new Color(c.getBackground().getRed(),c.getBackground().getGreen(),c.getBackground().getBlue(),100), 0, h, new Color(210,210,210,100));
            //g2d.setPaint(gp);
            g2d.setColor(Color.black);
            g2d.fillRoundRect(0, 0, w - 1, h - 1, 20, 20);

            // Draw a black border
            g2d.setColor(new Color(235, 213, 189));
            g2d.fillRoundRect(2, 0, w - 5, h - 4, 20, 20);
            g.setColor(c.getForeground());
            g.drawString((String) super.comboBox.getSelectedItem(), 10,(h - 15) / 2 + 15);            
            
            // Draw a black arrow on the right side
            Polygon arrow = new Polygon();
            arrow.addPoint(w - 8, h / 2 - 3);
            arrow.addPoint(w - 18, h / 2 - 3);
            arrow.addPoint(w - 13, h / 2 + 3);
            g2d.fillPolygon(arrow);
            //super.paint(g, c);
          }
     @Override
     protected JButton createArrowButton() {
         // TODO Auto-generated method stub
         return null;
     }
     
     @Override
     public void installUI(JComponent c) {
         super.installUI(c);
         //JComboBox cc = (JComboBox)c;
         //cc.setOpaque(false);
         c.setBackground(null);
     }

     @Override
     public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
         super.paintCurrentValue(g, bounds, false);
     }

 }