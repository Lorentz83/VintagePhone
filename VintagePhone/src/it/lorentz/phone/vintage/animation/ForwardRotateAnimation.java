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
import it.lorentz.phone.vintage.library.DragResult;
import it.lorentz.phone.vintage.library.IVibratorWrapper;

public class ForwardRotateAnimation extends RotateAnimation {
    public ForwardRotateAnimation(DragResult dragResult, IVibratorWrapper vibrator) {
        super(dragResult.getFromDegree(), dragResult.getToDegree(),
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        setFillAfter(true);
        if (dragResult.isLimitReached())
            setAnimationListener(new ForwardRotateAnimationListener(vibrator));
    }

    class ForwardRotateAnimationListener implements AnimationListener {
        IVibratorWrapper _vibrator;

        public ForwardRotateAnimationListener(IVibratorWrapper vibrator) {
            _vibrator = vibrator;
        }

        public void onAnimationEnd(Animation animation) {
            _vibrator.vibrate(50);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }
}
