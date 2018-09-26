package iMessage2;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel jpanel;
	private JTextArea convo;
	private Conversation currentConvo;

	public GUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
		try{
		if (Main.conversations.get(0) != null) {
			this.currentConvo = Main.conversations.get(0);
			currentConvo.setFocus(true);
			this.setTitle("iMessage : " + currentConvo.getName());
		} else
			this.setTitle("iMessage");
		}catch (Exception e){
			this.setTitle("iMessage");
		}
		this.setResizable(false);
		this.setBounds(screenSize.width / 4, screenSize.height / 4, screenSize.width / 2, screenSize.height / 2);
		jpanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				this.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
				this.setBounds(0, 0, // screenSize.width / 4, screenSize.height
										// / 4,
						screenSize.width / 2, screenSize.height / 2);
				this.setLayout(null);
				this.add(new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					int thisWidth = screenSize.width / 2 / 3;
					int thisHeight = screenSize.height / 2;
					{
						this.setBounds(0, 0, thisWidth + 1, thisHeight);
						this.setPreferredSize(new Dimension(thisWidth, thisHeight));
						this.setLayout(new BoxLayout(this, 1));
						this.add(new JLabel("Add Contact") {
							{
								this.setCursor(new Cursor(Cursor.HAND_CURSOR));
								this.addMouseListener(new MouseListener() {

									@Override
									public void mouseClicked(MouseEvent arg0) {
										JFrame jframe = new JFrame();
										jframe.setBounds(screenSize.width / 4 + 50, screenSize.height / 4 + 50, 200,
												400);
										jframe.setResizable(false);
										jframe.setName("new contact");
										jframe.add(new JPanel() {
											{
												this.setLayout(new BoxLayout(this, 1));
												this.add(new JLabel("Name"));
												TextField name = new TextField();
												name.setPreferredSize(new Dimension(100, 50));
												this.add(name);
												this.add(new JLabel("Number"));
												TextField number = new TextField();
												number.setPreferredSize(new Dimension(100, 50));
												this.add(number);
												this.add(new JLabel("Carrier"));
												JComboBox<Object> carriers = new JComboBox<>(
														Contact.Carrier.getCarriersString().toArray());
												this.add(carriers);
												Button OK = new Button("OK");
												this.add(OK);
												OK.addActionListener(new ActionListener() {

													@Override
													public void actionPerformed(ActionEvent arg0) {
														Main.conversations.add(new Conversation(
																new Contact(name.getText(), number.getText(),
																		Contact.Carrier.getCarriers().get(
																				carriers.getSelectedIndex() - 1))));
														// for(Component c :
														// GUI.this.jpanel.getComponents()){
														// System.out.println(c.getName());
														// }

														((JPanel) GUI.this.jpanel.getComponent(0)).add(
																Main.conversations.get(Main.conversations.size() - 1));
														GUI.this.jpanel.getComponent(0).validate();
														currentConvo.setFocus(false);
														currentConvo = Main.conversations
																.get(Main.conversations.size() - 1);
														currentConvo.setFocus(true);
														GUI.this.setTitle(currentConvo.getName());
														GUI.this.changeConversation(currentConvo);
														jframe.dispose();
													}

												});

											}
										});
										jframe.setVisible(true);
									}

									@Override
									public void mouseEntered(MouseEvent arg0) {
										((Component) arg0.getSource()).setForeground(Color.BLUE);
									}

									@Override
									public void mouseExited(MouseEvent arg0) {
										((Component) arg0.getSource()).setForeground(Color.BLACK);
									}

									@Override
									public void mousePressed(MouseEvent arg0) {
										// TODO Auto-generated method stub

									}

									@Override
									public void mouseReleased(MouseEvent arg0) {
										// TODO Auto-generated method stub

									}

								});
							}
						});
						for (Conversation c : Main.conversations) {
							this.add(c);
						}
						// sp = new JScrollPane(this);
						// this.add(sp);
					}

					public void paint(Graphics g) {
						super.paint(g);
						Graphics2D g2d = (Graphics2D) g;
						g2d.drawLine(thisWidth, 0, thisWidth, thisHeight);
						g2d.drawLine(thisWidth - 1, 0, thisWidth - 1, thisHeight);
						g2d.drawLine(thisWidth - 2, 0, thisWidth - 2, thisHeight);
					}
				});

				this.add(new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					int thisWidth = screenSize.width - (screenSize.width / 2) - screenSize.width / 2 / 3;
					int thisHeight = screenSize.height / 2 - 200;
					{
						this.setBounds(screenSize.width / 2 / 3, 0, thisWidth, thisHeight);
						this.setPreferredSize(new Dimension(thisWidth, thisHeight));
						this.setLayout(new BoxLayout(this, 0));
						convo = new JTextArea();
						JScrollPane scroll = new JScrollPane(convo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						convo.setFont(convo.getFont().deriveFont(24f));
						convo.setLineWrap(true);
						convo.requestFocus();
						convo.setEditable(false);
						this.add(scroll);
					}

					public void paint(Graphics g) {
						super.paint(g);
						Graphics2D g2d = (Graphics2D) g;
						g2d.drawLine(0, thisHeight - 1, thisWidth, thisHeight - 1);
						g2d.drawLine(0, thisHeight - 2, thisWidth, thisHeight - 2);
					}
				});
				this.add(new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					int thisWidth = screenSize.width - (screenSize.width / 2) - screenSize.width / 2 / 3;
					int thisHeight = screenSize.height / 2 - (screenSize.height / 2 - 200);
					{
						this.setBounds(screenSize.width / 2 / 3, screenSize.height / 2 - 200, thisWidth, thisHeight);
						this.setPreferredSize(new Dimension(thisWidth, thisHeight));
						// this.setBackground(Color.RED);
						TextArea message = new TextArea();
						this.add(message);
						Button send = new Button("Send");
						send.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								String s = message.getText();
								message.setText("");
								updateConvo("YOU : " + s);
								currentConvo.addOutgoingComm(s);
								Main.server.sendSMS(s, currentConvo.getContact().getContact());
							}

						});
						this.add(send);
						JComboBox<Object> emoji = new JComboBox<>(Emoji.getEmojisString().toArray());
						emoji.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (emoji.getSelectedIndex() != 0)
									message.append(emoji.getSelectedItem().toString());
								emoji.setSelectedIndex(0);
							}

						});
						this.add(emoji);
					}
				});
			}
		};
		this.setContentPane(jpanel);
		this.pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// System.out.println("Shutting Down");
				String intro = Main.log.readFile().split("\n")[0];
				Main.log.clearFile();
				Main.log.appendFile(intro);
				for (Conversation c : Main.conversations) {
					Main.log.appendFile(
							"-" + c.getName() + " " + c.getContact().getNumber() + " " + c.getContact().getCarrier());
					for (String s : c.getComms()) {
						Main.log.appendFile(s);
					}
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		if (currentConvo != null)
			this.changeConversation(currentConvo);
		this.validate();
		this.setVisible(true);
	}

	public void updateConvo(String s) {
		convo.setText(convo.getText() + (convo.getText().isEmpty() ? "" : "\n") + s);
		convo.repaint();
	}

	public void changeConversation(Conversation c) {
		convo.setText("");
		if (currentConvo != null)
			currentConvo.setFocus(false);
		currentConvo = c;
		currentConvo.setFocus(true);
		this.setTitle("iMessage : " + currentConvo.getName());
		for (String s : c.getComms()) {
			updateConvo(s);
		}
	}
}
