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
package com.drevelopment.couponcodes.core.coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.CommandCoupon;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.CouponHandler;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.api.exceptions.UnknownMaterialException;

public abstract class SimpleCouponHandler implements CouponHandler {

    @Override
    public boolean couponExists(Coupon coupon) {
        return getCoupons().contains(coupon.getName());
    }

    @Override
    public boolean couponExists(String coupon) {
        return getCoupons().contains(coupon);
    }

    @Override
    public int getAmountOf(String type) {
        ArrayList<String> list = getCoupons();
        int item = 0;
        int econ = 0;
        int rank = 0;
        int xp = 0;
        int cmd = 0;

        for (String name : list) {
            Coupon c = getBasicCoupon(name);
            if (c instanceof ItemCoupon)
                item++;
            if (c instanceof EconomyCoupon)
                econ++;
            if (c instanceof RankCoupon)
                rank++;
            if (c instanceof XpCoupon)
                xp++;
            if (c instanceof CommandCoupon)
                cmd++;
        }

        if (type.equalsIgnoreCase("Item"))
            return item;
        else if (type.equalsIgnoreCase("Economy"))
            return econ;
        else if (type.equalsIgnoreCase("Rank"))
            return rank;
        else if (type.equalsIgnoreCase("Xp"))
            return xp;
        else if (type.equalsIgnoreCase("Cmd"))
            return cmd;
        else
            return 0;
    }

    @Override
    public ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<String, Integer> items, HashMap<String, Boolean> usedplayers) {
        return new SimpleItemCoupon(name, usetimes, time, usedplayers, items);
    }

    @Override
    public EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money) {
        return new SimpleEconomyCoupon(name, usetimes, time, usedplayers, money);
    }

    @Override
    public RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
        return new SimpleRankCoupon(name, group, usetimes, time, usedplayers);
    }

    @Override
    public XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
        return new SimpleXpCoupon(name, usetimes, time, usedplayers, xp);
    }

    @Override
    public CommandCoupon createNewCommandCoupon(String name, String cmd, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
        return new SimpleCommandCoupon(name, usetimes, time, usedplayers, cmd);
    }

    @Override
    public String itemHashToString(HashMap<String, Integer> hash) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> en : hash.entrySet()) {
            sb.append(en.getKey()).append(":").append(en.getValue()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public HashMap<String, Integer> itemStringToHash(String args, CommandSender sender) throws UnknownMaterialException {
        HashMap<String, Integer> ids = new HashMap<>();
        String[] sp = args.split(",");
        try {
            for (String s : sp) {
                String name;
                int amount = 0;

                if (CouponCodes.getModTransformer().isValidMaterial(s.split(":")[0].toUpperCase())) {
                    name = s.split(":")[0].toUpperCase();
                } else {
                    throw new UnknownMaterialException(s.split(":")[0].toUpperCase());
                }

                if (CouponCodes.getModTransformer().isNumeric(s.split(":")[1])) {
                    amount = Integer.parseInt(s.split(":")[1]);
                }

                ids.put(name, amount);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ids;
    }

    @Override
    public String playerHashToString(HashMap<String, Boolean> hash) {
        if (hash.isEmpty() || hash == null || hash.size() < 1)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Boolean> en : hash.entrySet()) {
            sb.append(en.getKey()).append(":").append(en.getValue()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public HashMap<String, Boolean> playerStringToHash(String args) {
        HashMap<String, Boolean> pl = new HashMap<>();
        if (args.equals(null) || args.length() < 1)
            return pl;
        String[] sp = args.split(",");
        try {
            for (String aSp : sp) {
                String a = String.valueOf(aSp.split(":")[0]);
                Boolean b = Boolean.valueOf(aSp.split(":")[1]);
                pl.put(a, b);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return pl;
    }

}
