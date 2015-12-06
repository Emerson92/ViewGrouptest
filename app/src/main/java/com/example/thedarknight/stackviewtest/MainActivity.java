package com.example.thedarknight.stackviewtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private StackView mystack;
    private Button pre_button;
    private Button next_button;

    //private ArrayList<Integer> mylist = new ArrayList<>();
    private int[] ImageView = {
            R.drawable.batman,
            R.drawable.huke,
            R.drawable.ironman,
            R.drawable.sorn
    };
    private MyStackView mystackView;

    private List<HashMap<String,Object>> mylist = new ArrayList<HashMap<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView image1 = new ImageView(this);
//        image1.setImageResource(R.drawable.batman);
//        MyStackView myStackView= new MyStackView(this);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(480, 800);
//        myStackView.setLayoutParams(params);
//        myStackView.setBackgroundResource(R.drawable.welcome_bg);
        /**
        for(int i=0; i < 4 ; i++ ){
            mylist.add(ImageView[i]);
        }
        setContentView(R.layout.activity_main);
        mystackView = (MyStackView)findViewById(R.id.mystackView);
        MyStackViewAdapter adapter = new MyStackViewAdapter(this,mylist);
        mystackView.setAdapte(adapter);
         **/
        //ListView
        //mystackView.setAdapte();
        initview();
        //setOnclicklister();
        initData();
    }

    private void initData() {
        for(int i =0;i<ImageView.length;i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("image", ImageView[i]);
            mylist.add(map);
        }
        SimpleAdapter myadapter = new  SimpleAdapter(
                this,
                mylist,
                R.layout.activity_item,
                new String[]{"image"},
                new int[]{R.id.imageView}
        );
        mystack.setAdapter(myadapter);
    }

//    private void setOnclicklister() {
//        pre_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mystack.showPrevious();
//            }
//        });
//        next_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mystack.showNext();
//            }
//        });
//    }
//
    private void initview() {
        mystack     = (StackView)findViewById(R.id.mystackview);
//        pre_button  = (Button)findViewById(R.id.pre_button);
//        next_button = (Button)findViewById(R.id.next_button);
    }
}
