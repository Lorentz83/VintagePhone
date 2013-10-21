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


package it.lorentz.test.phone.vintage.library;


import it.lorentz.phone.vintage.library.DialerController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DialerControllerStressTest {
    private DialerController _dialer;
    private final int right = 100, bottom = 100;

    @Before public void setUp() {
        _dialer = new DialerController();
        _dialer.onSizeChanged(right, bottom);
    }

    @Test public void stressDial(){
        int num = 1000;
        long begin = System.currentTimeMillis();
        for (int i=0; i< num; i++)
            fullDial();
        long end = System.currentTimeMillis();
        long millisecond = end-begin;
        System.err.println(millisecond);
        assertTrue("Too slow!!!", millisecond < 1000);
    }

    private void fullDial(){
        int x,y;
        _dialer.beginDrag(0,100);
        for (x=0 ; x>-100 ; x-=1)
            _dialer.doDrag(x,100).getRotation();

        for (y=100 ; y>-100 ; y-=1)
            _dialer.doDrag(-100,y);

        for (x=-100 ; x<100 ; x+=1)
            _dialer.doDrag(x,-100);

        for (y=-100 ; y<100 ; y+=1)
            _dialer.doDrag(100,y);

        _dialer.endDrag(0,100);
    }

}
