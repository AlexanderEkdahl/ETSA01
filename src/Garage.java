/**
 * Main class responsible for gluing all the other classes together.
 *
 * @author Alexander Ekdahl
 */
public class Garage {
	public Garage(String config, String database) {
		Manager manager           = new Manager();
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

	public static void main(String[] args) {
		new Garage("config", "database");
	}
}
