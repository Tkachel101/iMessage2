package iMessage2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Conversation extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contact recipient;
	private ArrayList<String> comms;
	private boolean focused = false;

	public Conversation(Contact recipient) {
		this.recipient = recipient;
		comms = new ArrayList<>();
		this.setText(recipient.getName());
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Conversation.this.setForeground(Color.BLUE);
				Main.gui.changeConversation(Conversation.this);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Conversation.this.setForeground(Color.BLUE);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!focused)
					Conversation.this.setForeground(Color.BLACK);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void addOutgoingComm(String comm) {
		comms.add("YOU : " + comm);
		// Main.gui.updateConvo(recipient.getName() + " : " + comm);
	}

	public void addIncomingComm(String comm) {
		comms.add(recipient.getName() + " : " + comm);
		Main.gui.updateConvo(recipient.getName() + " : " + comm);
	}

	public void addComm(String comm){
		comms.add(comm);
	}
	
	public ArrayList<String> getComms() {
		return comms;
	}

	public void setFocus(boolean state) {
		this.focused = state;
		if (state)
			Conversation.this.setForeground(Color.BLUE);
		else
			Conversation.this.setForeground(Color.BLACK);
	}

	public Contact getContact() {
		return recipient;
	}

	public String getName() {
		return recipient.getName();
	}

}
