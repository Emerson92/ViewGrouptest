package com.example.thedarknight.stackviewtest;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
/**
 * Created by The Dark night on 2015/11/24.
 */
public class MyStackView extends FrameLayout {

    private MyStackViewAdapter adapte;

    private int widthSize;

    private int heightSize;

    private Context ctxt;

    /**
     * 手势触碰的点
     */
    private float firstX = 0;

    private float firstY = 0;

    private float lastX  = 0;

    private float lastY  = 0;


    private GestureDetector myDetector;
    /**
     *   动画
     *
     */
    private Animation alpha_animation;
    /**
     *   缓存视图
     *
     */
    private View cache_view;
    /**
     *   View临时视图
     */
    private Bitmap bitmap;

    /**
     *   画笔
     */
    private Paint bitmappanit;
    private int move = 0;
    /**
     *  左边的距离
     */
    private int Bitmap_left;
    /**
     *  右边的距离
     */
    private int Bitmap_right;

    /**
     * 距离顶上的距离
     */
    private int Bitmap_top;

    /**
     * 距离底部的距离
     */
    private int Bitmap_Bottom;

    /**
     *  Bitmap形状着色器
     *
     */
    private BitmapShader mySharder;

    private int movedistance = 300;


    public MyStackView(Context context) {
        super(context);
        this.ctxt = context;
        ImageView image1 = new ImageView(context);
        image1.setImageResource(R.drawable.batman);
        ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(150,150);
        addViewInLayout(image1, 0, params);
        myDetector = new GestureDetector(context,onGestureListener);
    }



    public MyStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctxt = context;
        myDetector = new GestureDetector(context,onGestureListener);
    }

    public MyStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctxt = context;
        myDetector = new GestureDetector(context,onGestureListener);
        //initPanit();
    }

    /**
     *  初始化画笔
     */
    private void initPanit() {
        bitmappanit = new Paint();
        bitmappanit.setAntiAlias(true);
        bitmappanit.setStrokeWidth(10);
        bitmappanit.setShader(mySharder);
        bitmappanit.setStyle(Paint.Style.FILL);
        bitmappanit.setColor(Color.BLUE);
    }


    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }


    public void setAdapte(MyStackViewAdapter adapte){
        this.adapte = adapte;
        addView(adapte);
    }

    private void addView(MyStackViewAdapter adapte) {
        for(int i = 0; i<adapte.getCount();i++){
            View v = new View(ctxt);
            adapte.getView(i,v,this);
        }
        for(int i = 0; i<adapte.getCount();i++){
            addView(adapte.getItemView(i));
        }
    }

    /**
     *
     *  计算所有ChildView的宽度和高度,然后根据ChildView的计算结果,设置自己的宽和高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取子视图的数量
        int count = getChildCount();
        if( count == 0 ) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        /**
         *  获取此ViewGroup上级容器为其推荐的宽度和高度，以及计算模式。
         */
        int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
        widthSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //initBitampPositionData();

        //measureChildren(widthMeasureSpec,heightMeasureSpec);

        int ChildHeight = 0;
        int ChildWidth  = 0;
        /**
         *  子视图为warp_content时候，计算子视图的高度。
         */
        for( int i=0 ; i<count ; i++ ) {
            View ChildernView = getChildAt(i);
            /**
             *  测量子视图的容器宽度和大小
             */
            measureChild(ChildernView,widthMeasureSpec,heightMeasureSpec);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    /**
     *   初始化Bitmap图像的位置
     *
     * @param bitmap
     */
    private void initBitampPositionData(Bitmap bitmap) {
        int bmpwitdh  = bitmap.getWidth();
        int bmpheight = bitmap.getHeight();

        float scale = 1.0f;
        Bitmap_left   = widthSize/6;
        Bitmap_right  = widthSize*5/6;
        Bitmap_top    = heightSize/6;
        Bitmap_Bottom = heightSize*5/6;
        movedistance = (Bitmap_Bottom - Bitmap_top)/2;
        scale = Math.max((Bitmap_right - Bitmap_left) * 1.0f / bmpwitdh, (Bitmap_Bottom - Bitmap_top) * 1.0f / bmpheight);
        Matrix myMatrix = new Matrix();
        myMatrix.setScale(scale,scale);
        mySharder.setLocalMatrix(myMatrix);
    }

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    if (x > 0) {
                         Log.e("Mytest","hello right");
                    }else{
                        Log.e("Mytest", "hello left");
                    }
                    return true;
                }
            };
    /**
     *  触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Log.e("Mytest","MotionEvent.ACTION_DOWN");
                firstX = event.getX();
                firstY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                move = (int)(event.getY() - firstY);
                if(bitmap == null){
                    cache_view = this.getChildAt(adapte.getViewCount() - 1);
                    //cache_view = (adapte.getAllViews()).get(adapte.getViewCount() - 1);
                    if(!cache_view.isDrawingCacheEnabled()) {
                        cache_view.setDrawingCacheEnabled(true);
                        bitmap = cache_view.getDrawingCache();
                    }
                    initSharder();
                    initBitampPositionData(bitmap);
                    initPanit();
                }
                if( move > 0 ) {
                    Log.e("Mytest", "Alpha :" + (int) ((Math.abs(move) * 1.0f / movedistance) * 100));
                }
                /**
                 *  刷新Layout事件
                */
                requestLayout();
                //bitmappanit.setAlpha(100 - (int)((Math.abs(move)*1.0f/movedistance)*100));
                //Log.e("Mytest","MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:

                lastX = event.getX();
                lastY = event.getY();

                if( lastY - firstY > movedistance ){
                    //do something here.
                    move = 0;
                    cache_view.destroyDrawingCache();
                    bitmap.recycle();
                    cache_view = null;
                }
                if( lastX - firstX > 150 ){
                    Log.e("Mytest", "hello right");
                    if( adapte.getViewCount() != 1 ) {
                        Animation alpha_animation_translation = setanimation(R.anim.anim_translation);
                        alpha_animation_translation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                removerefreshView();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        this.getChildAt(adapte.getViewCount() - 1).startAnimation(alpha_animation_translation);
                        adapte.removeItem(adapte.getViewCount() - 1);
                    }

                }else if(firstX - lastX > 150 ){
                    Log.e("Mytest", "hello left");
                    if(adapte.getViewCount() != adapte.getCount()) {
                        Animation alpha_animation_alpha = setanimation(R.anim.anim_alpha);
                        alpha_animation_alpha.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                addrefreshView();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        adapte.rebackItem(adapte.getViewCount());
                        addView(adapte.getAllViews().get(adapte.getViewCount()-1));
                        View view= this.getChildAt(adapte.getViewCount()-1);
                        view.startAnimation(alpha_animation_alpha);
                    }
                }
            clearPoint();
                //Log.e("Mytest","MotionEvent.ACTION_UP");
                break;
            default:
                clearPoint();
        }
        return true;
    }


    /**
     *  初始化着色器
     *
     */
    private void initSharder() {
        if(bitmap != null ){
             mySharder = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        }
    }

    /**
     *   添加刷新函数
    *
            */
    private void addrefreshView() {
        /**
         *  刷新Layout事件
         */
        requestLayout();
    }

    /**
     *
     */
    public Animation setanimation(int Res){
        Animation animation= AnimationUtils.loadAnimation(ctxt,Res);
        return animation;
    }
    /**
     *  重新刷新视图
     */
    private void removerefreshView() {
        //移除所有视图
        removeAllViewsInLayout();
        //重新添加新的视图
        for(int i = 0; i<adapte.getViewCount();i++){
            addView(adapte.getItemView(i));
        }
        /**
         *  刷新Layout事件
         */
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //onLayout(false, 0, 0, 0, 0);
        if(cache_view != null) {

            if( bitmap != null ){
                //canvas.drawBitmap(bitmap,50,50,bitmappanit);
            }
            canvas.drawRect(Bitmap_left+move,Bitmap_top+move,Bitmap_right-move,Bitmap_Bottom-move,bitmappanit);
            //canvas.drawRect(180,180,400,400,bitmappanit);
            //Log.e("Mytest", "fresh onDraw");
        }
    }

    /**
     *  清除触摸点的值
     */
    private void clearPoint() {
        lastX  = 0;
        lastY  = 0;
        firstX = 0;
        firstY = 0;
    }

    /**
     *  让ViewGroup来处理滑动事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

    }


    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        super.setOnHierarchyChangeListener(listener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("Mytest", "fresh onLayout");
        Log.e("Mytest", "fresh onLayout"+changed);
        int offset = 10;
        int count   = getChildCount();
        if( bitmap != null ){
            count = count-1;
        }
        int fWidth  = widthSize;//父容器的宽度
        int fHeigth = heightSize;//父容器的高度
        int essional_width  = 0;
        int essional_height = 0;
        for(int i =0; i<count ; i++){
           View view           = getChildAt(i);
           int cWitdh          = view.getMeasuredWidth();//子容器的宽度
           int cHeight         = view.getMeasuredHeight();//子容器的高度
           if( i == 0 ){
               essional_width   = ( fWidth - cWitdh )/2 ;
               essional_height  = ( fHeigth - cHeight )/2 ;
               if(essional_width == 0){
                   essional_width  = widthSize/3;
               }
               if(essional_height == 0){
                   essional_height = heightSize/3;
               }
           }
           int top             = essional_height+offset*i;
           int left            = essional_width+offset*i;
           int width           = left+cWitdh+offset*i;
           int height          = top+cHeight+offset*i;
           if( width >= widthSize ){
               width = (widthSize)*2/3;
               width = width + offset*i;
           }
           if( height >= heightSize){
               height = (heightSize)*2/3;
               height = height + +offset*i;
           }
           view.layout(left,top,width,height);
        }
    }
}
