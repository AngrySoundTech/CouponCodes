package com.github.drepic26.couponcodes.api.command;

public class CommandException extends Exception{
private static final long serialVersionUID = 2779430069824569910L;

	public CommandException() {
		super();
	}

	public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

}
