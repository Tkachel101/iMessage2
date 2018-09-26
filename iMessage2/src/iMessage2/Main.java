package iMessage2;

import java.util.ArrayList;

public class Main {
	public static ArrayList<Conversation> conversations;
	public static MMServer server;
	public static GUI gui;
	public static FileHandler log;

	public static void main(String args[]) {
		conversations = new ArrayList<>();
		log = new FileHandler("iMessageLog.txt");
		String[] file = log.readFile().split("\n");
		int i = 0;
		for (String s : file) {
			if (i == 0) {
				i++;
				continue;
			}
			if (s.startsWith("-")) {
				String[] line = s.split(" ");
				if (line.length == 4) {
					conversations.add(new Conversation(new Contact(line[0].substring(1) + " " + line[1], line[2],
							Contact.Carrier.getCarrier(line[3]))));
				}else if(line.length == 3){
					conversations.add(new Conversation(new Contact(line[0].substring(1), line[1],
							Contact.Carrier.getCarrier(line[2]))));
				}
			} else if (i > 0)
				conversations.get(conversations.size() - 1).addComm(s);
			i++;
		}
		gui = new GUI();
		server = new MMServer(file[0].split(" ")[0], file[0].split(" ")[1]);
		server.run();
	}
}
