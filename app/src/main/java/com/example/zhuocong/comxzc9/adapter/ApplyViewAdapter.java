package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.PersonList;

import java.util.List;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class ApplyViewAdapter extends BaseAdapter {

    private List<PersonList> personListList;
    private LayoutInflater inflater;

    public ApplyViewAdapter(List<PersonList> personListList, Context context){
        this.personListList=personListList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personListList==null?0:personListList.size();
    }

    @Override
    public PersonList getItem(int position) {
        return personListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.personlist, null);
        PersonList personList =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView person_name= (TextView) view.findViewById(R.id.person_name);
        TextView person_friendid=(TextView)view.findViewById(R.id.person_friendid);
        person_name.setText(personList.getName());
        String id=String.valueOf(personList.getByPersonId());
        person_friendid.setText(id);
        Log.d("testRun","id2="+id);

        return view;
    }
}
