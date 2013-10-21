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

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.ImageView;
import it.lorentz.phone.vintage.animation.BackRotateAnimationAndVibration;
import it.lorentz.phone.vintage.animation.ForwardRotateAnimation;
import it.lorentz.phone.vintage.audio.AudioPlayerFactory;
import it.lorentz.phone.vintage.library.DialerController;
import it.lorentz.phone.vintage.library.DragResult;
import it.lorentz.phone.vintage.library.IVibratorWrapper;

public class DialerView extends ImageView {
    private IVibratorWrapper _vibrator;
    private IDialButtonListener _buttonListener;
    private DialerController _controller;
    private AudioPlayerFactory _audioPlayerFactory;
    private SharedPreferences _pref;

    public DialerView(Context context, IVibratorWrapper vibrator, IDialButtonListener buttonListener) {
        super(context);
        _pref = PreferenceManager.getDefaultSharedPreferences(context);
        _vibrator = vibrator;

        setFocusable(true); //necessary for getting the touch events

        setImageResource(R.drawable.dialer);
        adjustColor();
        _buttonListener = buttonListener;
        _controller = new DialerController();
        _audioPlayerFactory = new AudioPlayerFactory(context);
    }

    public void adjustColor(){
        String s = _pref.getString("color_pref", "0");
        adjustHue(Float.parseFloat(s));
    }

    /**
     * @param value from -180 to 180
     */
    private void adjustHue(float value) {
        value = value / 180f * (float) Math.PI;
        float cosVal = (float) Math.cos(value);
        float sinVal = (float) Math.sin(value);
        float lumR = 0.213f;
        float lumG = 0.715f;
        float lumB = 0.072f;
        float[] mat = new float[]{
                lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                0f, 0f, 0f, 1f, 0f,
                0f, 0f, 0f, 0f, 1f
        };

        setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(mat)));
    }

    @Override protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        _controller.onSizeChanged(w, h);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                _controller.beginDrag(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                onActionUp(x, y);
                break;
        }
        return true;
    }

    private void onActionUp(int x, int y) {
        DragResult dragResult = _controller.endDrag(x, y);

        if (dragResult.isToAnimate()) {
            Animation animation = new BackRotateAnimationAndVibration(dragResult, _vibrator, _audioPlayerFactory.getPlayerWithCurrentSettings());
            startAnimation(animation);

            int selectedNumber = dragResult.getSelectedNumber();
            if (selectedNumber >= 0) {
                _buttonListener.numberDialed(selectedNumber);
            }
        }
    }

    private void onActionMove(int x, int y) {
        DragResult dragResult = _controller.doDrag(x, y);
        if (dragResult.isToAnimate()) {
            Animation animation = new ForwardRotateAnimation(dragResult, _vibrator);
            startAnimation(animation);
        }
    }
}
