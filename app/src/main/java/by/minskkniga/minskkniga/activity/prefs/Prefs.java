package by.minskkniga.minskkniga.activity.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    Context context;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_SESSION_ID_SCHET = "session_id_sched";
    private static final String APP_PREFERENCES_SESSION_NAME_SCHET = "session_name_sched";
    private static final String APP_PREFERENCES_SESSION_ID_OPER = "session_id_oper";
    private static final String APP_PREFERENCES_HOST = "host";
    private SharedPreferences mSettings;

    public Prefs(Context context) {
        this.context = context;
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String  getSessionIdSchet(){
        return mSettings.getString(APP_PREFERENCES_SESSION_ID_SCHET,"0");
    }

    public void setSessionIdSchet(String id){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_SESSION_ID_SCHET, id);
        editor.apply();
    }

    public String  getSessionIdOper(){
        return mSettings.getString(APP_PREFERENCES_SESSION_ID_SCHET,"0");
    }

    public void setSessionIdOper(String id){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_SESSION_ID_SCHET, id);
        editor.apply();
    }

    public String  getSeshet(){
        return mSettings.getString(APP_PREFERENCES_SESSION_NAME_SCHET,"0");
    }

    public void setSchet(String id){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_SESSION_NAME_SCHET, id);
        editor.apply();
    }

    public String  getHost(){
        return mSettings.getString(APP_PREFERENCES_HOST,"");
    }

    public void setHost(String host){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_HOST, host);
        editor.apply();
    }

}
