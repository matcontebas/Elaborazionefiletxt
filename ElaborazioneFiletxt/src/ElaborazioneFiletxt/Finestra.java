package ElaborazioneFiletxt;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * La classe disegna la finestra, gestisce il bottone e provvede
 * all'elaborazione dei file di testo scaricati da ART PMP; Poi provvede
 * a salvare il file riformattato correttamente in un nuovo file con suffisso
 * nomefileorigine_Elaborato.txt
 * @author 08043160
 *
 */
abstract class Finestra {
	//Variabili globali
	protected JFrame finestrastruttura;
	//protected JButton btnAvvio;
	protected JTextArea txtArea;
	//la variabile percorsocompleto mi serve per salvare il percorso (ed il nome) del file origine
	//uso un oggetto StringBuffer poichè la stringa è un oggetto immutabile
	protected StringBuffer percorsocompleto=new StringBuffer();
	/**
	 * il costruttore Finestra() provvede a disegnare la finestra e gestisce il bottone
	 */
	public Finestra() {
		// TODO Auto-generated constructor stub

		finestrastruttura = new JFrame("Finestra di avvio");
		finestrastruttura.getContentPane().setBackground(Color.WHITE);
		finestrastruttura.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panello_Bottone = new JPanel();
		finestrastruttura.getContentPane().add(panello_Bottone, BorderLayout.SOUTH);

		JButton btnAvvio = new JButton("Avvio");
		btnAvvio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elaborazionetesto();
			}
		});
		panello_Bottone.add(btnAvvio);

		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvafile(percorsocompleto.toString());
			}
		});
		panello_Bottone.add(btnSalva);

		JPanel panello_testi = new JPanel();
		finestrastruttura.getContentPane().add(panello_testi, BorderLayout.CENTER);
		panello_testi.setLayout(new BoxLayout(panello_testi, BoxLayout.X_AXIS));

		txtArea = new JTextArea();
		txtArea.setLineWrap(true);
		txtArea.setText("Finestra di testo");
		JScrollPane scroll = new JScrollPane ( txtArea );
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		panello_testi.add(scroll);

		finestrastruttura.setBounds(500, 100, 600, 500);
		finestrastruttura.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finestrastruttura.setVisible(true);

	}

	/**
	 * Il metodo elabtesto ricerca il file di testo all'interno delle cartelle e provvede
	 * all'elaborazione.
	 */
	abstract void elaborazionetesto();
	abstract void salvafile(String percorso);

}

