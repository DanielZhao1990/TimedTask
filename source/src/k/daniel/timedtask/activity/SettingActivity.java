package k.daniel.timedtask.activity;

import k.daniel.timedtask.R;
import k.daniel.timedtask.app.MyApplication;
import k.daniel.timedtask.util.LanguageUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceChangeListener{
	SharedPreferences defaultSp;
	LanguageUtil languageUtil;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_xml);
		defaultSp=getPreferences(MODE_PRIVATE);
		defaultSp.registerOnSharedPreferenceChangeListener(this);
		ListPreference languagePreference =(ListPreference) findPreference("language");
		languageUtil=new LanguageUtil(this, R.array.language_code);
		languagePreference.setEntries(languageUtil.getDisplayNames());
		languagePreference.setEntryValues(languageUtil.getPreferenceEntryValues());
		languagePreference.setOnPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		System.out.println("xxxx");
		if ("language".equals(key)) {
			int language=Integer.parseInt(sharedPreferences.getString("language","-1"));
			languageUtil.changeLocale(language);
			refresh();
			
			//AppSetting.getInstance(this).setLanguage(language);
		}
		
	}
	protected void refresh() {
		Intent refreshIntent=new Intent(this,this.getClass());
		startActivity(refreshIntent);
		finish();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		int language=Integer.parseInt(newValue.toString());
		languageUtil.changeLocale(language);
		//先清空再跳转，否则会结束整个程序
		MyApplication.exit(false);
		Intent toListIntent=new Intent(this, TaskListActivity.class);
		startActivity(toListIntent);
		
		return true;
	}
  
    
}
