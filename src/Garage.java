import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.logging.*;

/**
 * Main class responsible for gluing all the other classes together.
 *
 * @author Alexander Ekdahl
 */
public class Garage {
	Manager manager;
	Scanner scan;

	Garage(String config) {
		Logger log = Logger.getLogger("Garage");
		LogManager.getLogManager().reset();

		try {
			log.addHandler(new FileHandler("test.log", true));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(config));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("database"));
			manager = (Manager)in.readObject();
		} catch (Exception FileNotFoundException) {
			manager = new Manager();
		}

		manager.setProperties(prop);

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
											 + "7. Lista cyklar i garaget\n"
											 + "8. Lista alla användare\n"
											 + "9. Kolla upp cykel\n"
											 + "10. Spara och stäng");

			switch (Integer.parseInt(scan.next())) {
				case 1: {
					User user = manager.addUser(prompt("Förnamn"), prompt("Efternamn"), prompt("Personnummer"), prompt_yesno("Betalande användare?"));
					if (user != null) {
						System.out.print("Användare tillagd. Pinkod: " + user.getPin());
						save();
					} else {
						System.out.print("Användare med det personnumret finns redan");
					}
					break;
				}
				case 2: {
					if (manager.addBicycle(prompt("Ägarens personnummer"))) {
						System.out.print("Cykel tillagd.");
						save();
					} else {
						System.out.print("Användaren finns inte");
					}
					break;
				}
				case 3: {
					manager.upgradeUser(prompt("Personnummer"));
					System.out.print("Användare uppgraderad.");
					save();
					break;
				}
				case 4: {
					manager.downgradeUser(prompt("Personnummer"));
					System.out.print("Användare nedgraderad.");
					save();
					break;
				}
				case 5: {
					if (manager.removeBicycle(prompt("Cyckel ID"))) {
						System.out.print("Cykel bortagen");
						save();
					} else {
						System.out.print("Cykeln finns inte i systemet eller redan parkerad");
					}
					break;
				}
				case 6: {
					if (manager.removeUser(prompt("Personnummer"))) {
						System.out.print("Användare bortagen");
						save();
					} else {
						System.out.print("Användaren finns inte eller har fortfarande cyklar registrerade. Ta bort cyklarna först.");
					}
					break;
				}
				case 7: {
					for (User user : manager.getUsers()) {
						for (Bicycle bike : user.getBicyclesInGarage()) {
							System.out.println(bike.toString());
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
				case 10: {
					save();
					System.exit(0);
					break;
				}
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

	void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("database"));
			out.writeObject(manager);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		new Garage("config.properties").run();
	}
}
