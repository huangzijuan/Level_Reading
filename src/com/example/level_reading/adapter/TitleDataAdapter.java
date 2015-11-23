package com.example.level_reading.adapter;

import java.util.List;

import com.example.level_reading.R;
import com.example.level_reading.data.Chapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitleDataAdapter extends BaseAdapter {
	private Context context;
    private List<Chapter> titleDateList;

    public void setTitleDateList(List<Chapter> titleDateList) {
		this.titleDateList = titleDateList;
	}
    
    public TitleDataAdapter(Context context, List<Chapter> titleDateList) {
        this.context = context;
        this.titleDateList = titleDateList;
    }

    @Override
    public int getCount() {
        return titleDateList.size();
    }

    @Override
    public Object getItem(int position) {
        return titleDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_title_data, null);
            holder.tvLesson = (TextView) convertView.findViewById(R.id.tvLesson);
            holder.tvEglishTitle = (TextView) convertView.findViewById(R.id.tvEglishTitle);
            holder.tvChinaTitle = (TextView) convertView.findViewById(R.id.tvChinaTitle);
            
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        Chapter chapter = titleDateList.get(position);
        holder.tvLesson.setText(chapter.getLesson());
        holder.tvEglishTitle.setText(chapter.getE_title());
        holder.tvChinaTitle.setText(chapter.getC_title());
        
        return convertView;
    }

    private class Holder{
        private TextView tvLesson,tvEglishTitle,tvChinaTitle;
    }

}
