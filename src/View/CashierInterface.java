package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import controller.SocketServer;

import com.example.ramen.menu.Model.Order;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 *
 * @author Ramen
 */
public class CashierInterface extends javax.swing.JFrame {

	public SocketServer server;
	Socket socket;
	public Thread serverThread;
	public Map<Integer, List<Order>> billingList = new HashMap<Integer, List<Order>>();

	public CashierInterface() {
		initComponents();
		server = new SocketServer(this);

	}

	public void loadBillOf(int tableNo) {
		DefaultTableModel model = (DefaultTableModel) TotalTable.getModel();
		if (billingList.containsKey(tableNo)) {
			while (model.getRowCount() != 0) {
				model.removeRow(model.getRowCount() - 1);
			}

			List<Order> bill = billingList.get(tableNo);
			for (Order orderRow : bill) {
				addToOrderList(orderRow);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		TableDropBox = new javax.swing.JComboBox<>();
		TableDropBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println((String) e.getItem());
				String tableStr = e.getItem().toString();
				String table[];
				table = tableStr.split(" ");
				System.out.println(Integer.parseInt(table[1]));
				loadBillOf(Integer.parseInt(table[1]));
			}
		});
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		TotalTable = new javax.swing.JTable();
		jTextField1 = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();
		CalculateTotalButton = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		updateArea = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		TableDropBox.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Table 1", "Table 2", "Table 3", "Table 4" }));

		jLabel1.setText("CASHIER VIEW");

		TotalTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] { "Dish Name", "Quantity",
						"Unit Price", "Amount" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.Integer.class, java.lang.Double.class,
					java.lang.Double.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane1.setViewportView(TotalTable);

		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		jLabel2.setText("Cash Recieved: ");

		jButton1.setText("Recieve");

		jLabel3.setText("Cash Back:");

		jLabel4.setText("---");

		jButton2.setText("Paid");

		CalculateTotalButton.setText("Calculate Total");

		updateArea.setColumns(20);
		updateArea.setRows(5);
		jScrollPane2.setViewportView(updateArea);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						TableDropBox,
																						GroupLayout.PREFERRED_SIZE,
																						155,
																						GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												Alignment.TRAILING)
																												.addComponent(
																														jLabel1,
																														Alignment.LEADING)
																												.addComponent(
																														jScrollPane1,
																														GroupLayout.PREFERRED_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.PREFERRED_SIZE))
																								.addGap(18)
																								.addGroup(
																										layout.createParallelGroup(
																												Alignment.LEADING)
																												.addComponent(
																														jScrollPane2,
																														GroupLayout.PREFERRED_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														jButton1)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGroup(
																																		layout.createParallelGroup(
																																				Alignment.LEADING)
																																				.addComponent(
																																						jLabel2)
																																				.addComponent(
																																						jLabel3))
																																.addGap(18)
																																.addGroup(
																																		layout.createParallelGroup(
																																				Alignment.LEADING)
																																				.addComponent(
																																						jLabel4)
																																				.addComponent(
																																						jTextField1,
																																						GroupLayout.PREFERRED_SIZE,
																																						146,
																																						GroupLayout.PREFERRED_SIZE)))
																												.addComponent(
																														jButton2)))))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(172)
																.addComponent(
																		CalculateTotalButton)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(19)
								.addComponent(jLabel1)
								.addGap(26)
								.addComponent(TableDropBox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						jLabel2)
																				.addComponent(
																						jTextField1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(18)
																.addComponent(
																		jButton1)
																.addGap(18)
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						jLabel3)
																				.addComponent(
																						jLabel4))
																.addGap(18)
																.addComponent(
																		jButton2)
																.addGap(30)
																.addComponent(
																		jScrollPane2,
																		GroupLayout.PREFERRED_SIZE,
																		263,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jScrollPane1,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(CalculateTotalButton)
								.addContainerGap(83, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		pack();
	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void StartServerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		server = new SocketServer(this);
	}

	public void addToOrderList(Order ord) {
		DefaultTableModel model = (DefaultTableModel) TotalTable.getModel();
		model.addRow(new Object[] { ord.getDishName(), ord.getQuantity(),
				ord.getUnitPrice(), (ord.getUnitPrice() * ord.getQuantity()) });
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton CalculateTotalButton;
	private javax.swing.JComboBox<String> TableDropBox;
	public javax.swing.JTable TotalTable;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	public javax.swing.JTextArea updateArea;
	private javax.swing.JTextField jTextField1;

	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger
					.getLogger(CashierInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger
					.getLogger(CashierInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger
					.getLogger(CashierInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger
					.getLogger(CashierInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CashierInterface().setVisible(true);

			}
		});
	}

	public void sendToKitchen(Order order){
		int clientNo = server.find("Kitchen");
		if(clientNo!=-1){
				server.clients[clientNo].send(order);
		}
	}
	public void RetryStart(int port) {
		if (server != null) {
			server.stop();
		}
		server = new SocketServer(this, port);
	}

}
