package com.example.prem.firstpitch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ListView;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Prem on 15-Aug-16.
 */
public class MyMonthView extends ListView {

    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_NUM_DAYS = 4;
    private static final int DEFAULT_NUM_ROWS = 3;
    private static final int MAX_NUM_ROWS = 3;
    private static final int DAY_SEPARATOR_WIDTH = 1;
    private StringBuilder mStringBuilder;
    private int mMiniDayNumberTextSize;
    private int mMonthLabelTextSize;
    private int mMonthDayLabelTextSize;
    private int mMonthHeaderSize;
    private int mDaySelectedCircleSize;
    // affects the padding on the sides of this view
    private int mPadding = 40;
    private Paint mDayNumberPaint;
    private Paint mDayNumberDisabledPaint;
    private Paint mDayNumberSelectedPaint;
    private Paint mMonthTitlePaint;
    private Paint mMonthDayLabelPaint;
    private int mMonth;
    private int mYear;
    // Quick reference to the width of this view, matches parent
    private int mWidth;
    // The height this view should draw at in pixels, set by height param
    private int mRowHeight = DEFAULT_HEIGHT;
    // If this view contains the today
    private boolean mHasToday = false;
    // Which day is selected [0-6] or -1 if no day is selected
    private int mSelectedMonth = -1;
    // Which day is today [0-6] or -1 if no day is today
    private int mToday = DEFAULT_SELECTED_DAY;
    // Which day of the week to start on [0-6]
    // How many days to display
    private int mNumDays = DEFAULT_NUM_DAYS;
    // The number of days + a spot for week number if it is displayed
    private int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    // private  MyMonthViewTouchHelper mTouchHelper;
    private int mNumRows = DEFAULT_NUM_ROWS;
    // Optional listener for handling day click actions
    private OnDayClickListener mOnDayClickListener;
    // Whether to prevent setting the accessibility delegate
    private int monthBgColor;
    private int monthBgSelectedColor;
    private int monthFontColorNormal;
    private int monthFontColorSelected;
    private int monthFontColorDisabled;
    private int currMonth, maxMonth, minMonth;
    private int _enableMonthStart, _enableMonthEnd;
    private int mRowHeightKey;
    private Context context;
    private String[] monthNames = getResources().getStringArray(R.array.months);

    public MyMonthView(Context context) {
        this(context, null);
    }

    public MyMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.TestView);
    }

    public MyMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Log.d("TestView ", " attrs : " + attrs.toString() + " defStyleAttr: " + defStyleAttr);
        /*TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.monthPickerDialog, defStyleAttr, 0);
        monthBgColor = a.getColor(R.styleable.monthPickerDialog_monthBgColor, 0);
        monthBgSelectedColor = a.getColor(R.styleable.monthPickerDialog_monthBgSelectedColor, 0);
        monthFontColorNormal = a.getColor(R.styleable.monthPickerDialog_monthFontColorNormal, 0);
        monthFontColorSelected = a.getColor(R.styleable.monthPickerDialog_monthFontColorSelected, 0);
        monthFontColorDisabled = a.getColor(R.styleable.monthPickerDialog_monthFontColorDisabled, 0);*/

        /*monthBgColor = context.getResources().getColor(R.color.monthBgColor);
        monthBgSelectedColor = context.getResources().getColor(R.color.monthBgSelectedColor);
        monthFontColorNormal = context.getResources().getColor(R.color.monthFontColorNormal);
        monthFontColorSelected = context.getResources().getColor(R.color.monthFontColorSelected);
        monthFontColorDisabled = context.getResources().getColor(R.color.monthFontColorDisabled);*/

        // check resource exists or not...


      //  Log.d("TestView", " " + monthBgColor + " " + monthBgSelectedColor + " " + monthFontColorDisabled + " " + monthFontColorNormal + " " + monthFontColorSelected);

       /* int checkExistence = context.getResources().getIdentifier("monthBgColor", "color", context.getPackageName());
        if (checkExistence != 0) {  // the resouce exists...
            Log.d("-----------", "monthBgColor exists");
        } else {  // checkExistence == 0  // the resouce does NOT exist!!
            Log.d("-----------", "monthBgColor exists");
        }
        */
        // a.recycle();
       /* setUpListView();
        setAdapter(mAdapter);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));*/
        this.context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        mMiniDayNumberTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, displayMetrics);
        mMonthLabelTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, displayMetrics);
        mMonthDayLabelTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, displayMetrics);
        mMonthHeaderSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, displayMetrics);
        mDaySelectedCircleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                43, displayMetrics);
        mRowHeightKey = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                250, displayMetrics);
        mRowHeight = (mRowHeightKey - mMonthHeaderSize) / MAX_NUM_ROWS;

        mPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, displayMetrics);

        Log.d("init done", "mPadding = " + mPadding + " mMiniDayNumberTextSize = " + mMiniDayNumberTextSize + " mMonthLabelTextSize = " + mMonthLabelTextSize + " mMonthDayLabelTextSize = " + mMonthDayLabelTextSize
                + "mMonthHeaderSize = " + mMonthHeaderSize + " mDaySelectedCircleSize = " + mDaySelectedCircleSize + " mRowHeightKey = " + mRowHeightKey + " mRowHeight = " + mRowHeight);

        // Sets up any standard paints that will be used
    }

    private void setUpListView() {
        setCacheColorHint(0);
        setDivider(null);
        setItemsCanFocus(true);
        setFastScrollEnabled(false);
        setVerticalScrollBarEnabled(false);
        setFadingEdgeLength(0);
    }

    /**
     * Sets up the text and style properties for painting.
     */
    private void initView() {

        mDayNumberSelectedPaint = new Paint();
        mDayNumberSelectedPaint.setAntiAlias(true);
        mDayNumberSelectedPaint.setColor(monthBgSelectedColor);
       // mDayNumberSelectedPaint.setAlpha(200);
        mDayNumberSelectedPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberSelectedPaint.setStyle(Paint.Style.FILL);
        mDayNumberSelectedPaint.setFakeBoldText(true);

        mDayNumberPaint = new Paint();
        mDayNumberPaint.setAntiAlias(true);
        mDayNumberPaint.setColor(monthFontColorNormal);
        mDayNumberPaint.setTextSize(mMiniDayNumberTextSize);
        mDayNumberPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberPaint.setStyle(Paint.Style.FILL);
        mDayNumberPaint.setFakeBoldText(false);

        mDayNumberDisabledPaint = new Paint();
        mDayNumberDisabledPaint.setAntiAlias(true);
        mDayNumberDisabledPaint.setColor(monthFontColorDisabled);
        mDayNumberDisabledPaint.setTextSize(mMiniDayNumberTextSize);
        mDayNumberDisabledPaint.setTextAlign(Paint.Align.CENTER);
        mDayNumberDisabledPaint.setStyle(Paint.Style.FILL);
        mDayNumberDisabledPaint.setFakeBoldText(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDays(canvas);
    }

    /**
     * Draws the month days.
     */
    private void drawDays(Canvas canvas) {
        int y = (((mRowHeight + mMiniDayNumberTextSize) / 2) - DAY_SEPARATOR_WIDTH) + mMonthHeaderSize;
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);
        int j = 0;
        for (int month = 0; month < monthNames.length; month++) {
            //  Log.d("-------loop", " i = "+month+", monthNames[i] = "+ monthNames[month] +" , mSelectedMonth "+mSelectedMonth );
            int x = (2 * j + 1) * dayWidthHalf + mPadding;
            if (mSelectedMonth == month) {
                canvas.drawCircle(x, y - (mMiniDayNumberTextSize / 3), mDaySelectedCircleSize, mDayNumberSelectedPaint);
                mDayNumberPaint.setColor(monthFontColorSelected);
            }else{
                mDayNumberPaint.setColor(monthFontColorNormal);
            }

            final Paint paint = (month < minMonth || month > maxMonth) ?
                    mDayNumberDisabledPaint : mDayNumberPaint;
            canvas.drawText(monthNames[month], x, y, paint);
            j++;
            if (j == mNumDays) {
                j = 0;
                y += mRowHeight;
            }
        }
    }


    /**
     * Calculates the day that the given x position is in, accounting for week
     * number. Returns the day or -1 if the position wasn't in a day.
     *
     * @param x The x position of the touch event
     * @return The day number, or -1 if the position wasn't in a day
     */
    private int getDayFromLocation(float x, float y) {
        int dayStart = mPadding;
        if (x < dayStart || x > mWidth - mPadding) {
            return -1;
        }
        // Selection is (x - start) / (pixels/day) == (x -s) * day / pixels
        int row = (int) (y - mMonthHeaderSize) / mRowHeight;
        int column = (int) ((x - dayStart) * mNumDays / (mWidth - dayStart - mPadding));
        int day = column + 1;
        day += row * mNumDays;
        if (day < 0 || day > mNumCells) {
            return -1;
        }
        Log.d("------------------", "clicked on " + day);
        return day;
    }

    /**
     * Called when the user clicks on a day. Handles callbacks to the
     * {@link OnDayClickListener} if one is set.
     *
     * @param day The day that was clicked
     */
    private void onDayClick(int day) {
        Log.d("----------", "inside onDayClick");
        if (mOnDayClickListener != null) {
            Log.d("---------", "clicked date : " + day);
            mOnDayClickListener.onDayClick(this, day);
        } else {
            Log.d("---------- :-(", "mOnDayClickListener == null");
        }
    }

    protected void setColors(HashMap<String, Integer> colors) {
        Log.d("MonthView -> ", "colors size : " + colors.size());
        if (colors.containsKey("monthBgColor"))
            monthBgColor = colors.get("monthBgColor");
        if (colors.containsKey("monthBgSelectedColor"))
            monthBgSelectedColor = colors.get("monthBgSelectedColor");
        if (colors.containsKey("monthFontColorNormal"))
            monthFontColorNormal = colors.get("monthFontColorNormal");
        if (colors.containsKey("monthFontColorSelected"))
            monthFontColorSelected = colors.get("monthFontColorSelected");
        if (colors.containsKey("monthFontColorDisabled"))
            monthFontColorDisabled = colors.get("monthFontColorDisabled");
        Log.d("YOOYOOOY", " monthBgColor:  " + monthBgColor + " monthBgSelectedColor:  " + monthBgSelectedColor + " monthFontColorDisabled : " + monthFontColorDisabled + " monthFontColorNormal : " + monthFontColorNormal + " monthFontColorSelected: " + monthFontColorSelected);
        initView();
    }

    /**
     * Handles callbacks when the user clicks on a time object.
     */
    public interface OnDayClickListener {
        void onDayClick(MyMonthView view, int month);
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        mOnDayClickListener = listener;
    }

    void setMonthParams(int selectedMonth, int minMonth, int maxMonth) {
        mSelectedMonth = selectedMonth;
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        mNumCells = 12;

    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows
                + (mMonthHeaderSize * 2));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                final int day = getDayFromLocation(event.getX(), event.getY());
                if (day >= 0) {
                    Log.d("yes----------", "day grater then 0");
                    onDayClick(day);
                } else {
                    Log.d("Nooo----------", "day is 0");
                }
                break;
        }
        return true;
    }

    private static boolean isValidDayOfWeek(int day) {
        return day >= Calendar.SUNDAY && day <= Calendar.SATURDAY;
    }

    private static boolean isValidMonth(int month) {
        return month >= Calendar.JANUARY && month <= Calendar.DECEMBER;
    }

    private boolean sameDay(int day, Time today) {
        return mYear == today.year &&
                mMonth == today.month &&
                day == today.monthDay;
    }
}