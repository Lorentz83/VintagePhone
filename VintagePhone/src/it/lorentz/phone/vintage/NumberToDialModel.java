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


package it.lorentz.phone.vintage;

import android.widget.TextView;

class NumberToDialModel implements IDialButtonListener {
    TextView _numberView;
    CharSequence _numberToDial;
    static final CharSequence _empty = new String();

    public NumberToDialModel(TextView numberView) {
        _numberView = numberView;
        _numberToDial = _empty;
    }

    public void numberDialed(int number) {
        _numberToDial = _numberToDial.toString() + number;

        _numberView.setText(_numberToDial);
    }

    public boolean deleteLastDigit() {
        int end = _numberToDial.length() - 1;
        if (end >= 0) {
            _numberToDial = _numberToDial.subSequence(0, end);
            _numberView.setText(_numberToDial);
            return true;
        }
        return false;
    }

    public void clearNumber() {
        _numberToDial = _empty;
        _numberView.setText(_empty);
    }

    public CharSequence getPhone() {
        return _numberToDial;
    }

    public void setPhone(CharSequence phone) {
        if (phone == null)
            _numberToDial = _empty;
        else
            _numberToDial = phone;
    }
}