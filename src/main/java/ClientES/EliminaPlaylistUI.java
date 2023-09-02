package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Emilio Toli
 *      Classe per l'eliminazione di una playlist tra la lista
 *      delle playlist dell'utente
 */

public class EliminaPlaylistUI extends JFrame {
    // <editor-fold desc= "Attributi">// </editor-fold>
    private JTextField nomePlaylistField;
    private JButton submitButton;
    boolean res;
    private PlaylistDeletionCallback callback;
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param callback
     *      Metodo che crea la UI e gestisce l'eliminazione della playlist, con il nome della
     *      playlist da eliminare inserito in un'area di testo
     */
    // <editor-fold desc= "Eliminazione playlist">
    public void eliminaPlaylistUI(PlaylistDeletionCallback callback) {
        this.callback = callback;

        setTitle("Elimina Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        nomePlaylistField = new JTextField(15);
        submitButton = new JButton("Elimina Playlist");

        panel.add(new JLabel("Nome Playlist:"));
        panel.add(nomePlaylistField);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSubmit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        add(panel);
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @throws IOException
     * @throws SQLException
     *
     *      Metodo per la gestione dell'evento associato al click del pulsante
     *      Al click chiama il metodo della classe Client per l'eliminazione
     */
    // <editor-fold desc= "Gestione click button">
    private void handleSubmit() throws IOException, SQLException {
        String nomePlaylist = nomePlaylistField.getText();
        int res = Client.eliminaPlaylist(nomePlaylist);
        callback.onPlaylistDeletionResult(res);
        dispose();
    }
    // </editor-fold>
}