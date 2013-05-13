import java.util.Properties;
import java.util.HashMap;

public class Manager {
	BarcodePrinter printer;
	ElectronicLock entryLock;
	ElectronicLock exitLock;
	PinCodeTerminal terminal;
	Properties prop;

	HashMap<String, User> users;
	HashMap<String, User> usersPin;
	HashMap<String, Bicycle> bikes;
	String pinInput;

	/** Initializes a new instance of Manager
	 *
	 * @param prop        Properties object holding the configurable variables
	 * @return            BicycleManager instance
	 */
	public Manager(Properties prop) {
		this.prop = prop;

		users    = new HashMap<String, User>();
		usersPin = new HashMap<String, User>();
		bikes    = new HashMap<String, Bicycle>();
		pinInput = "";
	}

	/** Adds a new user to the system
	 *
	 * @param name   The full name of the user
	 * @param id     Unique id of the user
	 * @param pro    The pro status of the user
	 */
	public User addUser(String name, String id, boolean pro) {
		User u = new User(name, id, pro);
		users.put(id, u);
		usersPin.put(u.getPin(), u);
		return u;
	}

	/** Upgrades a user to pro status
	 *
	 * @param id     Id of the user
	 */
	public void upgradeUser(String id) {
		User u = users.get(id);
		u.upgradeUser();
	}

	/** Downgrade a user to normal status
	 *
	 * @param id     Id of the user
	 */
	public void downgradeUser(String id) {
		User u = users.get(id);
		u.downgradeUser();
	}

	/** Add a bicycle to a user
	 *
	 * @param id       Id of the user
	 */
	public void addBicycle(String id) {
		User u       = users.get(id);
		Bicycle bike = new Bicycle(u);

		u.addBicycle(bike);
		bikes.put(bike.getId(), bike);
		printer.printBarcode(bike.getId());
	}

	public void entryBarcode(String id) {
		if (bikes.get(id) != null) {
			entryLock.open(10);
		}
	}

	public void exitBarcode(String id) {
		exitLock.open(10);
	}

	public void entryCharacter(char c) {
		pinInput += c;
		if (pinInput.length() > 3) {
			if (usersPin.containsKey(pinInput)) {
				terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
				entryLock.open(10);
			} else {
				terminal.lightLED(PinCodeTerminal.RED_LED, 2);
			}
		}
	}

	public void registerHardwareDrivers(
			BarcodePrinter printer,
			ElectronicLock entryLock,
			ElectronicLock exitLock,
			PinCodeTerminal terminal) {
		this.printer = printer;
		this.entryLock = entryLock;
		this.exitLock = exitLock;
		this.terminal = terminal;
	}
}
