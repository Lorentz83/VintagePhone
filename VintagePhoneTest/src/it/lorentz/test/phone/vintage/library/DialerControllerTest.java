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
import it.lorentz.phone.vintage.library.DragResult;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class DialerControllerTest {
    private DialerController _dialer;
    private final int centerX = 5, centerY = 5;
    private final int right = 10, bottom = 10;

    @Before
    public void setUp() {
        _dialer = new DialerController();
        _dialer.onSizeChanged(right, bottom);
    }

    @Test
    public void beginDrag() {
        DragResult drag = _dialer.beginDrag(centerX, centerY + 10);

        assertEquals(false, drag.isToAnimate());
        assertEquals(0f, drag.getFromDegree(), 0);
        assertEquals(0f, drag.getToDegree(), 0);
        assertEquals(0f, drag.getRotation(), 0);
        assertEquals(0, drag.getSelectedNumber());
    }

    @Test
    public void doDrag_OneStep() {
        _dialer.beginDrag(centerX, centerY + 1);
        DragResult drag = _dialer.doDrag(centerX - 1, centerY);

        assertEquals(true, drag.isToAnimate());
        assertEquals(0f, drag.getFromDegree(), 0);
        assertEquals(90f, drag.getToDegree(), 0);
        assertEquals(90f, drag.getRotation(), 0);
        assertEquals(-1, drag.getSelectedNumber());
    }

    @Test
    public void doDrag_TwoSteps() {
        _dialer.beginDrag(centerX, centerY + 1);
        _dialer.doDrag(centerX - 1, centerY + 1);
        DragResult drag = _dialer.doDrag(centerX - 1, centerY);

        assertEquals(true, drag.isToAnimate());
        assertEquals(45f, drag.getFromDegree(), 0);
        assertEquals(90f, drag.getToDegree(), 0);
        assertEquals(45f, drag.getRotation(), 0);
        assertEquals(-1, drag.getSelectedNumber());
    }

    @Test
    public void endDrag_OneStep() {
        _dialer.beginDrag(centerX, centerY + 1);
        DragResult drag = _dialer.endDrag(centerX - 1, centerY);

        assertEquals(true, drag.isToAnimate());
        assertEquals(90f, drag.getFromDegree(), 0);
        assertEquals(0f, drag.getToDegree(), 0);
        assertEquals(-90f, drag.getRotation(), 0);
        assertEquals(1, drag.getSelectedNumber());
        assertFalse(drag.isLimitReached());
    }

    @Test
    public void endDrag_TwoSteps() {
        _dialer.beginDrag(centerX, centerY + 1);
        _dialer.doDrag(centerX - 1, centerY + 1);
        DragResult drag = _dialer.endDrag(centerX - 1, centerY);

        assertEquals(true, drag.isToAnimate());
        assertEquals(90f, drag.getFromDegree(), 0);
        assertEquals(0f, drag.getToDegree(), 0);
        assertEquals(-90f, drag.getRotation(), 0);
        assertEquals(1, drag.getSelectedNumber());
    }

    @Test
    public void drag_Dial0AndStopRotate() {
        _dialer.beginDrag(centerX, centerY + 1);
        _dialer.doDrag(centerX - 1, centerY);
        _dialer.doDrag(centerX, centerY - 1);
        _dialer.doDrag(centerX + 1, centerY);
        DragResult drag0 = _dialer.doDrag(centerX+1, centerY +1);
        DragResult drag1 = _dialer.endDrag(centerX, centerY + 1);

        assertTrue(drag0.getToDegree() < 360);
        assertTrue(drag0.getToDegree() > 270);


        assertEquals(true, drag1.isToAnimate());
        assertTrue(drag1.getFromDegree() < 360);
        assertTrue(drag1.getFromDegree() > 270);

        assertEquals(0f, drag1.getToDegree(), 0);
        assertEquals(0, drag1.getSelectedNumber());
    }

    @Test
    public void doDrag_NoBackRotate(){
        _dialer.beginDrag(centerX, centerY + 1);
        _dialer.doDrag(centerX - 1, centerY);
        DragResult drag0 = _dialer.doDrag(centerX -1, centerY + 1);
        DragResult drag1 = _dialer.doDrag(centerX , centerY - 1);

        assertFalse(drag0.isToAnimate());
        assertTrue(drag1.isToAnimate());
        assertEquals(90f, drag1.getRotation(), 0);
    }

    @Test
    public void endDrag_NoBackRotate(){
        _dialer.beginDrag(centerX, centerY + 1);
        _dialer.doDrag(centerX - 1, centerY);
        DragResult drag = _dialer.endDrag(centerX -1, centerY + 1);

        assertTrue(drag.isToAnimate());
        assertEquals(-90f, drag.getRotation(), 0);
        assertEquals(90f, drag.getFromDegree(), 0);
        assertEquals(0f, drag.getToDegree(), 0);
    }

    @Test
    public void doDrag_BackRotateBeginNewDrag(){
        _dialer.beginDrag(centerX-1,centerY);
        DragResult drag1 = _dialer.doDrag(centerX, centerY+1);
        DragResult drag2 = _dialer.doDrag(centerX - 1, centerY - 1);

        assertFalse(drag1.isToAnimate());

        assertTrue(drag2.isToAnimate());

        assertEquals(90 + 45, drag2.getRotation(), 0);
    }

    @Test
    public void doDrag_BackRotate0DegreeBug(){
        _dialer.beginDrag(centerX+1,centerY+1);
        DragResult drag1 = _dialer.doDrag(centerX+1, centerY-1);

        assertFalse(drag1.isToAnimate());
    }

    @Test
    public void endDrag_OnDigitBorder(){
        DragResult drag1_1 = _dialer.beginDrag(centerX +10, centerY - 10);
        DragResult drag1_2 = _dialer.endDrag(centerX, centerY + 10);

        DragResult drag2_1 = _dialer.beginDrag(centerX + 10, centerY - 11);
        DragResult drag2_2 = _dialer.endDrag(centerX, centerY + 10);

        assertEquals(1, drag1_1.getSelectedNumber());
        assertEquals(1, drag2_1.getSelectedNumber());

        assertEquals(drag2_2.getToDegree(), drag1_2.getToDegree(), 0);
        assertEquals(drag2_2.getFromDegree(), drag1_2.getFromDegree(), 0);
    }

    @Test
    public void doDrag_OnDigitBorder(){
        DragResult drag1_1 = _dialer.beginDrag(centerX +10, centerY - 10);
        DragResult drag1_2 = _dialer.doDrag(centerX, centerY + 10);

        DragResult drag2_1 = _dialer.beginDrag(centerX + 10, centerY - 11);
        DragResult drag2_2 = _dialer.doDrag(centerX, centerY + 10);

        assertEquals(1, drag1_1.getSelectedNumber());
        assertEquals(1, drag2_1.getSelectedNumber());
        assertTrue(drag1_2.isLimitReached());
        assertTrue(drag2_2.isLimitReached());

        assertEquals(drag2_2.getFromDegree(), drag1_2.getFromDegree(), 0);
        assertEquals(drag2_2.getToDegree(), drag1_2.getToDegree(), 0);
    }


}

