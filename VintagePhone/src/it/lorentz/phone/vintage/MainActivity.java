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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import it.lorentz.phone.vintage.library.VibratorWrapper;

//http://www.androiddevelopment.org/category/code-examples/
//http://developer.android.com/guide/appendix/g-app-intents.html
//http://developer.android.com/reference/android/content/Intent.html

public class MainActivity extends Activity {
    private NumberToDialModel _dialListener;
    private VibratorWrapper _vibrator;
    private DialerView _dialerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
         setVolumeControlStream(AudioManager.STREAM_MUSIC);
        _vibrator = new VibratorWrapper((Vibrator) getSystemService(Context.VIBRATOR_SERVICE), getBaseContext());

        setContentView(R.layout.main);
        TextView numberLbl = (TextView) findViewById(R.id.label_number);

        ImageButton callBtn = (ImageButton) findViewById(R.id.call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performCall();
            }
        });

        _dialListener = new NumberToDialModel(numberLbl);

        addDialer();
    }

    private void addDialer() {
        RelativeLayout dialerContainer = (RelativeLayout) findViewById(R.id.dialer_container);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView background = new ImageView(this);
        background.setImageResource(R.drawable.bg);
        background.setAdjustViewBounds(true);

        _dialerView = new DialerView(this, _vibrator, _dialListener);
        _dialerView.setAdjustViewBounds(true);

        ImageView foreground = new ImageView(this);
        foreground.setImageResource(R.drawable.fg);
        foreground.setAdjustViewBounds(true);

        dialerContainer.addView(background, param);
        dialerContainer.addView(_dialerView, param);
        dialerContainer.addView(foreground, param);
    }

    @Override
    public void onPause() {
        _vibrator.cancel();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (_dialListener.deleteLastDigit() || event.getRepeatCount() > 0)
                return true;
        }
        if (keyCode == KeyEvent.KEYCODE_CALL) {
            performCall();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void performCall() {
        CharSequence phoneNumber = _dialListener.getPhone();
        if (phoneNumber.length() > 0) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
            _dialListener.clearNumber();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _dialListener.setPhone(savedInstanceState.getCharSequence("Phone"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putCharSequence("Phone", _dialListener.getPhone());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_call:
                performCall();
                return true;
            case R.id.menu_clear:
                _dialListener.clearNumber();
                return true;
            case R.id.menu_about:
                displayAboutWindow();
                return true;
            case R.id.menu_settings:
                Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
                startActivityForResult(settingsActivity, 1);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        _dialerView.adjustColor();
    }

    private void displayAboutWindow() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.about_menu);
        alertDialogBuilder.setMessage(String.format(
                getString(R.string.about_msg),
                getString(R.string.app_name),
                getVersionName()));

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private String getVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "<No version info>";
    }
}
