package iMessage2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MMServer {
	private Date sentDate;
	private Properties props;
	private Session session;
	private Folder inbox;
	private Store store;
	private String from;
	private volatile String message;

	public MMServer(final String username, final String password) {
		try {
			props = new Properties();
			props.setProperty("mail.store.protocol", "imaps");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			store = session.getStore();
			store.connect("imap.gmail.com", username, password);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			sentDate = sentDate();
			from = "41494322605@mms.att.net";
			message = "";
		} catch (Exception e) {

		}
	}
	
	public MMServer(final String[] loginData) {
		try {
			props = new Properties();
			props.setProperty("mail.store.protocol", "imaps");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(loginData[0], loginData[1]);
				}
			});
			store = session.getStore();
			store.connect("imap.gmail.com", loginData[0], loginData[1]);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			sentDate = sentDate();
			from = "";
			message = "";
		} catch (Exception e) {

		}
	}

	public void run() {
		while (true) {
			if (newMail()) {
				message = readText();
				for (Conversation c : Main.conversations) {
					if (from.equals(c.getContact().getContact())) {
						c.addIncomingComm(getMessageWithEmoji(message));
					}
				}
			}
		}
	}

	public String getMessageWithEmoji(String message) {
		String total = "";
		for (int i = 0; i < message.length(); i++) {
			if (i + 12 <= message.length()) {
				String sub = message.substring(i, i + 12);
				if (sub.charAt(0) == '=' && sub.charAt(3) == '=' && sub.charAt(6) == '=' && sub.charAt(9) == '=') {
					sub = sub.replaceFirst("=", "");
					String[] strNumbers = sub.split("=");
					byte[] rawChars = new byte[strNumbers.length];
					for (int j = 0; j < strNumbers.length; j++) {
						//System.out.println(sub + " " + strNumbers[j]);
						rawChars[j] = (byte) (int) Integer.valueOf(strNumbers[j], 16);
					}
					total += new String(rawChars, Charset.forName("UTF-8"));
					i += 11;
				} else {
					total += message.charAt(i);
				}
			} else
				total += message.charAt(i);
			// System.out.println(total);
		}
		return total;
	}

	public boolean newMail() {
		try {
			return !sentDate().equals(sentDate);
		} catch (NullPointerException e) {
			return false;
		}
	}

	private<T> T inLine(T t){
		System.out.println(t);
		return t;
	}
	
	public String getMessage() {
		return message;

	}

	private Date sentDate() {
		try {
			try {
				try {
					Message msg = inbox.getMessage(inbox.getMessageCount());
					return msg.getSentDate();
				} catch (NullPointerException e) {
					//System.err.println("No Internet Connection");
					// System.exit(1);
					return null;
				}
			} catch (FolderClosedException mex) {
				System.err.println("Folder Closed Exception");
				inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
				return sentDate;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String readText() {
		String total = "";
		try {
			Message msg = inbox.getMessage(inbox.getMessageCount());
			sentDate = (msg.getSentDate());
			Address[] in = msg.getFrom();
			for (Address address : in) {
				from = address.toString();
			}
			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			bp.getContent();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				msg.writeTo(out);
			} catch (IOException e) {
			}
			String s = out.toString();
			s = s.substring(s.indexOf("<td>") + 4, s.indexOf("</td>"));
			String[] arr = s.split(" ");
			for (String str : arr) {
				if (!str.equals(""))
					total += str + " ";
			}
			total = total.replace("\n", "").trim();
			// System.out.print(total + " : ");
			return total;
		} catch (Exception mex) {
		}
		return total;
	}

	public String sendSMS(String aMessage) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Unknown", "The Machine"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(from));
			message.setText(aMessage);
			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
		}
		return aMessage;
	}

	public String sendSMS(String aMessage, String recipient) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Unknown", "The Machine"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setText(aMessage);
			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
		}
		return aMessage;
	}
}
