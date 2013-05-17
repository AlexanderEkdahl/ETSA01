import java.io.Serializable;

public class Bicycle implements Serializable {
	String id;
	User user;
	boolean status;

	/** Initializes a new instance of Bicycle
	 *
	 * @param user  Owner of the bike
   * @param id    Unique id of the bike
	 * @return      Bicycle instance
	 */
	public Bicycle(User user, String id) {
		this.user = user;
    this.id   = id;
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
   * @return    String holding id, owner name and status
   */
  public String toString() {
    return "Cykel ID: " + id + " Ägare: " + user.getName() + " Garage status: " + String.valueOf(status);
  }
}
