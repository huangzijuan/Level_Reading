package com.example.level_reading.ui;

import java.util.ArrayList;

import com.example.level_reading.R;
import com.example.level_reading.data.Chapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleInfoActivity extends Activity {
	
	private TextView tvLesson, tvTitle1, tvTitle2, tvContent;
	private Chapter chapter;
	private ImageView btnTitleLeft;
	//侧滑菜单
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_info);
		if (this.getIntent() != null) {
            if (null != this.getIntent().getExtras()) {
            	chapter = (Chapter) this.getIntent().getSerializableExtra("articleInfo");
            }
        }
        init();
	}
	
	private void init() {
		tvLesson = (TextView) findViewById(R.id.tvlesson);
		tvLesson.setText(chapter.getLesson());
		tvTitle1 = (TextView) findViewById(R.id.tvtitle1);
		tvTitle1.setText(chapter.getE_title());
		tvTitle2 = (TextView) findViewById(R.id.tvtitle2);
		tvTitle2.setText(chapter.getC_title());
		tvContent = (TextView) findViewById(R.id.tvcontent);
		tvContent.setText(chapter.getContent());
		
		//侧滑菜单
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		for (int i = 0; i < 7; i++)
			menuLists.add("level " + i);
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, menuLists);
		mDrawerList.setAdapter(adapter);
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mDrawerLayout.closeDrawer(GravityCompat.START);
			}
		});
		
		btnTitleLeft = (ImageView) findViewById(R.id.btn_title_left);
		btnTitleLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
		});
		
	}

}
