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


package it.lorentz.phone.vintage.animation;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import it.lorentz.phone.vintage.audio.IAudioPlayer;
import it.lorentz.phone.vintage.library.DragResult;
import it.lorentz.phone.vintage.library.IVibratorWrapper;

public class BackRotateAnimationAndVibration extends RotateAnimation {

    public BackRotateAnimationAndVibration(DragResult dragResult, IVibratorWrapper vibrator, IAudioPlayer backSound) {
        super(dragResult.getFromDegree(), dragResult.getToDegree(),
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        setFillAfter(true);
        setAnimationListener(new BackRotateAnimationListener(vibrator, backSound));
        //s = v*t     t = s / v
        int duration = (int) (Math.abs(dragResult.getRotation()) / .3);
        setDuration(duration);
    }

    class BackRotateAnimationListener implements AnimationListener {
        private final IVibratorWrapper _vibrator;
        private final IAudioPlayer _backSound;

        public BackRotateAnimationListener(IVibratorWrapper vibrator, IAudioPlayer backSound) {
            _vibrator = vibrator;
            _backSound = backSound;
        }

        public void onAnimationEnd(Animation animation) {
            _backSound.stop();
            _vibrator.cancel();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            _backSound.start();
            _vibrator.vibrate(new long[]{150, 30}, 0);
        }
    }
}
