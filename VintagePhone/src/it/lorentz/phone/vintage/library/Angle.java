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

public class Angle {
    private float _degree;

    public Angle(float degree) {
        _degree = degree;
    }

    public float getMinRotation(Angle toAngle) {
        float diff = toAngle._degree - _degree;
        while (diff < -180) diff += 360;
        while (diff > 180) diff -= 360;
        return diff;
    }

    public Angle add(float rotation) {
        return new Angle(_degree + rotation);
    }

    public float getRotation(Angle toAngle) {
        return toAngle._degree - _degree;
    }

    public float getDegree() {
        return _degree;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Angle)) return false;
        return _degree == ((Angle) other)._degree;
    }

    @Override
    public String toString() {
        return "" + _degree;
    }
}