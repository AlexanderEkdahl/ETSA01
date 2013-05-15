import java.util.Properties;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

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
	Bicycle pendingBicycle;
	int garageN;

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
	public String addUser(String name, String id, boolean pro) {
		User u = new User(name, id, pro, generatePincode());
		users.put(id, u);
		usersPin.put(u.getPin(), u);
		return u.getPin();
	}

	String generatePincode() {
		String pin;

		do {
			pin = Integer.toString((int)(1000 + (int)(Math.random() * ((9999 - 1000) + 1))));
		} while (usersPin.containsKey(pin));

		return pin;
	}

	/** Remove a user from the system
	 *
	 * @param id     Unique id of the user
	 */
	public boolean removeUser(String id) {
		User u = users.get(id);

		if (u != null) {
			for (Bicycle bike : u.getBicycles()) {
				if (!removeBicycle(bike.getId())) {
					return false;
				}
			}

			users.remove(id);
			usersPin.remove(u.getPin());

			return true;
		}

		return false;
	}

	/** Returns a list of the users with bikes in their garage
	 *
	 * @return       HashSet of all active users
	 */
	public HashSet<User> activeUsers() {
		HashSet<User> users = new HashSet<User>();

		for (User user : this.users.values()) {
			for (Bicycle bike : user.getBicycles()) {
				if (bike.isInGarage()) {
					users.add(user);
				}
			}
		}

		return users;
	}

	/** Returns a list of all the users
	 *
	 * @return       ArrayList of all active users
	 */
	public ArrayList<User> getUsers() {
		return new ArrayList<User>(this.users.values());
	}

	/** Returns the user associated with the bicycle
	 *
	 * @return       ArrayList of all active users
	 */
	public User getBicycleOwner(String id) {
		Bicycle bike = bikes.get(id);

		if (bike != null) {
			return bike.getUser();
		}

		return null;
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

		if (u != null) {
			Bicycle bike = new Bicycle(u);

			u.addBicycle(bike);
			bikes.put(bike.getId(), bike);
			printer.printBarcode(bike.getId());
		}
	}

	/** Remove a bicycle from a user
	 *
	 * @param id       Id of the user
	 * @return         Returns true if the bike was successfully removed
	 */
	public boolean removeBicycle(String id) {
		Bicycle bike = bikes.get(id);

		if (bike != null) {
			User u = bike.getUser();

			if (!bike.isInGarage()) {
				u.removeBicycle(bike);
				bikes.remove(id);
				return true;
			}
		}

		return false;
	}

	public void entryBarcode(String id) {
		if (garageN >= Integer.parseInt(prop.getProperty("garage_full", "10"))) {
			terminal.lightLED(PinCodeTerminal.RED_LED, 5);
			return;
		}

		Bicycle bike = bikes.get(id);
		if (bike != null && !bike.isInGarage()) {
			pendingBicycle = bike;
		} else {
			terminal.lightLED(PinCodeTerminal.RED_LED, 5);
		}
	}

	public void exitBarcode(String id) {
		Bicycle bike = bikes.get(id);
		if (bike != null) {
			bike.setGarageStatus(false);
			garageN--;
		}
		exitLock.open(10);
	}

	public void entryCharacter(char c) {
		pinInput += c;
		if (pinInput.length() > 3) {
			if (pendingBicycle != null && pendingBicycle.getUser().getPin().equals(pinInput)) {
				pendingBicycle.setGarageStatus(true);
				garageN++;
				terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
				entryLock.open(10);
				pendingBicycle = null;
			} else if (usersPin.containsKey(pinInput)) {
				terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
				entryLock.open(10);
			} else {
				terminal.lightLED(PinCodeTerminal.RED_LED, 2);
			}
			pinInput = "";
		}
	}

	public void registerHardwareDrivers(
			BarcodePrinter printer,
			ElectronicLock entryLock,
			ElectronicLock exitLock,
			PinCodeTerminal terminal) {
		this.printer   = printer;
		this.entryLock = entryLock;
		this.exitLock  = exitLock;
		this.terminal  = terminal;
	}
}
