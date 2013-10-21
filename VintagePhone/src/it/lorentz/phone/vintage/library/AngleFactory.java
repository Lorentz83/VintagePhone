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


package it.lorentz.phone.vintage.library;

public class AngleFactory {
    private static final int DigitsDegreeWidth = 25;
    private static final int DigitDegreeHalfWidth = DigitsDegreeWidth / 2;

    public Angle getAngleDegree(int pointX, int pointY) {
        return getAngle(pointX, pointY);
    }

    private Angle getAngle(int x, int y) {
        double r = Math.sqrt(x * x + y * y);
        if (r == 0)
            return new Angle(0);
        double rad = Math.asin(y / r);
        if (x < 0) rad = Math.PI - rad;
        if (rad < 0) rad = 2 * Math.PI + rad;

        float degree = (float) (rad * 180 / Math.PI);
        return new Angle(degree);
    }

    public static Angle moveUntilLimit(Angle currentPosition, Angle oldPosition, Angle beginDragPosition) {
        float limitAngle = 55;

        float minRotation = oldPosition.getMinRotation(currentPosition);
        float currentDegree = currentPosition.getDegree();

        int digit = getDigitByPosition(beginDragPosition);
        if (digit != -1) {
            float canonicalAngle = getCanonicalPositionByDigit(digit);
            limitAngle = limitAngle + (beginDragPosition.getDegree() - canonicalAngle) - DigitDegreeHalfWidth;
        }

        if (minRotation > 0 && currentDegree > limitAngle && currentDegree - minRotation <= limitAngle) {
            return new Angle(limitAngle);
        }
        return currentPosition;
    }

    private static float getCanonicalPositionByDigit(int digit) {
        switch (digit) {
            case 0:
                return 90;
            case 9:
                return 115;
            case 8:
                return 140;
            case 7:
                return 165;
            case 6:
                return 190;
            case 5:
                return 215;
            case 4:
                return 240;
            case 3:
                return 265;
            case 2:
                return 290;
            case 1:
                return 315;
        }
        throw new IndexOutOfBoundsException();
    }

    public static int getDigitByPosition(Angle position) {
        int degree = (int) position.getDegree() - 90 + DigitDegreeHalfWidth;
        if (degree < 0)
            return -1;

        int div = degree / DigitsDegreeWidth;

        if (div > 9)
            return -1;

        return (10 - div) % 10;
    }

    public static int getDigitByRotation(float rotation) {
        rotation = Math.abs(rotation);
        final int emptyRotation = 100 - DigitsDegreeWidth;
        rotation -= emptyRotation;
        if (rotation < 0)
            return -1;
        int ret = (int) (rotation / DigitsDegreeWidth);
        if (ret < 9)
            return ret + 1;
        else
            return 0;
    }
}
