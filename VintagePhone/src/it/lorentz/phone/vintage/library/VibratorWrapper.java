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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;

public class VibratorWrapper implements IVibratorWrapper {
    private Vibrator _vibrator;
    private SharedPreferences _pref;

    public VibratorWrapper(Vibrator vibrator, Context context) {
        _vibrator = vibrator;
        _pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void cancel() {
        _vibrator.cancel();
    }

    public void vibrate(long[] pattern, int repeat) {
        if(enable())
            _vibrator.vibrate(pattern, repeat);
    }

    public void vibrate(long milliseconds) {
        if(enable())
            _vibrator.vibrate(milliseconds);
    }

    private boolean enable(){
        return _pref.getBoolean("vibration_pref", true);
    }
}

