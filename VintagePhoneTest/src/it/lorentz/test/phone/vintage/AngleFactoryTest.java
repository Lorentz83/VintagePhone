/**
 *  Copyright (C) 2011 Lorenzo Bossi
 *
 *  This file is part of Vintage Phone.
 *
 *  Vintage Phone is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Vintage Phone is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Vintage Phone.
 *  If not, see <http://www.gnu.org/licenses/>.
 */


package it.lorentz.test.phone.vintage;

import it.lorentz.phone.vintage.library.Angle;
import it.lorentz.phone.vintage.library.AngleFactory;
import junit.framework.Assert;
import org.junit.Test;

public class AngleFactoryTest {

    @Test
    public void angle90Degree() {
        AngleFactory angle = new AngleFactory();

        Angle angle0 = angle.getAngleDegree(1, 0);
        Angle angle90 = angle.getAngleDegree(0, 1);
        Angle angle180 = angle.getAngleDegree(-1, 0);
        Angle angle270 = angle.getAngleDegree(0, -1);

        Assert.assertEquals(0f, angle0.getDegree());
        Assert.assertEquals(90f, angle90.getDegree());
        Assert.assertEquals(180f, angle180.getDegree());
        Assert.assertEquals(270f, angle270.getDegree());
    }

    @Test
    public void angle45Degree() {
        AngleFactory angle = new AngleFactory();

        Angle angle45 = angle.getAngleDegree(1, 1);
        Angle angle135 = angle.getAngleDegree(-1, 1);
        Angle angle225 = angle.getAngleDegree(-1, -1);
        Angle angle315 = angle.getAngleDegree(1, -1);

        Assert.assertEquals(45f, angle45.getDegree());
        Assert.assertEquals(135f, angle135.getDegree());
        Assert.assertEquals(225f, angle225.getDegree());
        Assert.assertEquals(315f, angle315.getDegree());
    }

    @Test
    public void angleTranslateDegree() {
        AngleFactory angle = new AngleFactory();

        Angle degree = angle.getAngleDegree(-1, -1);

        Assert.assertEquals(225f, degree.getDegree());
    }

    @Test
    public void invalidAngleDegree() {
        AngleFactory angle = new AngleFactory();

        Angle degree = angle.getAngleDegree(0, 0);

        Assert.assertEquals(0f, degree.getDegree());
    }

    @Test
    public void getDigitByPosition_Normal() {
        Assert.assertEquals(0, AngleFactory.getDigitByPosition(new Angle(90)));
        Assert.assertEquals(9, AngleFactory.getDigitByPosition(new Angle(115)));
        Assert.assertEquals(8, AngleFactory.getDigitByPosition(new Angle(140)));
        Assert.assertEquals(7, AngleFactory.getDigitByPosition(new Angle(165)));
        Assert.assertEquals(6, AngleFactory.getDigitByPosition(new Angle(190)));
        Assert.assertEquals(5, AngleFactory.getDigitByPosition(new Angle(215)));
        Assert.assertEquals(4, AngleFactory.getDigitByPosition(new Angle(240)));
        Assert.assertEquals(3, AngleFactory.getDigitByPosition(new Angle(265)));
        Assert.assertEquals(2, AngleFactory.getDigitByPosition(new Angle(290)));
        Assert.assertEquals(1, AngleFactory.getDigitByPosition(new Angle(315)));
    }

    @Test
    public void getDigitByPosition_Interval() {
        Assert.assertEquals(0, AngleFactory.getDigitByPosition(new Angle(78)));
        Assert.assertEquals(0, AngleFactory.getDigitByPosition(new Angle(101)));
        Assert.assertEquals(9, AngleFactory.getDigitByPosition(new Angle(103)));
    }

    @Test
    public void getDigitByPosition_NoNumber() {
        Assert.assertEquals(-1, AngleFactory.getDigitByPosition(new Angle(76)));
        Assert.assertEquals(-1, AngleFactory.getDigitByPosition(new Angle(328)));
    }

    @Test
    public void moveUntilLimit(){
        //AngleFactory.moveUntilLimit(null, null, null);
        Assert.fail("To Test This");
    }

    @Test public void getDigitByRotation_OutOfBoundsValue(){
        Assert.assertEquals(-1, AngleFactory.getDigitByRotation(4));
        Assert.assertEquals(0, AngleFactory.getDigitByRotation(360));
        Assert.assertEquals(0, AngleFactory.getDigitByRotation(400));
    }

    @Test public void getDigitByRotation_DigitBounds(){
        Assert.assertEquals(-1, AngleFactory.getDigitByRotation(74));
        Assert.assertEquals(1, AngleFactory.getDigitByRotation(75));
        Assert.assertEquals(1, AngleFactory.getDigitByRotation(99));
        Assert.assertEquals(2, AngleFactory.getDigitByRotation(100));
    }

    @Test public void getDigitByRotation_AllDigits(){
        Assert.assertEquals(1, AngleFactory.getDigitByRotation(100 - 1));
        Assert.assertEquals(2, AngleFactory.getDigitByRotation(125 - 1));
        Assert.assertEquals(3, AngleFactory.getDigitByRotation(150 - 1));
        Assert.assertEquals(4, AngleFactory.getDigitByRotation(175 - 1));
        Assert.assertEquals(5, AngleFactory.getDigitByRotation(200 - 1));
        Assert.assertEquals(6, AngleFactory.getDigitByRotation(225 - 1));
        Assert.assertEquals(7, AngleFactory.getDigitByRotation(250 - 1));
        Assert.assertEquals(8, AngleFactory.getDigitByRotation(275 - 1));
        Assert.assertEquals(9, AngleFactory.getDigitByRotation(300 - 1));
        Assert.assertEquals(0, AngleFactory.getDigitByRotation(325 - 1));
    }
}
