package com.example.thedarknight.stackviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by The Dark night on 2015/11/25.
 */
public class MyStackViewAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Integer> mylist = new ArrayList<>();

    private ArrayList<View>    viewlist =  new ArrayList<>();

    public MyStackViewAdapter(Context ctxt,ArrayList<Integer> bitmaps){
        this.context = ctxt;
        this.mylist  = bitmaps;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = new ImageView(context);
        image.setImageResource(mylist.get(position));
        viewlist.add(image);
        return image;
    }

    public View getItemView(int position){
        return viewlist.get(position);
    }

    public ArrayList<View> getAllViews(){
        return viewlist;
    }

    /**
     *  获取当前视图的数量
     */
    public int getViewCount(){
        return viewlist.size();
    }

    /**
     * 暂时移除掉当时的视图
     * @param position
     */
    public void removeItem(int position){
       //mylist.remove(postion);
       viewlist.remove(position);
    }

    /**
     *  退回上一个视图
     * @param position
     */
    public void rebackItem(int position){
        ImageView image = new ImageView(context);
        image.setImageResource(mylist.get(position));
        viewlist.add(image);
    }

    public void clean (){
        viewlist.clear();
    }
}
