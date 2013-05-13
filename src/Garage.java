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

		System.out.println("Cykelgarage version 0.1 av: \n"
										 + "Alexander Ekdahl + Co");
	}

	void run() {
		scan = new Scanner(System.in);

		while (true) {
			System.out.println("\n------------------------------------------\n"
											 + "1. Lägg till en ny användare\n"
											 + "2. Lägg till en ny cykel\n"
											 + "3. Uppgradera användare till betalande\n"
											 + "4. Nedgradera användare till icke betalande");

			switch (Integer.parseInt(scan.next())) {
				case 1: {
					User u = manager.addUser(prompt("Namn"), prompt("Personnummer"), prompt_yesno("Betalande användare?"));
					System.out.print("Användare tillagd. Pinkod: " + u.getPin());
					break;
				}
				case 2: {
					manager.addBicycle(prompt("Ägarens personnummer"));
					System.out.print("Cykel tillagd.");
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
