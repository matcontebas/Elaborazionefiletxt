package ElaborazioneFiletxt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import RicercaFile.FileDialogWindows;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
/**
 * La classe disegna la finestra, gestisce il bottone e provvede
 * all'elaborazione dei file di testo scaricati da ART PMP; Poi provvede
 * a salvare il file riformattato correttamente in un nuovo file con suffisso
 * nomefileorigine_Elaborato.txt
 * @author 08043160
 *
 */
public class Finestra {
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
	public void elaborazionetesto() {
		FileDialogWindows trovafiletxt = new FileDialogWindows("File di testo","txt");
		if (trovafiletxt.getEsito()==1) {
			File filein =new File(trovafiletxt.percorsofile());
			//imposto la variabile globale percorsocompleto con il percorso (ed il nome) del file
			//prima però cancello il contenuto con il metodo delete
			percorsocompleto.delete(0, percorsocompleto.length());
			percorsocompleto.append(trovafiletxt.percorsofile());
			try {
				FileReader fr=new FileReader(filein);
				BufferedReader br=new BufferedReader(fr);
				try {
					txtArea.setText("");
					String st;
					int righe = 0;
					int righenonvuote=0;
					int righecorrette=0;
					//Acquisizione prima riga di intestazione da togliere una volta sistemato il controllo intestazione
					//txtArea.setText(br.readLine()+"\n");
					while ((st=br.readLine())!=null) {
						//intanto escludiamo le righe vuote
						int lunghezza=st.length();
						if (lunghezza!=0) {
							//Inizio conteggio righe corrette (iniziano con RR e finiscono con ;
							if (st.startsWith("RR") && st.charAt(lunghezza-1) ==';') {
								righecorrette++;
							}
							//Fine conteggio righe corrette
							//Inserire qui controllo per intestazione
							if(st.startsWith("ID")) {
								txtArea.setText(txtArea.getText() + st + "\n");
							}	else if (st.startsWith("RR")) {
								txtArea.setText(txtArea.getText() + st + "\n");
							} else {
								//il controllo sulla lunghezza serve per escludere la prima riga di intestazione che va scartata
								//infatti quando si processa la prima riga la lunghezza txtArea è ancora vuoto.
								if (txtArea.getText().length()!=0) {
									//Togliere la stringa \n nel testo
									int lung = txtArea.getText().length();
									//Prendo la sottostringa escludendo il carattere \n
									String modificastringa = (txtArea.getText().substring(0, lung - 1) + " ");
									txtArea.setText(modificastringa.toString());
									//System.out.println("Stampa carattere" + txtArea.getText().charAt(lung-1)+"Vediamo che esce");
									txtArea.setText(txtArea.getText() + st + "\n");
								}
							}
							righenonvuote++;
						}
						righe++;
					}
					JOptionPane.showMessageDialog(finestrastruttura, "conteggio righe totali: " + righe,"Righe totali",JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(finestrastruttura, "conteggio righe non vuote: " + righenonvuote, "RIGHE NON VUOTE",JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(finestrastruttura, "conteggio corrette: righe che iniziano con 'RR' e finiscono con ';': " + righecorrette,"RIGHE CORRETTE",JOptionPane.INFORMATION_MESSAGE);
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//GESTIRE ERRORE
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//GESTIRE ERRORE ACCESSO AL FILE
				JOptionPane.showMessageDialog(finestrastruttura, "ERRORE DI ACCESSO AL FILE", "ERRORE NELL'ACCESSO AL FILE", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(finestrastruttura, "File non trovato","Ricerca File",JOptionPane.WARNING_MESSAGE);
		}
		//fine ricerca file
	}
	public void salvafile(String percorso) {
		//controllo che la stringa non sia vuota nel qual caso non si fa nulla
		if (percorso.length()!=0) {
			/*definisco l'oggetto fin di tipo file per determinare il path del file origine
			con il quale ricostruire il nome del path e del file destinazione con i metodi
			degli oggetti File. Questo metodo mi consente di slegarmi dalle convenzioni
			dei percorsi tra windows e linux*/
			File fin=new File(percorso);
			String percorsofileinput=fin.getPath();
			//Isolo la sottostringa per modificare il nome del file
			String temp= percorsofileinput.substring(0, percorsofileinput.lastIndexOf('.'));
			String fileoutPercorso= temp +"_Elaborato"+".txt";
			JOptionPane.showMessageDialog(finestrastruttura, fileoutPercorso, "Percorso file out",JOptionPane.INFORMATION_MESSAGE);
			File fout=new File(fileoutPercorso);
			try {
				FileWriter fw=new FileWriter(fout);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(txtArea.getText());
				bw.flush();
				bw.close();
				fw.close();
				JOptionPane.showMessageDialog(finestrastruttura, "salvataggio completato nel percorso: "+fileoutPercorso, "Salvataggio file",JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(finestrastruttura, "Errore nel salvataggio del file: "+ e, "Errore"+ e,JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}

