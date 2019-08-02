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
	/*le variabili JFrame, JTextArea e JPanel le metto globali protected per poterle
	modificare nella classe derivata*/
	protected JFrame finestrastruttura;
	protected JTextArea txtArea;
	protected JPanel panello_Bottone;
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

		panello_Bottone = new JPanel();
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
				salvafile(percorsocompleto.toString(),true);
			}
		});
		panello_Bottone.add(btnSalva);
		
		JButton btnMerge = new JButton("Merge");
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mergefile();
			}
		});
		panello_Bottone.add(btnMerge);

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
	 * Elabora il file txt in ingresso, lo elabora e lo scrive sull'area di
	 * testo della finestra.
	 * @return se il risultato dell'elaborazione è andato bene, ritorna true. Altrimenti false.
	 */
	abstract boolean elaborazionetesto();
	/**
	 * Il metodo salvafile salva il file associato nel percorso modificandone il nome (se modificanomefile=true)
	 * con il suffisso _Elaborato
	 * @param percorso il percorso del file di origine
	 * @param modificanomefile se vero, aggiunge al nome del file il suffisso _Elaborato altrimenti no
	 */
	abstract String salvafile(String percorso,boolean modificanomefile);
	/**
	 * Fa il merge dei due file elaborati
	 */
	abstract boolean mergefile();

}

