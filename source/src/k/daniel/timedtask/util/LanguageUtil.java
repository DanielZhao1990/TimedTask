package k.daniel.timedtask.util;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;

/**
 * A Util to set Locale and Language 
 * @author Daniel
 */
public class LanguageUtil {
    private static final String SEPARATOR = "-";
    Context context;
    private ArrayList<Locale> localeList = new ArrayList<Locale>();
    /**
     * To initial a Util
     * @param context the app context
     * @param resId thes string-array resource Id which store the language code
     */
    public LanguageUtil(Context context, int resId) {
        this.context = context;
        String localeCode[] = context.getResources().getStringArray(resId);
        for (int i = 0; i < localeCode.length; i++) {
            Locale locale = getLocaleByCode(localeCode[i]);
            localeList.add(locale);
        }
    }
    
    /**
     * Get all language names. Please use getDisplayNames()
     * @return
     */
    @Deprecated
    public String[] getLanguageNames() {
        String names[] = new String[localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            names[i] = locale.getDisplayLanguage(locale);
        }
        return names;
    }
    /**
     * Get all Language names as different language.
     * @return
     */
    public String[] getDisplayNames() {
        String names[] = new String[localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            names[i] = locale.getDisplayName(locale);
        }
        return names;
    }
    /**
     * Get Locale By Language code.If you have a country code,please build as zh-CN
     * @param code
     * @return
     */
    public Locale getLocaleByCode(String code) {
        int index;
        if ((index = code.indexOf(SEPARATOR)) != -1) {
            String languageCodeString = code.substring(0, index);
            String countryCodeString = code.substring(index + 1, code.length());
            return new Locale(languageCodeString, countryCodeString);
        } else {
            return new Locale(code);
        }
    }
    /**
     * Change the locale to change language
     * @param position the position in list
     * @param context 
     */
    public void changeLocale(int position) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = localeList.get(position);
        // if change position equals current position ,
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());
        System.out.println("Change Locale");
    }
    /**
     * Get the Locale with the position in Locale ArrayList
     * @param position
     * @return
     */
    public Locale getLocale(int position) {
        // TODO Auto-generated method stub
        return localeList.get(position);
    }
    /**
     * get position by locale
     * @param locale
     * @return
     */
    public int getPosition(Locale locale)
    {
        return localeList.indexOf(locale);
    }
    /**
     * For Preference EntryValues
     * @return
     */
    public String[] getPreferenceEntryValues()
    {
    	String []values=new String[localeList.size()];
    	for (int i = 0; i < localeList.size(); i++) {
			values[i]=i+"";
		}
    	return values;
    }
}    


