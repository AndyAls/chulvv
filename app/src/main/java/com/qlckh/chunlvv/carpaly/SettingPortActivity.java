package com.qlckh.chunlvv.carpaly;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.qlckh.chunlvv.App;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.manager.Constant;

import android_serialport_api.SerialPortFinder;

/**
 * @author Andy
 * @date 2018/9/16 16:05
 * Desc:
 */
public class SettingPortActivity extends PreferenceActivity {

    private App mApplication;
    private SerialPortFinder mSerialPortFinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        mApplication = (App) getApplication();
        mSerialPortFinder = mApplication.mSerialPortFinder;
        addPreferencesFromResource(R.xml.seting_preference);
        String[] entries = mSerialPortFinder.getAllDevices();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
//        String[] entries=new String[]{"a","b","c","d","e"};
//        String[] entryValues=new String[]{"1","2","3","4","5"};

        final ListPreference pannels = (ListPreference)findPreference(Constant.PANEL_NODE);
        pannels.setEntries(entries);
        pannels.setEntryValues(entryValues);
        pannels.setSummary(pannels.getValue());
        pannels.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // Baud rates
        ListPreference panelRate = (ListPreference) findPreference(Constant.PANEL_RATE);
        panelRate.setSummary(panelRate.getValue());
        panelRate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });


        // Devices
        final ListPreference weights = (ListPreference)findPreference(Constant.WEGHT_NODE);
        weights.setEntries(entries);
        weights.setEntryValues(entryValues);
        weights.setSummary(weights.getValue());
        weights.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // Baud rates
        final ListPreference baudrates = (ListPreference)findPreference(Constant.WEGHT_RATE);
        baudrates.setSummary(baudrates.getValue());
        baudrates.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });



        // Devices
        final ListPreference scans = (ListPreference)findPreference(Constant.SCAN_NODE);
        scans.setEntries(entries);
        scans.setEntryValues(entryValues);
        scans.setSummary(scans.getValue());
        scans.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // Baud rates
        final ListPreference scansRate = (ListPreference)findPreference(Constant.SCAN_RATE);
        scansRate.setSummary(scansRate.getValue());
        scansRate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });



        final ListPreference printes = (ListPreference)findPreference(Constant.PRINT_NODE);
        printes.setEntries(entries);
        printes.setEntryValues(entryValues);
        printes.setSummary(printes.getValue());
        printes.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });

        // Baud rates
        ListPreference printRate = (ListPreference) findPreference(Constant.PRINT_RATE);
        printRate.setSummary(printRate.getValue());
        printRate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                return true;
            }
        });
    }
}
