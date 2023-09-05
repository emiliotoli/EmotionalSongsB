package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Emilio Toli
 *         Classe che gestisce l'interfaccia utente per la
 *         creazione di una nuova playlist
 */

public class NuovaPlaylistUI extends JFrame {

    // <editor-fold desc= "Attributi">
    private JTextField nomePlaylistField;
    private JButton submitButton;
    private PlaylistCreationCallback callback;
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param callback
     *                 Metodo utilizzato per creare una nuova playlist
     *                 inserendo il nome della nuova playlist in un'area di testo
     */

    // <editor-fold desc= "Creazione nuova playlist">
    public void nuovaPlaylistUI(PlaylistCreationCallback callback) {
        this.callback = callback;
        setTitle("Nuova Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        nomePlaylistField = GraphicUtils.createTextFields(20);
        submitButton = GraphicUtils.createButtons("Crea Playlist");

        panel.add(GraphicUtils.createLabels("Nome Playlist:"));
        panel.add(nomePlaylistField);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSubmit();
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
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
     *                      Metodo per la gestione dell'evento associato al pulsante
     *                      submit
     *                      Al click di tale pulsante vengono inviati i dati
     */

    // <editor-fold desc= "Gestione evento submit">
    private void handleSubmit() throws IOException, SQLException {
        String nomePlaylist = nomePlaylistField.getText();
        int isPlaylistCreated = Client.creaPlayList(nomePlaylist, Client.idGlobale);

        callback.onPlaylistCreationResult(isPlaylistCreated);
        dispose();
    }
    // </editor-fold>
}
