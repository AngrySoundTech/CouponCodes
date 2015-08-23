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
package com.drevelopment.couponcodes.api.config;

public interface ConfigHandler {

    /**
     * Gets whether or not a thread should be created for timed coupons.
     * <p> If this is false, then timed coupons will not function
     * @return boolean specified in config.
     */
    boolean getUseThread();

    /**
     * Gets whether or not debug messages should be printed to the console
     * @return boolean specified in config.
     */
    boolean getDebug();

    /**
     * Gets whether or not the plugin should use Metrics.
     * <p> These statistics can be viewed <a href='http://mcstats.org/plugin/CouponCodes'>here</a>
     * @return boolean specified in config
     */
    boolean getUseMetrics();

    /**
     * Gets whether or not the plugin should update itself automatically
     * @return boolean specified in config
     */
    boolean getAutoUpdate();

    /**
     * Gets the default locale to use
     * @return the default locale specified in the config, in the form <code>en_US</code>
     */
    String getLocale();

}
