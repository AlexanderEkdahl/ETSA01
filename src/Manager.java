public class Manager {
	BarcodePrinter printer;
	ElectronicLock entryLock;
	ElectronicLock exitLock;
	PinCodeTerminal terminal;

	// /** Initializes a new instance of Manager
	//  *
	//  * @param config        HashMap representing the available Manager configurations
	//  * @return              BicycleManager instance
	//  */
	// public Manager(Properties config);

	// /** Adds a new user to the system
	//  *
	//  * @param name   The full name of the user
	//  * @param id     Unique id of the user
	//  * @param pro    The pro status of the user
	//  */
	// public String addUser(String name, String id, boolean pro);

	// /** Upgrades a user to pro status
	//  *
	//  * @param id     Id of the user
	//  */
	// public void upgradeUser(String id);

	// /** Downgrade a user to normal status
	//  *
	//  * @param id     Id of the user
	//  */
	// public void downgradeUser(String id);

	// /** Add a bicycle to a user
	//  *
	//  * @param id       Id of the user
	//  * @param bicycle  Bicycle instance
	//  * @return         Returns unique bicycle id
	//  */
	// public String addBicycle(String id, Bicycle bicycle);

	// /** Returns a list of all users in the system
	//  *
	//  * @return             Returns an ArrayList of all the users
	//  */
	// public ArrayList<User> getAllUsers();

	// /** Finds a user by their unique id
	//  *
	//  * @param id       User id
	//  * @return         User
	//  */
	// public User getUserByID(String id);

	// /** Finds a user associated with a unique bicycle id
	//  *
	//  * @param id       Bicycle id
	//  * @return         User
	//  */
	// public User getUserByBicycle(String id);

	// /** Returns a list of all users with bicycles inside the garage
	//  *
	//  * @return         An ArrayList of all users
	//  */
	// public ArrayList<User> getIsInGarageList();

	public void entryBarcode(String bicycleID) {

	}

	public void exitBarcode(String bicycleID) {

	}

	public void entryCharacter(char c) {

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
