package effyis.partners.socle.exception;

import java.util.NoSuchElementException;

public class ElementNotFoundException extends NoSuchElementException {

    private static final long serialVersionUID = 1L;
    private final String name;

    public ElementNotFoundException(String name) {
        super("Element : "+ name + " not found");
        this.name = name;
    }

    public String getName() {
        return name;
    }

	
}
