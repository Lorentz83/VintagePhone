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
import junit.framework.Assert;
import org.junit.Test;

public class AngleTest {

    @Test
    public void getMinRotationAnglesEquals() {
        Assert.assertEquals(0f, new Angle(1).getMinRotation(new Angle(1)));
        Assert.assertEquals(0f, new Angle(88).getMinRotation(new Angle(88)));
        Assert.assertEquals(0f, new Angle(276).getMinRotation(new Angle(276)));
    }

    @Test
    public void getMinRotationPositiveAngles() {
        Assert.assertEquals(10f, new Angle(0).getMinRotation(new Angle(10)));
        Assert.assertEquals(10f, new Angle(60).getMinRotation(new Angle(70)));
        Assert.assertEquals(10f, new Angle(355).getMinRotation(new Angle(5)));
    }

    @Test
    public void getMinRotationNegativeAngles() {
        Assert.assertEquals(-10f, new Angle(10).getMinRotation(new Angle(0)));
        Assert.assertEquals(-10f, new Angle(5).getMinRotation(new Angle(355)));
    }

    @Test
    public void getMinRotationNegativeBigAngles() {
        Assert.assertEquals(180f, new Angle(0).getMinRotation(new Angle(180)));
        Assert.assertEquals(-179f, new Angle(0).getMinRotation(new Angle(181)));

        Assert.assertEquals(180f, new Angle(90).getMinRotation(new Angle(270)));
        Assert.assertEquals(-179f, new Angle(90).getMinRotation(new Angle(271)));
    }

    @Test
    public void getRotationNegativeBigAngles() {
        Assert.assertEquals(180f, new Angle(0).getRotation(new Angle(180)));
        Assert.assertEquals(181f, new Angle(0).getRotation(new Angle(181)));

        Assert.assertEquals(180f, new Angle(90).getRotation(new Angle(270)));
        Assert.assertEquals(181f, new Angle(90).getRotation(new Angle(271)));
    }

    public void equals() {
        Assert.assertEquals(new Angle(12), new Angle(12));
        Assert.assertFalse(new Angle(0).equals(new Angle(360)));
    }
}