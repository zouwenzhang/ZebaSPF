package com.zeba.spf;

import android.app.Application;
import android.content.SharedPreferences;

public class ZebaSPF {
    private static SharedPreferences spf;
    private static SharedPreferences.Editor editor;
    public static void init(Application application,String name,int mode){
        spf=application.getSharedPreferences(name,mode);
        editor=spf.edit();
        spf.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        });
    }

    public static SharedPreferences get(){
        return spf;
    }

    public static void put(String key,Object value){
        SharedPreferences.Editor editor = spf.edit();
        put(editor,key,value);
        editor.apply();
    }

    public static void batchPut(String key,Object value){
        put(editor,key,value);
    }

    public static void batchCommit(){
        editor.apply();
    }

    public static void remove(String key){
        spf.edit().remove(key).apply();
    }
    private static void put(SharedPreferences.Editor editor ,String key,Object value){
        if(value instanceof String){
            editor.putString(key,(String)value);
        }else if(value instanceof Integer){
            editor.putInt(key,(Integer)value);
        }else if(value instanceof Float){
            editor.putFloat(key,(Float)value);
        }else if(value instanceof Long){
            editor.putLong(key,(Long)value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key,(Boolean)value);
        }
    }

    public static String getString(String key,String def){
        return spf.getString(key,def);
    }

    public static Integer getInt(String key,Integer def){
        return spf.getInt(key,def);
    }

    public static Float getFloat(String key,Float def){
        return spf.getFloat(key,def);
    }

    public static Long getLong(String key,Long def){
        return spf.getLong(key,def);
    }

    public static Boolean getBoolean(String key,Boolean def){
        return spf.getBoolean(key,def);
    }
}
