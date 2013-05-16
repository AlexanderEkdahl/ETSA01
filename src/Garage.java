import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Main class responsible for gluing all the other classes together.
 *
 * @author Alexander Ekdahl
 */
public class Garage {
	Manager manager;
	Scanner scan;

	Garage(String config) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(config));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		manager                   = new Manager(prop);
		ElectronicLock entryLock  = new ElectronicLockTestDriver("Entrance");
		ElectronicLock exitLock   = new ElectronicLockTestDriver("Exit");
		BarcodePrinter printer    = new BarcodePrinterTestDriver();
		PinCodeTerminal terminal  = new PinCodeTerminalTestDriver();
		BarcodeReader readerEntry = new BarcodeReaderEntryTestDriver();
		BarcodeReader readerExit  = new BarcodeReaderExitTestDriver();

		manager.registerHardwareDrivers(printer, entryLock, exitLock, terminal);
		terminal.register(manager);
		readerEntry.register(manager);
		readerExit.register(manager);
	}

	void run() {
		scan = new Scanner(System.in);

		while (true) {
			System.out.println("\n------------------------------------------\n"
											 + "1. Lägg till en ny användare\n"
											 + "2. Lägg till en ny cykel\n"
											 + "3. Uppgradera användare till betalande\n"
											 + "4. Nedgradera användare till icke betalande\n"
											 + "5. Ta bort cykel\n"
											 + "6. Ta bort användare\n"
											 + "7. Lista aktiva användare\n"
											 + "8. Lista alla användare\n");

			switch (Integer.parseInt(scan.next())) {
				case 1: {
					User user = manager.addUser(prompt("Förnamn"), prompt("Efternamn"), prompt("Personnummer"), prompt_yesno("Betalande användare?"));
					if (user != null) {
						System.out.print("Användare tillagd. Pinkod: " + user.getPin());
					} else {
						System.out.print("Användare med det personnumret finns redan");
					}
					break;
				}
				case 2: {
					if (manager.addBicycle(prompt("Ägarens personnummer"))) {
						System.out.print("Cykel tillagd.");
					} else {
						System.out.print("Användaren finns inte");
					}
					break;
				}
				case 3: {
					manager.upgradeUser(prompt("Personnummer"));
					System.out.print("Användare uppgraderad.");
					break;
				}
				case 4: {
					manager.downgradeUser(prompt("Personnummer"));
					System.out.print("Användare nedgraderad.");
					break;
				}
				case 5: {
					if (manager.removeBicycle(prompt("Cyckel ID"))) {
						System.out.print("Cykel bortagen");
					} else {
						System.out.print("Cykeln finns inte i systemet eller redan parkerad");
					}
					break;
				}
				case 6: {
					if (manager.removeUser(prompt("Personnummer"))) {
						System.out.print("Användare bortagen");
					} else {
						System.out.print("Användaren finns inte eller har fortfarande cyklar registrerade. Ta bort cyklarna först.");
					}
					break;
				}
				case 7: {
					for (User user : manager.activeUsers()) {
						System.out.println(user.toString());
						for (Bicycle bike : user.getBicycles()) {
							System.out.println("\t" + bike.toString());
						}
					}
					break;
				}
				case 8: {
					for (User user : manager.getUsers()) {
						System.out.println(user.toString());
						for (Bicycle bike : user.getBicycles()) {
							System.out.println("\t" + bike.toString());
						}
					}
					break;
				}
				case 9: {
					User user = manager.getBicycleOwner(prompt("Cykel ID"));
					if (user != null) {
						System.out.println(user.toString());
					}
					break;
				}
			}
		}
	}

	void list(Iterator<User> users) {
		for (User user : users) {
			System.out.println(user.toString());
			for (Bicycle bike : user.getBicycles()) {
				System.out.println("\t" + bike.toString());
			}
		}
	}

	String prompt(String out) {
		System.out.print(out + ": ");
		return scan.next();
	}

	boolean prompt_yesno(String out) {
		System.out.print(out + "(y/n): ");
		return scan.next().equals("y");
	}

	public static void main(String[] args) {
		new Garage("config.properties").run();
	}
}
