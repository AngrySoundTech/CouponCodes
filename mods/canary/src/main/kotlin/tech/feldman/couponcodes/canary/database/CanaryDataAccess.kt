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
package tech.feldman.couponcodes.canary.database

import net.canarymod.database.Column
import net.canarymod.database.Column.DataType
import net.canarymod.database.DataAccess

class CanaryDataAccess : DataAccess("canary_coupon_object") {

    @Column(columnName = "name", dataType = DataType.STRING)
    lateinit var couponName: String
    @Column(columnName = "ctype", dataType = DataType.STRING)
    lateinit var ctype: String
    @Column(columnName = "usetimes", dataType = DataType.INTEGER)
    var usetimes: Int = 0
    @Column(columnName = "usedplayers", dataType = DataType.STRING)
    lateinit var usedplayers: String
    @Column(columnName = "ids", dataType = DataType.STRING)
    lateinit var ids: String
    @Column(columnName = "money", dataType = DataType.INTEGER)
    var money: Int = 0
    @Column(columnName = "groupname", dataType = DataType.STRING)
    lateinit var groupname: String
    @Column(columnName = "timeuse", dataType = DataType.INTEGER)
    var timeuse: Int = 0
    @Column(columnName = "xp", dataType = DataType.INTEGER)
    var xp: Int = 0
    @Column(columnName = "command", dataType = DataType.STRING)
    lateinit var command: String


    @Override
    override fun getInstance(): DataAccess {
        return CanaryDataAccess()
    }

}
