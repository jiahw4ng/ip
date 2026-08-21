public enum Command {
  LIST("list"),
  MARK("mark"),
  UNMARK("unmark"),
  DELETE("delete"),
  TODO("todo"),
  DEADLINE("deadline"),
  EVENT("event"),
  BYE("bye");

  private final String keyword;

  Command(String keyword) {
    this.keyword = keyword;
  }

  public static Command from(String input) {
    String commandWord = input.trim().split("\\s+", 2)[0];
    for (Command type : values()) {
      if (type.keyword.equals(commandWord)) {
        return type;
      }
    }
    throw new IllegalArgumentException("I'm sorry, I don't know what that means.");
  }

}
