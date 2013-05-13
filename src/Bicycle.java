public class Bicycle {
 	static int idCounter = 0;

	String id;
	User user;

	/** Initializes a new instance of Bicycle
	 *
	 * @param user  Owner of the bike
	 * @return      Bicycle instance
	 */
	public Bicycle(User user) {
		this.user = user;
		id        = Integer.toString(++idCounter);
	}

	/** Returns the id of the bike(barcode)
	 *
	 * @return      Bicycle id
	 */
	public String getId() {
		return id;
	}

	/** Returns the owner of the bike
	 *
	 * @return      Bicycle owner
	 */
	public User getUser() {
		return user;
	}
}
