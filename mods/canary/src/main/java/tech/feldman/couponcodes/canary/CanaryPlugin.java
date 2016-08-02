/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.couponcodes.canary;

import java.io.IOException;

import tech.feldman.couponcodes.api.CouponCodes;
import tech.feldman.couponcodes.canary.config.CanaryConfigHandler;
import tech.feldman.couponcodes.canary.coupon.CanaryCouponHandler;
import tech.feldman.couponcodes.canary.coupon.CanaryCouponTimer;
import tech.feldman.couponcodes.canary.database.CanaryDatabaseHandler;
import tech.feldman.couponcodes.canary.listeners.CanaryListener;
import tech.feldman.couponcodes.canary.metrics.CustomDataSender;
import tech.feldman.couponcodes.canary.metrics.Metrics;
import tech.feldman.couponcodes.canary.permission.CanaryPermissionHandler;
import tech.feldman.couponcodes.core.commands.SimpleCommandHandler;
import tech.feldman.couponcodes.core.event.SimpleEventHandler;

import net.canarymod.Canary;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.plugin.Plugin;

public class CanaryPlugin extends Plugin {

    @Override
    public boolean enable() {

        CouponCodes.setEventHandler(new SimpleEventHandler());
        CouponCodes.setCommandHandler(new SimpleCommandHandler());
        CouponCodes.setModTransformer(new CanaryModTransformer(this));
        CouponCodes.setConfigHandler(new CanaryConfigHandler(this));

        CouponCodes.setDatabaseHandler(new CanaryDatabaseHandler());
        CouponCodes.setCouponHandler(new CanaryCouponHandler());

        CouponCodes.setPermissionHandler(new CanaryPermissionHandler());

        if (CouponCodes.getConfigHandler().getUseThread()) {
            Canary.getServer().addSynchronousTask(new CanaryCouponTimer(this, 200L));
        }

        if (CouponCodes.getConfigHandler().getUseMetrics()) {
            try {
                Metrics metrics = new Metrics(this);
                CouponCodes.getModTransformer().scheduleRunnable(new CustomDataSender(metrics));
                metrics.start();
            } catch (IOException ignored) {
            }
        }

        try {
            Canary.commands().registerCommands(new CanaryListener(), this, false);
        } catch (CommandDependencyException e) {
            return false;
        }

        return true;
    }

    @Override
    public void disable() {
        CouponCodes.setModTransformer(null);
    }

}
