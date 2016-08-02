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
package tech.feldman.couponcodes.core.commands.runnables;

import java.util.HashMap;

import tech.feldman.couponcodes.api.CouponCodes;
import tech.feldman.couponcodes.api.command.CommandSender;
import tech.feldman.couponcodes.api.coupon.CommandCoupon;
import tech.feldman.couponcodes.api.coupon.EconomyCoupon;
import tech.feldman.couponcodes.api.coupon.ItemCoupon;
import tech.feldman.couponcodes.api.coupon.RankCoupon;
import tech.feldman.couponcodes.api.coupon.XpCoupon;
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException;
import tech.feldman.couponcodes.core.util.LocaleHandler;
import tech.feldman.couponcodes.core.util.RandomName;

public class AddCommand implements Runnable {

    private CommandSender sender;
    private String[] args;

    public AddCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void run() {
        if (args.length < 2) {
            helpAdd(sender);
            return;
        }

        if (args[1].equalsIgnoreCase("item")) {
            if (args.length >= 4) {
                String name = args[2];

                if (name.equalsIgnoreCase("random"))
                    name = RandomName.generateName();
                if (args.length >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
                    return;
                }

                HashMap<String, Integer> itemHash;
                try {
                    itemHash = CouponCodes.getCouponHandler().itemStringToHash(args[3], sender);
                } catch (UnknownMaterialException e) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.InvalidName", e.getItemName()));
                    return;
                }
                ItemCoupon ic = CouponCodes.getCouponHandler().createNewItemCoupon(name, 1, -1, itemHash, new HashMap<String, Boolean>());

                if (ic.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
            }
        } else if (args[1].equalsIgnoreCase("econ")) {
            if (args.length >= 4) {
                String name = args[2];
                int money = Integer.parseInt(args[3]);

                if (name.equalsIgnoreCase("random"))
                    name = RandomName.generateName();
                if (args.length >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
                    return;
                }

                EconomyCoupon ec = CouponCodes.getCouponHandler().createNewEconomyCoupon(name, 1, -1, new HashMap<String, Boolean>(), money);

                if (ec.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
            }

        } else if (args[1].equalsIgnoreCase("rank")) {
            if (args.length >= 4) {
                String name = args[2];
                String group = args[3];

                if (name.equalsIgnoreCase("random"))
                    name = RandomName.generateName();
                if (args.length >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
                    return;
                }

                RankCoupon rc = CouponCodes.getCouponHandler().createNewRankCoupon(name, group, 1, -1, new HashMap<String, Boolean>());

                if (rc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
            }
        } else if (args[1].equalsIgnoreCase("xp")) {
            if (args.length >= 4) {
                String name = args[2];
                int xp = Integer.parseInt(args[3]);

                if (name.equalsIgnoreCase("random"))
                    name = RandomName.generateName();
                if (args.length >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
                    return;
                }

                XpCoupon xc = CouponCodes.getCouponHandler().createNewXpCoupon(name, xp, 1, -1, new HashMap<String, Boolean>());

                if (xc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
            }
        } else if (args[1].equalsIgnoreCase("cmd")) {
            if (args.length >= 4) {
                String name = args[2];
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String cmd = sb.toString();

                if (name.equalsIgnoreCase("random"))
                    name = RandomName.generateName();

                CommandCoupon cc = CouponCodes.getCouponHandler().createNewCommandCoupon(name, cmd, 1, -1, new HashMap<String, Boolean>());

                if (cc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddCmd"));
            }
        } else {
            helpAdd(sender);
        }
    }

    private void helpAdd(CommandSender sender) {
        sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
    }
}