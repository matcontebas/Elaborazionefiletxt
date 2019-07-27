package ElaborazioneFiletxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import RicercaFile.FileDialogWindows;

public class EleborazioneDatiART extends Finestra{

	/**
	 * @wbp.parser.entryPoint
	 */
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
	public void salvafile(String percorso, boolean modificanomefile) {
		//controllo che la stringa non sia vuota nel qual caso non si fa nulla
		if (percorso.length()!=0) {
			String fileoutPercorso;
			if (modificanomefile) {
				/*definisco l'oggetto fin di tipo file per determinare il path del file origine
						con il quale ricostruire il nome del path e del file destinazione con i metodi
						degli oggetti File. Questo metodo mi consente di slegarmi dalle convenzioni
						dei percorsi tra windows e linux*/
				File fin = new File(percorso);
				String percorsofileinput = fin.getPath();
				//Isolo la sottostringa per modificare il nome del file
				String temp = percorsofileinput.substring(0, percorsofileinput.lastIndexOf('.'));
				fileoutPercorso = temp + "_Elaborato" + ".txt";
			} else {
				fileoutPercorso=percorso;
			}
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
	public void mergefile() {
		boolean controlloflusso=false;
		FileDialogWindows file1 = new FileDialogWindows("File di testo","txt");
		if (file1.getEsito()==1) {
			File f1=new File(file1.percorsofile());
			try {
				FileReader f1r = new FileReader(f1);
				BufferedReader b1r=new BufferedReader(f1r);
				String st;
				StringBuffer contenuto=new StringBuffer();
				while ((st=b1r.readLine())!=null) {
					//memorizzo il contenuto del file in contenuto variabile di tipo StringBuffer
					contenuto.append(st+"\n");
				}
				JOptionPane.showMessageDialog(finestrastruttura, "File letto", "Righe lette", JOptionPane.INFORMATION_MESSAGE);
				txtArea.setText(contenuto.toString());
				controlloflusso=true;
				f1r.close();
			} catch (Exception e) {
				// TODO: handle exception
				controlloflusso=false;
				e.printStackTrace();
			}
		}
		//----------- Parto con il file2------------------
		//Parto con il file 2 solo se è stato elaborato il file 1 (controlloflusso=vero)
		if (controlloflusso) {
			FileDialogWindows file2 = new FileDialogWindows("File di testo", "txt");
			if (file2.getEsito()==1) {
				File f2=new File(file2.percorsofile());
				try {
					FileReader f2r =new FileReader(f2);
					BufferedReader b2r = new BufferedReader(f2r);
					String temp;
					StringBuffer contenuto2=new StringBuffer();
					try {
						while ((temp=b2r.readLine())!=null) {
							//l'if successivo serve per escludere la riga di intestazione del secondo file
							if (temp.startsWith("RR")) {
								//memorizzo il contenuto del file in contenuto variabile di tipo StringBuffer
								contenuto2.append(temp + "\n");
							}
						}
						JOptionPane.showMessageDialog(finestrastruttura, "File letto", "Righe lette", JOptionPane.INFORMATION_MESSAGE);
						f2r.close();
						//Faccio il merge dei due file
						txtArea.setText(txtArea.getText()+contenuto2.toString());
						//per salvare il file definitivo utilizzo l'oggetto JFileChooser per scegliere il percorso
						//e il nome del file
						JFileChooser sceltafile = new JFileChooser();
						//inserimento del filtro per ricercare solamente file di testo
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
						        "File di testo", "txt");
						sceltafile.setFileFilter(filter);
						int n=sceltafile.showSaveDialog(finestrastruttura);
						if (n == JFileChooser.APPROVE_OPTION) {
							File f=sceltafile.getSelectedFile();
							JOptionPane.showMessageDialog(finestrastruttura, f.getPath());
							salvafile(f.getPath(),false);
						} else {
							JOptionPane.showMessageDialog(finestrastruttura, "Scegliere il nome del file su cui fare il merge", "Warning", JOptionPane.WARNING_MESSAGE);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						controlloflusso=false;
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					controlloflusso=false;
					e.printStackTrace();
				}
			}
		}
	}
}
