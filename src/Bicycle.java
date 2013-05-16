public class Bicycle {
 	static int idCounter = 0;

	String id;
	User user;
	boolean status;

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

	/** Returns whether or not the bike is in the garage
  *
  * @return      Bicycle garage status
  */
  public boolean isInGarage() {
  	return status;
  }

	/** Changes the bicycle garage status to status
  *
  * @param status  Bicycle garage status
  */
  public void setGarageStatus(boolean status) {
  	this.status = status;
  }

	/** Returns the owner of the bike
	 *
	 * @return      Bicycle owner
	 */
	public User getUser() {
		return user;
	}

  /** Returns a human friendly representation of the Bicycle object
   *
   * @return    String holding id, owner name,
   */
  // public String toString() {
  //   return "Namn: " + name + " Personummer: " + id + " Pinkod: " + pin + " Betalande: " + String.valueOf(pro);
  // }
}
