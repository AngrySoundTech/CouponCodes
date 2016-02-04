package com.drevelopment.couponcodes.core.commands;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;

public abstract class ServerSender implements CommandSender {

    @Override
    public String getLocale() {
        return CouponCodes.getConfigHandler().getLocale();
    }

    @Override
    public boolean hasPermission(String node) {
        return true;
    }
}
