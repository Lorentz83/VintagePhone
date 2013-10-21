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

public class DialerController {
    private int _cx;
    private int _cy;
    private AngleFactory _angleFactory;

    private final Angle _baseDialerRotation;
    private Angle _dialerRotation;

    private Angle _lastDragPosition;
    private Angle _beginDragPosition;
    private int _lastDigit;
    private boolean _isDragging;

    public DialerController() {
        _angleFactory = new AngleFactory();
        _lastDragPosition = _baseDialerRotation = _angleFactory.getAngleDegree(0, 0);
        _lastDigit = -1;
        _isDragging = false;
    }

    public DragResult beginDrag(int x, int y) {
        x -= _cx;
        y -= _cy;
        _beginDragPosition = _lastDragPosition = _angleFactory.getAngleDegree(x, y);
        _lastDigit = AngleFactory.getDigitByPosition(_lastDragPosition);
        _dialerRotation = _baseDialerRotation;
        _isDragging = false;
        return new DragResult(_dialerRotation.getDegree(), _dialerRotation.getDegree(), 0, _lastDigit, false);
    }

    public DragResult doDrag(int posX, int posY) {
        int x = posX - _cx;
        int y = posY - _cy;

        Angle currentUnlimitedPosition = _angleFactory.getAngleDegree(x, y);
        Angle currentPosition = AngleFactory.moveUntilLimit(currentUnlimitedPosition, _lastDragPosition, _beginDragPosition);
        float rotation = _lastDragPosition.getMinRotation(currentPosition);
        boolean limitReached = currentUnlimitedPosition != currentPosition;

        if (rotation <= 0) {
            if (_isDragging)
                return new DragResult(_dialerRotation.getDegree(), _dialerRotation.getDegree(), 0, -1, limitReached);
            else
                return beginDrag(posX, posY);
        }

        _isDragging = true;
        _lastDragPosition = currentPosition;
        Angle fromRotation = _dialerRotation;
        _dialerRotation = _dialerRotation.add(rotation);
        return new DragResult(fromRotation.getDegree(), _dialerRotation.getDegree(), rotation, -1, limitReached);
    }

    public DragResult endDrag(int x, int y) {
        x -= _cx;
        y -= _cy;

        Angle newPosition = _angleFactory.getAngleDegree(x, y);

        Angle currentPosition = AngleFactory.moveUntilLimit(newPosition, _lastDragPosition, _beginDragPosition);

        if (currentPosition.equals(newPosition)) {
            _lastDigit = -1;
        }

        float rotation = _lastDragPosition.getMinRotation(currentPosition);

        if (rotation < 0) {
            rotation = 0;
        }

        _dialerRotation = _dialerRotation.add(rotation);
        rotation = _dialerRotation.getRotation(_baseDialerRotation);

        int digit = AngleFactory.getDigitByRotation(rotation);
        return new DragResult(_dialerRotation.getDegree(), _baseDialerRotation.getDegree(), rotation, digit, false);
    }

    public void onSizeChanged(int w, int h) {
        _cx = w / 2;
        _cy = h / 2;
    }
}
