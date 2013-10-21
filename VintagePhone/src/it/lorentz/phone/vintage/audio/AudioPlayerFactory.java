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


package it.lorentz.phone.vintage.audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import it.lorentz.phone.vintage.R;

public class AudioPlayerFactory {
    private SharedPreferences _pref;
    private IAudioPlayer _nullPlayer;
    private AudioPlayer _soundPlayer;

    public AudioPlayerFactory(Context context){
        _pref = PreferenceManager.getDefaultSharedPreferences(context);
        _nullPlayer = new NullAudioPlayer();
        _soundPlayer = new AudioPlayer(context, R.raw.back);
    }

    public IAudioPlayer getPlayerWithCurrentSettings() {
        float volume = getVolume();
        if(volume>0){
            _soundPlayer.setVolume(volume);
            return _soundPlayer;
        }
        else {
            return _nullPlayer;
        }
    }

    private float getVolume(){
        float volume = 1;
        try{
            volume = Float.parseFloat(_pref.getString("soundVolume", "1"));
        }
        catch (NumberFormatException ex){}
        return volume;
    }
}

class NullAudioPlayer implements IAudioPlayer{
    @Override public void start() {}
    @Override public void stop() {}
}

class AudioPlayer implements IAudioPlayer{
    private MediaPlayer _audio;

    public AudioPlayer(Context context, int rawAudioId) {
        _audio = MediaPlayer.create(context, rawAudioId);
    }

    @Override public void stop() {
        try {
            _audio.stop();
            _audio.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public void start() {
        try {
            _audio.seekTo(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            _audio.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume){
        try{
            _audio.setVolume(volume,volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
