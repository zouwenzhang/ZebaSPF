package com.zeba.spf;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeba.spf.annotation.SpfColumn;

public class BaseSpf {
    private static Map<String,List<SpfModelHolder>> mapHolders=new HashMap<String,List<SpfModelHolder>>();
    private static Map<String,Map<String,Object>> mapCopys =new HashMap<String,Map<String,Object>>();
    private static Map<String,List<WeakReference<Object>>> mapObjects=new HashMap<String,List<WeakReference<Object>>>();
    public BaseSpf(){
        if(mapHolders.get(getClass().getName())==null){
            init();
        }else{
            refresh();
        }
        if(mapObjects.get(getClass().getName())==null){
            List<WeakReference<Object>> list=new ArrayList<WeakReference<Object>>();
            mapObjects.put(getClass().getName(),list);
        }
        mapObjects.get(getClass().getName()).add(new WeakReference<Object>(this));
    }

    private void init(){
        List<SpfModelHolder> list=new ArrayList<SpfModelHolder>();
        mapHolders.put(getClass().getName(),list);
        Map<String,Object> map=new HashMap<String,Object>();
        mapCopys.put(getClass().getName(),map);
        Class cls=getClass();
        Field[] fields= cls.getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field f=fields[i];
            if(!isSuppered(f)){
                continue;
            }
            SpfModelHolder holder=null;
            if(f.isAnnotationPresent(SpfColumn.class)){
                SpfColumn spfColumn=f.getAnnotation(SpfColumn.class);
                if(spfColumn.isSave()){
                    holder=new SpfModelHolder(f,spfColumn);
                }
            }else{
                holder=new SpfModelHolder(f,null);
            }
            if(holder!=null){
                list.add(holder);
                initValue(holder,map);
            }
        }
    }

    private boolean isSuppered(Field f){
        if(f.getType()==Integer.class){
            return true;
        }else if(f.getType()==String.class){
            return true;
        }else if(f.getType()==Long.class){
            return true;
        }else if(f.getType()==Float.class){
            return true;
        }else if(f.getType()==Boolean.class){
            return true;
        }
        return false;
    }

    private void initValue(SpfModelHolder holder,Map<String,Object> map){
        try{
            Object value=null;
            if(holder.getField().getType()==Integer.class){
                value= ZebaSPF.getInt(holder.getColumnName(),holder.getDefInt());
            }else if(holder.getField().getType()==Long.class){
                value= ZebaSPF.getLong(holder.getColumnName(),holder.getDefLong());
            }else if(holder.getField().getType()==Float.class){
                value= ZebaSPF.getFloat(holder.getColumnName(),holder.getDefFloat());
            }else if(holder.getField().getType()==Boolean.class){
                value= ZebaSPF.getBoolean(holder.getColumnName(),holder.getDefBoolean());
            }else if(holder.getField().getType()==String.class){
                value= ZebaSPF.getString(holder.getColumnName(),holder.getDefString());
            }
            holder.getField().set(this,value);
            map.put(holder.getField().getName(),value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void refresh(){
        Map<String,Object> map=mapCopys.get(getClass().getName());
        if(map==null){
            return;
        }
        List<SpfModelHolder> list=mapHolders.get(getClass().getName());
        if(list==null){
            return;
        }
        try{
            for(SpfModelHolder holder:list){
                Object value=map.get(holder.getField().getName());
                holder.getField().set(this,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int save(){
        int c=0;
        try {
            List<SpfModelHolder> list=mapHolders.get(getClass().getName());
            Map<String,Object> map=mapCopys.get(getClass().getName());
            List<WeakReference<Object>> listObjects=mapObjects.get(getClass().getName());
            for(SpfModelHolder holder:list){
                Field field=holder.getField();
                Object obj2=field.get(this);
                Object obj3= map.get(holder.getField().getName());
                if(obj2!=null){
                    if(!obj2.equals(obj3)){
                        ZebaSPF.put(holder.getColumnName(),obj2);
                        map.put(holder.getField().getName(),obj2);
                        dataChange(listObjects,holder.getField(),obj2);
                        c++;
                    }
                }else if(obj3!=null){
                    ZebaSPF.remove(holder.getColumnName());
                    map.remove(holder.getField().getName());
                    dataChange(listObjects,holder.getField(),obj2);
                    c++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    private void dataChange(List<WeakReference<Object>> list,Field field,Object value){
        try{
            for(int i=list.size()-1;i>=0;i--){
                WeakReference<Object> weakObj=list.get(i);
                if(weakObj.get()==null){
                    list.remove(i);
                    continue;
                }
                if(!weakObj.get().equals(this)){
                    field.set(weakObj.get(),value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
