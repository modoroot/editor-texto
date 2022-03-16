package dual.actividad3;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.awt.GraphicsEnvironment;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;

/**
 * Clase donde se realiza el programa de Editor de texto entero con interfaz gráfica.
 * Se usan interfaces como DocumentListener para actualizar in live
 * @author amna
 * @version 1.0
 */
//ActionListener para manejar eventos. DocumentListener para actualizar in live
//algunos contenidos
public class EditorTexto extends JFrame implements ActionListener, DocumentListener{
	JTextArea textArea;
	JTextArea textArea2;
	JTextArea textAreaCompare;
	JScrollPane scrollPane;
	JLabel fontContainerLabel;
	JSpinner fontSizeEditor;
	JComboBox fontSelector;
	JLabel statusWordCounterLabel;
	JLabel statusLinesCounterLabel;
	//Añadimos una barra de herramientas
	JMenuBar toolBar;
	JMenu toolManagerArchivo;
	JMenu toolManagerFuente;
	JMenuItem createDoc;
	JMenuItem openDoc;
	JMenuItem saveDoc;
	JMenuItem exitDoc;
	JMenuItem recentDoc;
	JMenuItem negritaFont;
	JMenuItem cursivaFont;
	//Barra de menú
	JPanel menuBar;
	JButton createDocButton;
	JButton openDocButton;
	JButton saveDocButton;
	JButton exitDocButton;
	JButton recentDocButton;
	JButton negritaFontButton;
	JButton cursivaFontButton;
	JButton compareButton;
	//Añadimos una barra de estado
	JPanel statusBar;
	JLabel wordCounter;
	JLabel linesCounter;
	//Pestañas
	JTabbedPane tab1;
	JTabbedPane tab2;
	JTabbedPane tab3;
	public EditorTexto(){
		//cierra el programa cuando clickeas en la X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Editor de texto AMNA");
		this.setSize(700, 750);
		//para que organice automáticamente los componentes
		this.setLayout(new FlowLayout());
		//esto hace que aparezca en medio de la pantalla (valor null)
		this.setLocationRelativeTo(null);
		//personalizando el contenedor donde se escribe en el procesador de textos
		textArea = new JTextArea();
		textArea2 = new JTextArea();
		textAreaCompare = new JTextArea();
		//cuando llegue al límite del contenedor, actúa como si se diese un salto de línea
		//(no se da) para que así no llegue hasta el infinito
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea2.setLineWrap(true);
		textArea2.setWrapStyleWord(true);
		textAreaCompare.setLineWrap(true);
		textAreaCompare.setWrapStyleWord(true);
		//tipografía por defecto
		textArea.setFont(new Font("Liberation Serif",Font.PLAIN,18));
		textArea2.setFont(new Font("Liberation Serif",Font.PLAIN,18));
		textAreaCompare.setFont(new Font("Liberation Serif",Font.PLAIN,18));
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450, 450));
		//Añadimos la barra vertical. Sólo se mostrará cuando se necesite
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fontContainerLabel = new JLabel("Fuente tipografía: ");
		fontSizeEditor = new JSpinner();
		fontSizeEditor.setPreferredSize(new Dimension(40,25));
		//valor por defecto de la fuente una vez se inicia el programa 
		fontSizeEditor.setValue(18);
		fontSizeEditor.addChangeListener(new ChangeListener() {
			/**
			 * método que cambia el tamaño de la fuente incluido el texto ya escrito
			 */
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, 
						(int) fontSizeEditor.getValue()));
				textArea2.setFont(new Font(textArea2.getFont().getFamily(), Font.PLAIN, 
						(int) fontSizeEditor.getValue()));
			}
		});
		//Inicialización del array con todas las fuentes de Java. Guarda el nombre 
		//de todas las fuentes
		String[] javaFonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		//Se asigna al objeto fontSelector el array inicializado anterior con el nombre de todas
		//las fuentes en Java
		fontSelector = new JComboBox(javaFonts);
		//Al tener implementada la interfaz ActionListener podemos usarla
		fontSelector.addActionListener(this);
		//Asignamos una fuente por defecto
		fontSelector.setSelectedItem("Liberation Serif");
		//Barra de herramientas
		toolBar = new JMenuBar();
		toolManagerArchivo = new JMenu("Archivo");
		toolManagerFuente = new JMenu("Fuente");
		createDoc = new JMenuItem("Crear");
		openDoc = new JMenuItem("Abrir");
		recentDoc = new JMenuItem("Abrir archivo más reciente");
		saveDoc = new JMenuItem("Guardar");
		exitDoc = new JMenuItem("Salir");
		negritaFont = new JMenuItem("Cambiar texto a negrita");
		cursivaFont = new JMenuItem("Cambiar texto a cursiva");
		//Barra de menú
		menuBar = new JPanel();
		createDocButton = new JButton("Crear");
		openDocButton = new JButton("Abrir");
		recentDocButton = new JButton("Abrir rec.");
		saveDocButton = new JButton("Guardar");
		exitDocButton = new JButton("Salir");
		negritaFontButton = new JButton("Negrita");
		cursivaFontButton = new JButton("Cursiva");
		compareButton = new JButton("Comparar");
		//Pestañas
		tab1 = new JTabbedPane();
		tab2 = new JTabbedPane();
		tab3 = new JTabbedPane();
		//Se añade el método que activa a los botones para que funcionen
		createDocButton.addActionListener(this);
		openDocButton.addActionListener(this);
		recentDocButton.addActionListener(this);
		saveDocButton.addActionListener(this);
		exitDocButton.addActionListener(this);
		negritaFontButton.addActionListener(this);
		cursivaFontButton.addActionListener(this);
		compareButton.addActionListener(this);
		//Al tener implementada la interfaz ActionListener se la podemos pasar directamente
		//para que ejecute los eventos creados en el método actionPerformed
		createDoc.addActionListener(this);
		openDoc.addActionListener(this);
		recentDoc.addActionListener(this);
		saveDoc.addActionListener(this);
		exitDoc.addActionListener(this);
		negritaFont.addActionListener(this);
		cursivaFont.addActionListener(this);
		toolBar.add(toolManagerArchivo);
		toolBar.add(toolManagerFuente);
		toolManagerArchivo.add(createDoc);
		toolManagerArchivo.add(openDoc);
		toolManagerArchivo.add(recentDoc);
		toolManagerArchivo.add(saveDoc);
		toolManagerArchivo.add(exitDoc);
		toolManagerFuente.add(negritaFont);
		toolManagerFuente.add(cursivaFont);
		//Barra de estado
		statusBar = new JPanel();
		wordCounter = new JLabel();
		linesCounter = new JLabel();
		//Pestañas
		tab1.add("Archivo 1", textArea);
		tab1.setPreferredSize(new Dimension(250, 250));
		tab2.add("Archivo 2", textArea2);
		tab2.setPreferredSize(new Dimension(250, 250));
		tab3.add("Comparador", textAreaCompare);
		tab3.setPreferredSize(new Dimension(515, 250));
		//Añade los componentes de forma visual
		//Añade una bara de herramientas
		this.setJMenuBar(toolBar);
		//Texto simple
		this.add(fontContainerLabel);
		//Editor de tamaño de fuente
		this.add(fontSizeEditor);
		//Editor de tipografía para el usuario
		this.add(fontSelector);
		//Botones
		this.add(createDocButton);
		this.add(openDocButton);
		this.add(recentDocButton);
		this.add(saveDocButton);
		this.add(exitDocButton);
		this.add(negritaFontButton);
		this.add(cursivaFontButton);
		this.add(compareButton);
		//Pestañas
		this.add(tab1);
		this.add(tab2);
		this.add(tab3);
		//Barra de estado
		//Añade un borde para visualizar mejor la separación entre el documento y
		//la barra de estado
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		//Coloca abajo del todo la barra de estado
		this.add(statusBar, BorderLayout.SOUTH);
		//Tamaño barra de estado (anchura, altura)
		statusBar.setPreferredSize(new Dimension(450, 20));
		//Ajusta en el eje x la organización del contenedor de la barra
		//de estado
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusWordCounterLabel = new JLabel("Palabras: ");
		statusLinesCounterLabel = new JLabel(" Líneas: ");
		//Texto barra de estado
		statusBar.add(statusWordCounterLabel);
		//Contador de palabras
		statusBar.add(wordCounter);
		//Texto barra de estado
		statusBar.add(statusLinesCounterLabel);
		//Contador de líneas
		statusBar.add(linesCounter);
		/**
		 * Creamos una instancia de DocumentListener para que el programa
		 * obtenga una notificación cada vez que se realiza un cambio.
		 * En este caso, se actualizará el contador cada vez que se inserte
		 * una nueva palabra
		 */
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				countLinesTextArea1();
				countWordsTextArea1();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				countLinesTextArea1();
				countWordsTextArea1();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				countLinesTextArea1();
				countWordsTextArea1();
			}
		});
		textArea2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				countLinesTextArea2();
				countWordsTextArea2();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				countLinesTextArea2();
				countWordsTextArea2();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				countLinesTextArea2();
				countWordsTextArea2();
			}
		});
		//Permite visualizar la ventana
		this.setVisible(true);
	}
	public void countWordsTextArea1() {
		String text = textArea.getText();
		String words[] = text.split("\\s");
		wordCounter.setText(""+words.length);
	}
	public void countWordsTextArea2() {
		String textString2 = textArea2.getText();
		String wordsArray2[] = textString2.split("\\s");
		wordCounter.setText(""+wordsArray2.length);
	}
	public void countLinesTextArea1() {
		int totalLines = textArea.getLineCount();
		linesCounter.setText(""+totalLines);
	}
	public void countLinesTextArea2() {
		int totalLines = textArea2.getLineCount();
		linesCounter.setText(""+totalLines);
	}
	public void negritaTextArea1() {
		textArea.setFont(new Font(textArea.getFont().getFamily(), Font.BOLD, 
				(int) fontSizeEditor.getValue()));
	}
	public void negritaTextArea2() {
		textArea2.setFont(new Font(textArea2.getFont().getFamily(), Font.BOLD, 
				(int) fontSizeEditor.getValue()));
	}
	public void cursivaTextArea1() {
		textArea.setFont(new Font(textArea.getFont().getFamily(), Font.ITALIC, 
				(int) fontSizeEditor.getValue()));	
	}
	public void cursivaTextArea2() {
		textArea2.setFont(new Font(textArea2.getFont().getFamily(), Font.ITALIC, 
				(int) fontSizeEditor.getValue()));	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * Bloque if que cambia la fuente seleccionada a partir de la lista mostrada en fontSelector		
		 */
		if(e.getSource()==fontSelector) {
			//Ajusta la fuente seleccionada en la lista creada anterior en fontSelector
			//y mantiene el tamaño de la fuente actual
			textArea.setFont(new Font((String)fontSelector.getSelectedItem(),Font.PLAIN, textArea.getFont().getSize()));
		}
		/**
		 * Bloque if que crea un nuevo documento
		 */
		if(e.getSource()==createDoc || e.getSource()==createDocButton) {
			textArea.setText("");
		}
		/**
		 * Bloque if que abre cualquier archivo de texto
		 */
		if(e.getSource()==openDoc || e.getSource()==openDocButton || e.getSource()==recentDocButton || e.getSource()==recentDoc) {
			//Instanciamos la clase JFileChooser para seleccionar la ruta donde guardar
			//el fichero
			JFileChooser selectFile = new JFileChooser();
			//Abre el directorio por defecto al clickar en Guardar el directorio actual
			selectFile.setCurrentDirectory(new File("."));
			//Creamos un filtro para que sólamente busque ficheros con extensión txt
			FileNameExtensionFilter filterExtension = new FileNameExtensionFilter("Archivos de texto plano",
					"txt");
			selectFile.setFileFilter(filterExtension);
			//Asistente de apertura de fichero
			int dialogAssistant = selectFile.showOpenDialog(null);
			if(dialogAssistant == JFileChooser.APPROVE_OPTION) {
				File fileOpen = new File(selectFile.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				try {
					fileIn = new Scanner(fileOpen);
					/**
					 * Bloque if que añade el texto dentro del archivo seleccionado para abrir
					 * y copia el contenido (append) dentro del contenedor del programa
					 */
					if(fileOpen.isFile()) {
						while(fileIn.hasNextLine()) {
							String lineaTexto = fileIn.nextLine()+"\n";
							textArea.append(lineaTexto);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}finally {
					//Se cierra el flujo de datos. Supuestamente da error, pero no lo da
					fileIn.close();
				}
			}
			if(e.getSource()==recentDocButton || e.getSource()==recentDoc) {

			}
		}
		if(e.getSource()==saveDoc || e.getSource()==saveDocButton) {
			//Instanciamos la clase JFileChooser para seleccionar la ruta donde guardar
			//el fichero
			JFileChooser selectFile = new JFileChooser();
			//Abre el directorio por defecto al clickar en Guardar el directorio actual
			selectFile.setCurrentDirectory(new File("."));	
			//Asistente de guardado del fichero
			int dialogAssistant = selectFile.showSaveDialog(null);
			//Si la ruta es válida, ejecutará el código dentro del siguiente bloque if
			if(dialogAssistant == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				file = new File(selectFile.getSelectedFile().getAbsolutePath());
				/**
				 * Bloque try-catch que salta la excepción si el fichero es inexistente
				 */
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				}catch(FileNotFoundException f) {
					f.printStackTrace();
				}
				finally {
					//Se cierra el flujo de datos. Supuestamente da error, pero no lo da
					fileOut.close();
				}
			}
		}
		if(e.getSource()==negritaFont || e.getSource()==negritaFontButton) {
			this.negritaTextArea1();
			this.negritaTextArea2();
		}
		if(e.getSource()==cursivaFont || e.getSource()==cursivaFontButton) {
			this.cursivaTextArea1();
			this.cursivaTextArea2();
		}
		if(e.getSource()==compareButton) {
			//Palabras
			String textString1 = textArea.getText();
			String words[] = textString1.split("\\s");
			wordCounter.setText(""+words.length);
			int lengthTextArea = Integer.parseInt(wordCounter.getText());

			String textString2 = textArea2.getText();
			String wordsArray2[] = textString2.split("\\s");
			wordCounter.setText(""+wordsArray2.length);
			int lengthTextArea2 = Integer.parseInt(wordCounter.getText());
			//Líneas
			int totalLinesTextArea1 = textArea.getLineCount();
			int totalLinesTextArea2 = textArea2.getLineCount();
			//Tamaño en bytes
			byte[] bytesTextArea1 = null;
			try {
				bytesTextArea1 = textString1.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			byte[] bytesTextArea2 = null;
			try {
				bytesTextArea2 = textString2.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}


			if(lengthTextArea > lengthTextArea2) {
				textAreaCompare.setText("Archivo 1 tiene más palabras que"
						+ " Archivo 2"+"\n");
			}else if(lengthTextArea == lengthTextArea2) {
				textAreaCompare.setText("Archivo 1 tiene la misma"
						+ " cantidad de palabras que Archivo 2"+"\n");
			}else {
				textAreaCompare.setText("Archivo 2 tiene más palabras que"
						+ " Archivo 1"+"\n");
			}

			if(totalLinesTextArea1 > totalLinesTextArea2) {
				textAreaCompare.setText(textAreaCompare.getText()+"Archivo 1 tiene más líneas"
						+ " que Archivo 2"+"\n");
			}else if(totalLinesTextArea1 == totalLinesTextArea2) {
				textAreaCompare.setText(textAreaCompare.getText()+"Archivo 1 tiene la misma cantidad de líneas"
						+ " que Archivo 2"+"\n");
			}else{
				textAreaCompare.setText(textAreaCompare.getText()+"Archivo 2 tiene más líneas"
						+ " que Archivo 1"+"\n");
			}
			if(bytesTextArea1.length > bytesTextArea2.length) {
				textAreaCompare.setText(textAreaCompare.getText()+"Tamaño de bytes de Archivo 1: "+bytesTextArea1.length+"\n"
						+"Tamaño de bytes de Archivo 2: "+bytesTextArea2.length+"\n"+"Archivo 1 es más grande que Archivo 2");
			}else if(bytesTextArea1.length < bytesTextArea2.length) {
				textAreaCompare.setText(textAreaCompare.getText()+"Tamaño de bytes de Archivo 1: "+bytesTextArea1.length+"\n"
						+"Tamaño de bytes de Archivo 2: "+bytesTextArea2.length+"\n"+"Archivo 2 es más grande que Archivo 1");
			}else {
				textAreaCompare.setText(textAreaCompare.getText()+"Tamaño de bytes de Archivo 1: "+bytesTextArea1.length+"\n"
						+"Tamaño de bytes de Archivo 2: "+bytesTextArea2.length+"\n"+"Tienen el mismo tamaño ambos archivos");
			}

		}

		if(e.getSource()==exitDoc  || e.getSource()==exitDocButton) {
			System.exit(0);
		}

	}

	/**
	 * Estos métodos los he puesto porque sino resulta en error el programa
	 * (aunque estén puestos ya dentro de una instancia de DocumentListener)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
