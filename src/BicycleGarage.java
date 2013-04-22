/**
 * Main class responsible for gluing all the other classes together.
 *
 * @author Alexander Ekdahl
 */
public class BicycleGarage {
	public BicycleGarage() {
		// BicycleGarageManager manager = new BicycleGarageManager();
		ElectronicLock entryLock     = new ElectronicLockTestDriver("Entrance");
		ElectronicLock exitLock      = new ElectronicLockTestDriver("Exit");
		// BarcodePrinter printer       = new BarcodePrinterTestDriver();
		// PinCodeTerminal terminal     = new PinCodeTerminalTestDriver();
		// BarcodeReader readerEntry    = new BarcodeReaderEntryTestDriver();
		// BarcodeReader readerExit     = new BarcodeReaderExitTestDriver();

		// manager.registerHardwareDrivers(printer, entryLock, exitLock, terminal);
		// terminal.register(manager);
		// readerEntry.register(manager);
		// readerExit.register(manager);
	}

	public static void main(String[] args) {
		new BicycleGarage();
	}
}
