//package com.elvis.magicdevice.MainScreen;
//
//import java.util.zip.Inflater;
//
//
//import android.app.TimePickerDialog;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.ImageView;
//import android.widget.TimePicker;
//import android.os.Parcelable;
//import android.preference.DialogPreference;
//import android.preference.Preference;
//import android.preference.Preference.OnPreferenceClickListener;
//
//public class ItemEditTextPreference extends Preference implements
//        OnPreferenceClickListener {
//
//    private Context context;
//    private View view;
//    private ImageView icon;
//    private TextView summary;
//
//    public ItemEditTextPreference(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        // TODO Auto-generated constructor stub
//        this.context = context;
//        this.setOnPreferenceClickListener(this);
//
//    }
//
//    @Override
//    protected View onCreateView(ViewGroup parent) {
//        // TODO Auto-generated method stub
//        //如果不加这句，下拉的时候界面会被重新刷新，界面上的数据就会丢失
//        if(view!=null)return view;
//
//        view = LayoutInflater.from(getContext()).inflate(
//                R.layout.perference_timerpicker, parent, false);
//        icon = (ImageView) view.findViewById(R.id.image_icon);
//        summary = (TextView) view.findViewById(android.R.id.summary);
//        //android自定义的ID不能再开始时变化所以使用setSummary方法改变
//        this.setSummary(.getString(this, getKey(), "08:00"));
////		summary.setText(PreferenceSaveUtils.getString(this, getKey(), "08:00"));
//        return view;
//    }
//
//
//    @Override
//    public boolean onPreferenceClick(Preference arg0) {
//        // TODO Auto-generated method stub
//        String time = PreferenceSaveUtils.getString(this, getKey(), "8:00");
//        String[] mTime = time.split(":");
//        Integer mHour = Integer.valueOf(mTime[0]);
//        Integer mMinute = Integer.valueOf(mTime[1]);
//        summary.setText((mHour > 9 ? "" + mHour : "0" + mHour) + ":"
//                + (mMinute > 9 ? "" + "" + mMinute : "0" + mMinute));
//
//
//
//        new Ed
//
//        new TimePickerDialog(this.context,
//                new android.app.TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int mHour,
//                                          int mMinute) {
//                        // TODO Auto-generated method stub
//                        String time = (mHour > 9 ? "" + mHour : "0" + mHour)
//                                + ":"
//                                + (mMinute > 9 ? "" + "" + mMinute : "0"
//                                + mMinute);
//                        PreferenceSaveUtils.putString(
//                                PreferenceSelectTimerDialog.this, getKey(),
//                                time);
//
//                        summary.setText(time);
//                    }
//                }, mHour, mMinute, true).show();
//
//        return false;
//    }
//
//}
