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

public class DragResult {
    private final float _fromDegree;
    private final float _toDegree;
    private final float _rotation;
    private final int _selectedNumber;
    private final boolean _limitReached;

    public DragResult(float fromDegree, float toDegree, float rotation, int selectedNumber, boolean limitReached) {
        _fromDegree = fromDegree;
        _toDegree = toDegree;
        _rotation = rotation;
        _selectedNumber = selectedNumber;
        _limitReached = limitReached;
    }

    public float getFromDegree() {
        return _fromDegree;
    }

    public float getToDegree() {
        return _toDegree;
    }

    public boolean isToAnimate() {
        return _rotation != 0;
    }

    public boolean isLimitReached() {
        return _limitReached;
    }

    public int getSelectedNumber() {
        return _selectedNumber;
    }

    public float getRotation() {
        return _rotation;
    }
}
