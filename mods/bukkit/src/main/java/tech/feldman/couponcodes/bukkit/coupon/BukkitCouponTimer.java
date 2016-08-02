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
package tech.feldman.couponcodes.bukkit.coupon;

import java.sql.SQLException;
import java.util.ArrayList;

import tech.feldman.couponcodes.api.CouponCodes;
import tech.feldman.couponcodes.api.coupon.Coupon;
import tech.feldman.couponcodes.api.event.coupon.CouponTimeChangeEvent;
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions;

public class BukkitCouponTimer implements Runnable {

    private BukkitCouponHandler ch;
    private ArrayList<String> cl;

    public BukkitCouponTimer() {
        ch = (BukkitCouponHandler) CouponCodes.getCouponHandler();

        // Make sure SQL is open
        if (ch.getDatabaseHandler().getDatabaseOptions() instanceof MySQLOptions) {
            try {
                ch.getDatabaseHandler().open();
            } catch (SQLException ignored) {
            }
        }

        cl = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            cl = ch.getCoupons();
            if (cl == null)
                return;

            for (String name : cl) {
                if (ch.getDatabaseHandler().getConnection().isClosed())
                    return;
                Coupon c = ch.getBasicCoupon(name);
                if (c == null)
                    continue;
                if (c.isExpired() || c.getTime() == -1)
                    continue;

                if (c.getTime() - 10 < 0) {
                    if (c.getTime() - 5 < 0 || c.getTime() - 5 == 0) {
                        c.setTime(0);
                    } else {
                        c.setTime(5);
                    }
                } else {
                    c.setTime(c.getTime() - 10);
                }
                ch.updateCouponTime(c);
                CouponCodes.getEventHandler().post(new CouponTimeChangeEvent(c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ignored) {

        }
    }

}
