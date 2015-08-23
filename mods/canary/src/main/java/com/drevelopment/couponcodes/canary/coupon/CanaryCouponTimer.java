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
package com.drevelopment.couponcodes.canary.coupon;

import java.util.ArrayList;

import net.canarymod.tasks.ServerTask;
import net.canarymod.tasks.TaskOwner;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.event.coupon.CouponTimeChangeEvent;

public class CanaryCouponTimer extends ServerTask {

    private ArrayList<String> cl = new ArrayList<String>();
    private Coupon c;
    public CanaryCouponTimer(TaskOwner owner, long delay) {
        super(owner, delay, true);
    }

    @Override
    public void run() {
        cl = CouponCodes.getCouponHandler().getCoupons();
        if (cl == null)
            return;

        for (String name : cl) {
            c = CouponCodes.getCouponHandler().getCoupon(name);
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
            CouponCodes.getCouponHandler().updateCouponTime(c);
            CouponCodes.getEventHandler().post(new CouponTimeChangeEvent(c));
        }
    }

}
