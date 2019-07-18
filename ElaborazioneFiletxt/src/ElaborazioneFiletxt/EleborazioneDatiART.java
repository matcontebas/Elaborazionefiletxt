package ElaborazioneFiletxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import RicercaFile.FileDialogWindows;

public class EleborazioneDatiART extends Finestra{

	public EleborazioneDatiART() {
		// TODO Auto-generated constructor stub
	}
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
