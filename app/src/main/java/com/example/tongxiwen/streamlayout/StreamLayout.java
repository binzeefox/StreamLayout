package com.example.tongxiwen.streamlayout;

import android.content.Context;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tong.xiwen on 2017/8/9.
 */
public class StreamLayout extends LinearLayout {

    public static final int LEFT_LIST = 0;
    public static final int CENTER_LIST = 1;
    public static final int RIGHT_LIST = 2;

    private ListView mListView1;
    private ListView mListView2;
    private ListView mListView3;

    public String[] str;
    public List<String> str1;

    private ArrayAdapter adapter1;
    private ArrayAdapter adapter2;
    private ArrayAdapter adapter3;

    private LayoutParams params;
    private OnListItemClickListener listener;


    public StreamLayout(Context context) {
        this(context, null, 0);
    }

    public StreamLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StreamLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);

        str = new String[20];
        for (int i = 0; i < 20; i++) {
            str[i] = String.valueOf(i);
        }
        str1 = new ArrayList<>();
        str1.addAll(Arrays.asList(str));

        adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, str1);
        adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, str1);
        adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, str1);

        mListView1 = new ListView(getContext());
        mListView2 = new ListView(getContext());
        mListView3 = new ListView(getContext());

        this.addView(mListView1);
        this.addView(mListView2);
        this.addView(mListView3);


        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_LONG).show();
                listener.onClick(LEFT_LIST, i);
            }
        });
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_LONG).show();
                listener.onClick(CENTER_LIST, i);
            }
        });
        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_LONG).show();
                listener.onClick(RIGHT_LIST, i);
            }
        });

        params = new LayoutParams(0, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        params.width = MeasureSpec.getSize(widthMeasureSpec) / 3;
        params.height = MeasureSpec.getSize(heightMeasureSpec);

        mListView1.setAdapter(adapter1);
        mListView2.setAdapter(adapter2);
        mListView3.setAdapter(adapter3);

        mListView1.setLayoutParams(params);
        mListView2.setLayoutParams(params);
        mListView3.setLayoutParams(params);
    }

    /**
     * 全部拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int listHeight = mListView1.getHeight();
        Region regionT = new Region(mListView2.getLeft(), mListView2.getTop(), mListView2.getRight(), mListView2.getBottom() - listHeight / 2);

        if (regionT.contains((int) ev.getX(), (int) ev.getY())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int listHeight = mListView1.getHeight();
        Region regionT = new Region(mListView2.getLeft(), mListView2.getTop(), mListView2.getRight(), mListView2.getBottom() - listHeight / 2);

        int xDown = (int) event.getX();
        int yDown = (int) event.getY();

        event.setLocation(mListView1.getWidth()/2, yDown);

        if (event.getAction() == MotionEvent.ACTION_UP){

            MotionEvent cancelEvent = MotionEvent.obtain(event);
            cancelEvent.setAction(MotionEvent.ACTION_CANCEL);

            mListView2.dispatchTouchEvent(event);
            mListView1.dispatchTouchEvent(cancelEvent);
            mListView3.dispatchTouchEvent(cancelEvent);
        } else if (regionT.contains(xDown, yDown)) {
            mListView2.dispatchTouchEvent(event);
            mListView1.dispatchTouchEvent(event);
            mListView3.dispatchTouchEvent(event);
            return true;
        }

        return true;
    }

    /**
     * 获取listView实例
     * @return
     */
    public ListView getmListView1() {
        return mListView1;
    }

    public ListView getmListView2() {
        return mListView2;
    }

    public ListView getmListView3() {
        return mListView3;
    }

    /**
     * 设置三个listView 的适配器
     * @param adapter1  左侧
     * @param adapter2  中间
     * @param adapter3  右侧
     */
    public void setAdapters(ListAdapter adapter1, ListAdapter adapter2, ListAdapter adapter3){
        if (adapter1 != null){
            this.adapter1 = (ArrayAdapter) adapter1;
        }
        if (adapter2 != null){
            this.adapter2 = (ArrayAdapter) adapter2;
        }
        if (adapter3 != null){
            this.adapter3 = (ArrayAdapter) adapter3;
        }
    }

    /**
     * 监听器
     */
    public interface OnListItemClickListener{
        abstract void onClick(int position1, int position2);
    }

    /**
     * 设置监听器
     * @param listener 监听器
     */
    public void setOnListItemClickListener(OnListItemClickListener listener){
        this.listener = listener;
    }
}
