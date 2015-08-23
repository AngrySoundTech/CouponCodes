/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
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

import java.util.ArrayList;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class RemoveCommand implements Runnable {

    private CommandSender sender;
    private String[] args;

    public RemoveCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void run() {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("all")) {
                int j = 0;
                ArrayList<String> cs = CouponCodes.getCouponHandler().getCoupons();
                for (String i : cs) {
                    CouponCodes.getCouponHandler().removeCouponFromDatabase(i);
                    j++;
                }
                sender.sendMessage(LocaleHandler.getString("Command.Remove.AllRemoved", j));
                return;
            }
            if (!CouponCodes.getCouponHandler().couponExists(args[1])) {
                sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"));
                return;
            }
            CouponCodes.getCouponHandler().removeCouponFromDatabase(CouponCodes.getCouponHandler().createNewItemCoupon(args[1], 0, -1, null, null));
            sender.sendMessage(LocaleHandler.getString("Command.Remove.Removed", args[1]));
            return;
        } else {
            sender.sendMessage(LocaleHandler.getString("Command.Help.Remove"));
            return;
        }
    }
}
