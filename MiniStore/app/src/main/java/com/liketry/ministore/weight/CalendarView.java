package com.liketry.ministore.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liketry.ministore.R;
import com.liketry.ministore.utils.MobileUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author Simon
 * create 2017/10/12
 * 日历控件
 */

public class CalendarView extends RecyclerView implements View.OnClickListener {
    private RecyclerView.Adapter adapter;
    private LayoutInflater inflater;
    private final int startYear = 2000;
    private final int endYear = 2100;
    private int total;
    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private Calendar tempCalendar = Calendar.getInstance();
    private Date startTime;//起始时间
    private Date endTime;//结束时间
    private String startTimeTxt;//起始时间
    private String endTimeTxt;//结束时间
    private int fixYear;
    private int fixMonth;
    private int fixDay;
    private int selectColor;
    private int commonColor;
    private OnCalendarListener listener;

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        total = (endYear - startYear + 1) * 12;
        inflater = LayoutInflater.from(context);
        adapter = new CalendarAdapter();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        setLayoutManager(layoutManager);
        setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();
        fixYear = calendar.get(Calendar.YEAR);
        fixMonth = calendar.get(Calendar.MONTH) + 1;
        fixDay = calendar.get(Calendar.DAY_OF_MONTH);
        int position = (fixYear - startYear) * 12;
        CalendarView.this.scrollToPosition(position + fixMonth - 1);
        selectColor = ContextCompat.getColor(context, R.color.white);
        commonColor = ContextCompat.getColor(context, R.color.calendar_common);


    }

    private class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.view_calendar, parent, false);
            LinearLayout centerView = rootView.findViewById(R.id.ll_view_calendar_centerview);
            centerView.getLayoutParams().width = MobileUtil.toPx(context, 45 * 7);
            return new Holder(rootView);
        }

        private int day[];

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            int year = startYear + position / 12;
            int month = position % 12 + 1;
            String titleContent = month == 1 ? year + "年 " + month + "月" : month + "月";
            holder.monthTxt.setText(titleContent);
            day = new int[42];
            calendar.set(year, month - 1, 1);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            week = week == 0 ? 7 : week;
            int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int index = 1;
            for (int i = week - 1; index <= daySize; i++) {
                day[i] = index++;
            }
            for (int i = 0; i < 42; i++) {
                LinearLayout weekLayout;
                switch (i / 7) {
                    case 0:
                        weekLayout = holder.week1LL;
                        break;
                    case 1:
                        weekLayout = holder.week2LL;
                        break;
                    case 2:
                        weekLayout = holder.week3LL;
                        break;
                    case 3:
                        weekLayout = holder.week4LL;
                        break;
                    case 4:
                        weekLayout = holder.week5LL;
                        break;
                    default:
                        weekLayout = holder.week6LL;
                        break;
                }
                int currentDay = day[i];
                RelativeLayout itemView = (RelativeLayout) weekLayout.getChildAt(i % 7);
                TextView dateView = (TextView) itemView.getChildAt(0);
                if (currentDay == 0) {
                    dateView.setText("");
                } else {
                    dateView.setTextColor(commonColor);
                    if (year == fixYear && month == fixMonth && fixDay == currentDay)
                        dateView.setText("今天");
                    else
                        dateView.setText(String.valueOf(currentDay));
                }
                itemView.setTag(R.id.tag_first, year + "-" + month + "-" + currentDay);
                itemView.setOnClickListener(CalendarView.this);
                if (startTime != null && startTimeTxt.equals(year + "-" + month + "-" + currentDay)) {
                    itemView.setBackgroundResource(endTime == null || startTimeTxt.equals(endTimeTxt) ? R.mipmap.view_time_select : R.mipmap.view_starttime_select);
                    dateView.setTextColor(selectColor);
                } else if (endTime != null && endTimeTxt.equals(year + "-" + month + "-" + currentDay)) {
                    itemView.setBackgroundResource(R.mipmap.view_endtime_select);
                    dateView.setTextColor(selectColor);
                } else if (startTime != null && endTime != null) {
                    tempCalendar.set(year, month - 1, currentDay);
                    long current = tempCalendar.getTimeInMillis();
                    if (current > startTime.getTime() && current < endTime.getTime() && currentDay != 0) {
                        itemView.setBackgroundResource(R.mipmap.view_time_include);
                        dateView.setTextColor(selectColor);
                    } else {
                        itemView.setBackgroundResource(0);
                    }
                } else {
                    itemView.setBackgroundResource(0);
                }
            }
        }

        @Override
        public int getItemCount() {
            return total;
        }

        class Holder extends ViewHolder {
            TextView monthTxt;
            LinearLayout week1LL;
            LinearLayout week2LL;
            LinearLayout week3LL;
            LinearLayout week4LL;
            LinearLayout week5LL;
            LinearLayout week6LL;

            Holder(LinearLayout root) {
                super(root);
                monthTxt = root.findViewById(R.id.txt_view_calendar_month);
                week1LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(1);
                week2LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(2);
                week3LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(3);
                week4LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(4);
                week5LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(5);
                week6LL = (LinearLayout) ((LinearLayout) root.getChildAt(0)).getChildAt(6);
            }
        }
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Override
    public void onClick(View view) {
        try {
            RelativeLayout itemView = (RelativeLayout) view;
            String dayText = ((TextView) itemView.getChildAt(0)).getText().toString();
            if (TextUtils.isEmpty(dayText))
                return;
            if (startTime == null || endTime != null) {
                startTimeTxt = itemView.getTag(R.id.tag_first).toString();
                startTime = format.parse(startTimeTxt);
                endTime = null;
                listener.start(startTimeTxt);
            } else {
                endTimeTxt = itemView.getTag(R.id.tag_first).toString();
                endTime = format.parse(endTimeTxt);
                if (endTime.getTime() - startTime.getTime() < 0) {
                    Date tempDate = startTime;
                    String tempDateStr = startTimeTxt;
                    startTime = endTime;
                    startTimeTxt = endTimeTxt;
                    endTime = tempDate;
                    endTimeTxt = tempDateStr;
                }
                listener.start(startTimeTxt);
                listener.end(endTimeTxt);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnCalendarListener(OnCalendarListener listener) {
        this.listener = listener;
    }

    interface OnCalendarListener {
        void start(String startDate);

        void end(String endDate);
    }
}