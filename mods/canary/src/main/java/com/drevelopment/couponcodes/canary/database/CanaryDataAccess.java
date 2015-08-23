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
package com.drevelopment.couponcodes.canary.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Column.DataType;

public class CanaryDataAccess extends DataAccess {

    @Column(columnName = "name", dataType = DataType.STRING)
    public String name;
    @Column(columnName = "ctype", dataType = DataType.STRING)
    public String ctype;
    @Column(columnName = "usetimes", dataType = DataType.INTEGER)
    public int usetimes;
    @Column(columnName = "usedplayers", dataType = DataType.STRING)
    public String usedplayers;
    @Column(columnName = "ids", dataType = DataType.STRING)
    public String ids;
    @Column(columnName = "money", dataType = DataType.INTEGER)
    public int money;
    @Column(columnName = "groupname", dataType = DataType.STRING)
    public String groupname;
    @Column(columnName = "timeuse", dataType = DataType.INTEGER)
    public int timeuse;
    @Column(columnName = "xp", dataType = DataType.INTEGER)
    public int xp;
    @Column(columnName = "command", dataType = DataType.STRING)
    public String command;

    public CanaryDataAccess() {
        super("canary_coupon_object");
    }

    @Override
    public DataAccess getInstance() {
        return new CanaryDataAccess();
    }

}
