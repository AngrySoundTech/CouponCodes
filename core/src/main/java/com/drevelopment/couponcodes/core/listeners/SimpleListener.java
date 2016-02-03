package com.drevelopment.couponcodes.core.listeners;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.Command;
import com.drevelopment.couponcodes.api.entity.Player;

public abstract class SimpleListener {

    protected boolean handleCommandEvent(Command.Sender type, Player sender, String message) {
        message = trimCommand(message);
        int indexOfSpace = message.indexOf(' ');

        if (indexOfSpace != -1) {
            String command = message.substring(0, indexOfSpace);
            String args[] = message.substring(indexOfSpace + 1).split(" ");

            return CouponCodes.getCommandHandler().handleCommand(command, args, sender);
        } else {
            return CouponCodes.getCommandHandler().handleCommand(message, sender);
        }
    }

    private String trimCommand(String command) {
        if (command.startsWith("/")) {
            if (command.length() == 1) {
                return "";
            } else {
                command = command.substring(1);
            }
        }
        return command.trim();
    }
}
