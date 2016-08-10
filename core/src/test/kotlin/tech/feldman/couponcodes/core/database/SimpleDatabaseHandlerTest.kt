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
package tech.feldman.couponcodes.core.database

import org.junit.Test

class SimpleDatabaseHandlerTest {

    val itemHash = hashMapOf<String, Int>(
            Pair("stone", 2),
            Pair("grass", 3)
    )
    val itemStrings = listOf<String>("grass,3|stone,2", "stone,2|grass,3")

    val playerHash = hashMapOf<String, Boolean>(
            Pair("blue", true),
            Pair("carter", false)
    )
    val playerStrings = listOf<String>("blue:true,carter:false", "carter:false,blue:true")

    @Test
    fun itemHashToString() {
        assert (itemStrings.contains(SimpleDatabaseHandler.itemHashToString(itemHash)))
    }

    @Test
    fun itemStringToHash() {
        for (s in itemStrings) {
            assert(SimpleDatabaseHandler.itemStringToHash(s).equals(itemHash))
        }
    }

    @Test
    fun playerHashToString() {
        assert (playerStrings.contains(SimpleDatabaseHandler.playerHashToString(playerHash)))
    }

    @Test
    fun playerStringToHash() {
        for (s in playerStrings) {
            assert(SimpleDatabaseHandler.playerStringToHash(s).equals(playerHash))
        }
    }
}
