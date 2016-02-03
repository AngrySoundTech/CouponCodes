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
package com.drevelopment.couponcodes.core.commands.runnables;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class TimeCommand implements Runnable {

    private CommandSender sender;
    private String[] args;

    public TimeCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void run() {
        if (args.length == 3) {
            Coupon c = CouponCodes.getCouponHandler().getCoupon(args[1]);
            if (c != null) {
                try {
                    c.setTime(Integer.parseInt(args[2]));
                    c.updateWithDatabase();
                    sender.sendMessage(LocaleHandler.getString("Command.Time.Changed", c.getName(), args[2]));
                } catch (NumberFormatException e) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.SyntaxError"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"));
            }
        } else {
            sender.sendMessage(LocaleHandler.getString("Command.Help.Time"));
        }
    }

}
