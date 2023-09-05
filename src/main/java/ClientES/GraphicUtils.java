package ClientES;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GraphicUtils {
    private static final Color textColor = new Color(76, 79, 105);
    private static final Color background = new Color(204, 208, 218);
    private static final Color borderColor = new Color(4, 165, 229);
    private static final int fontTextSize = 16;

    /**
     * @author Cristian Stinga
     * @param String text stringa mostrata dal pulsante
     * @return Jbutton
     *
     *         metodo che inizializza globalmente i pulsanti
     */
    public static JButton createButtons(String text) {
        JButton bt = new JButton(text);

        bt.setPreferredSize(new Dimension(250, 50));
        bt.setBorder(new LineBorder(borderColor, 1));
        bt.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        bt.setForeground(textColor);

        return bt;
    };

    /**
     * @author Cristian Stinga
     * @param String text stringa mostrata dalla label
     * @return Jlabel
     *
     *         metodo che inizializza globalmente le label
     */
    public static JLabel createLabels(String text) {
        JLabel lb = new JLabel(text);

        lb.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        lb.setForeground(textColor);

        return lb;
    }

    /**
     * @author Cristian Stinga
     * @param int text larghezza etichetta
     * @return JTextField
     *
     *         metodo che inizializza globalmente i textfields
     */
    public static JTextField createTextFields(int dim) {
        JTextField tf = new JTextField();

        tf.setForeground(textColor);
        tf.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        tf.setColumns(dim);
        tf.setPreferredSize(new Dimension(100, 25));

        return tf;
    }
}
