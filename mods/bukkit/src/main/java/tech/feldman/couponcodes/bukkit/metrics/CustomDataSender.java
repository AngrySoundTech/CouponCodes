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
package tech.feldman.couponcodes.bukkit.metrics;

import tech.feldman.couponcodes.api.CouponCodes;
import tech.feldman.couponcodes.api.database.DatabaseHandler;
import tech.feldman.couponcodes.bukkit.metrics.Metrics.Graph;

public class CustomDataSender implements Runnable {

    private Metrics metrics;
    private DatabaseHandler ch;

    public CustomDataSender(Metrics metrics) {
        this.metrics = metrics;
        this.ch = CouponCodes.getDatabaseHandler();
    }

    @Override
    public void run() {
        // Coupon Types
        Graph couponTypesGraph = metrics.createGraph("Coupon Types");
        couponTypesGraph.addPlotter(new Metrics.Plotter("Item Coupons") {
            @Override
            public int getValue() {
                return ch.getAmountOf("Item");
            }
        });
        couponTypesGraph.addPlotter(new Metrics.Plotter("Economy Coupons") {
            @Override
            public int getValue() {
                return ch.getAmountOf("Economy");
            }
        });
        couponTypesGraph.addPlotter(new Metrics.Plotter("Rank Coupons") {
            @Override
            public int getValue() {
                return ch.getAmountOf("Rank");
            }
        });
        couponTypesGraph.addPlotter(new Metrics.Plotter("Xp Coupons") {
            @Override
            public int getValue() {
                return ch.getAmountOf("Xp");
            }
        });
        couponTypesGraph.addPlotter(new Metrics.Plotter("Command Coupons") {
            @Override
            public int getValue() {
                return ch.getAmountOf("Command");
            }
        });
        // Database types
        Graph dbTypesGraph = metrics.createGraph("Database Type");
        dbTypesGraph.addPlotter(new Metrics.Plotter(CouponCodes.getDatabase().getDatabaseType()) {
            @Override
            public int getValue() {
                return 1;
            }
        });
    }
}
