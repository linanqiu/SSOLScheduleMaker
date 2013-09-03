package columbia;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class CalendarMakerGUI {

	private JFrame frmSsolIcalEvent;
	private JTextArea txtrSsolClipboard;
	private JButton btnGenerateEvents;
	private JTextField txtStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarMakerGUI window = new CalendarMakerGUI();
					window.frmSsolIcalEvent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalendarMakerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSsolIcalEvent = new JFrame();
		frmSsolIcalEvent.setTitle("SSOL iCal Event Maker");
		frmSsolIcalEvent.setBounds(100, 100, 450, 300);
		frmSsolIcalEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		frmSsolIcalEvent.getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmSsolIcalEvent.getContentPane().add(scrollPane, gbc_scrollPane);

		txtrSsolClipboard = new JTextArea();
		txtrSsolClipboard.setText("SSOL Clipboard");
		scrollPane.setViewportView(txtrSsolClipboard);

		txtStatus = new JTextField();
		txtStatus.setText("Status");
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.gridx = 1;
		gbc_txtStatus.gridy = 0;
		frmSsolIcalEvent.getContentPane().add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);

		btnGenerateEvents = new JButton("Generate Events");
		GridBagConstraints gbc_btnGenerateEvents = new GridBagConstraints();
		gbc_btnGenerateEvents.gridx = 1;
		gbc_btnGenerateEvents.gridy = 1;
		frmSsolIcalEvent.getContentPane().add(btnGenerateEvents, gbc_btnGenerateEvents);

		txtStatus.setText("");

		btnGenerateEvents.addActionListener(new EventListener());
		
		txtrSsolClipboard.addMouseListener(new ClearListener());
	}

	public class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			EventWorker eventWorker = new EventWorker();
			eventWorker.execute();
		}

	}

	public class ClearListener implements MouseListener {

		public boolean changed;

		public ClearListener() {
			changed = false;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!changed) {
				JTextArea textArea = (JTextArea) e.getSource();
				textArea.setText("");
				changed = true;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class EventWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			SSOLParser parser = new SSOLParser(txtrSsolClipboard.getText());
			parser.parse();

			CalendarMaker maker = new CalendarMaker();
			maker.makeFiles(parser.getSections());
			return null;
		}

		protected void done() {
			txtStatus.setText("Done");
		}
	}

	public JTextField getTxtStatus() {
		return txtStatus;
	}
}
