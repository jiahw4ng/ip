/** A task occurring between specified start and end dates or times. */
public class Event extends Task {
  protected final String from;
  protected final String to;

  public Event(String desc, String from, String to) {
    super(desc);
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
  }

}
