import java.util.ArrayList;

public class User {
  String name;
  String id;
  boolean pro;
  String pin;
  ArrayList<Bicycle> bikes;

  /** Initializes a new instance of User
   *
   * A psedu-random pincode is generated for the user
   *
   * @param name    The users name
   * @param id      The unique identity of the user
   * @param pro     True if the user is a paying customer with increased privileges
   * @return
   */
  public User(String name, String id, boolean pro) {
    this.name = name;
    this.id   = id;
    this.pro  = pro;
    this.pin  = Integer.toString((int)(1000 + (int)(Math.random() * ((9999 - 1000) + 1))));
    bikes     = new ArrayList<Bicycle>();
  }

  /** Returns the name of the user
   *
   * @return      User name
   */
  public String getName() {
    return name;
  }

  /** Returns the unique identity of the user
   *
   * @return      User id
   */
  public String getId() {
    return id;
  }

  /* Returns the pin of the user
   *
   * @return      User pin
  */
  public String getPin() {
    return pin;
  }

  /** Returns the pro status of the bike
   *
   * @return      Pro status
   */
  public boolean getStatus() {
    return pro;
  }

  /** Returns a list of bicycles assigned to the user
   *
   * @return      User bicycles
   */
  public ArrayList<Bicycle> getBicycles() {
    return bikes;
  }

  /** Assign a bicycle to a user
   *
   * @param bike  The bicycle that should be assigned to the user
   */
  public void addBicycle(Bicycle bike) {
    bikes.add(bike);
  }

  /** Upgrades a user to pro status
   *
   */
  public void upgradeUser() {
    pro = true;
  }

  /** Downgrade a user to normal status
   *
   */
  public void downgradeUser() {
    pro = false;
  }
}
