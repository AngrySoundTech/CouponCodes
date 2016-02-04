package com.drevelopment.couponcodes.canary.entity;

import com.drevelopment.couponcodes.core.commands.ServerSender;
import net.canarymod.Canary;
import net.canarymod.chat.MessageReceiver;

public class CanaryServerSender extends ServerSender {

    MessageReceiver canaryCaller;

    public CanaryServerSender(MessageReceiver caller) {
        this.canaryCaller = caller;
    }

    @Override
    public void sendMessage(String message) {
        canaryCaller.message(message);
    }
}
