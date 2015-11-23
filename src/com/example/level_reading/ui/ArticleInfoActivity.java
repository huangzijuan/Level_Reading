package com.example.level_reading.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.level_reading.R;
import com.example.level_reading.data.Chapter;

public class ArticleInfoActivity extends Activity {

	private TextView tvLesson, tvTitle1, tvTitle2, tvContent;
	private Chapter chapter;
	private ImageView btnTitleLeft;
	private CheckBox btnHighLight;
	// 侧滑菜单
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;

	// 单词列表
	private Map<String, String> wordMap = new HashMap<String, String>();
	public List<String> dictsList = new ArrayList<String>();
	SpannableString spannableString;
	final String START = "start";
	final String END = "end";
	String str = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_info);
		if (this.getIntent() != null) {
			if (null != this.getIntent().getExtras()) {
				chapter = (Chapter) this.getIntent().getSerializableExtra(
						"articleInfo");
				wordMap = (Map<String, String>) this.getIntent()
						.getSerializableExtra("map");
			}
		}
		str = chapter.getContent();
		spannableString = new SpannableString(str);

		System.out.println("0000000000");
		Set set = wordMap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			System.out.println(mapentry.getKey() + "/" + mapentry.getValue());
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

		// 侧滑菜单
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		for (int i = 0; i < 7; i++)
			menuLists.add("level " + i);
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, menuLists);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(ArticleInfoActivity.this, "当前过滤的高亮单词等级为" + arg2,
						Toast.LENGTH_SHORT).show();
				gaoliang(0, arg2);
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

		btnHighLight = (CheckBox) findViewById(R.id.btnhighlight);

		btnHighLight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (btnHighLight.isChecked()) {
					btnHighLight.setText(R.string.highlingt_hide);
					gaoliang(1, 6);
				} else {
					btnHighLight.setText(R.string.highlingt_show);
					spannableString.setSpan(
							new BackgroundColorSpan(Color.WHITE), 0,
							str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					tvContent.setText(spannableString);
				}
			}
		});
	}

	private void gaoliang(int color, int level) {
		spannableString.setSpan(new BackgroundColorSpan(Color.WHITE), 0,
				str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		String[] contentArray = chapter.getContent().split(" ");
		for (int i = 0; i < contentArray.length; i++) {
			// System.out.println(contentArray[i]);
			if (wordMap.containsKey(contentArray[i])
					&& Integer.parseInt(wordMap.get(contentArray[i])) <= level) {
				System.out.println(contentArray[i]);
				if (color == 1)
					heightLight(contentArray[i], Color.parseColor("#ff0000"));
				else if (color == 0)

					switch (level) {
					case 0:
						heightLight(contentArray[i],
								Color.parseColor("#8B00FF"));
						break;
					case 1:
						heightLight(contentArray[i],
								Color.parseColor("#FF7F00"));
						break;
					case 2:
						heightLight(contentArray[i],
								Color.parseColor("#FFFF00"));
						break;
					case 3:
						heightLight(contentArray[i],
								Color.parseColor("#00FF00"));
						break;
					case 4:
						heightLight(contentArray[i],
								Color.parseColor("#00FFFF"));
						break;
					case 5:
						heightLight(contentArray[i],
								Color.parseColor("#0000FF"));
						break;
					case 6:
						heightLight(contentArray[i],
								Color.parseColor("#ff0000"));
						break;
					}
			}
		}
		tvContent.setText(spannableString);

	}

	private void heightLight(String pattern, int color) {
		ArrayList<Map<String, String>> lists = getStartAndEnd(Pattern
				.compile(pattern));
		for (Map<String, String> str : lists) {
			BackgroundColorSpan span = new BackgroundColorSpan(color);
			spannableString.setSpan(span, Integer.parseInt(str.get(START)),
					Integer.parseInt(str.get(END)),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	private ArrayList<Map<String, String>> getStartAndEnd(Pattern pattern) {
		ArrayList<Map<String, String>> lists = new ArrayList<Map<String, String>>(
				0);

		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			Map<String, String> map = new HashMap<String, String>(0);
			map.put(START, matcher.start() + "");
			map.put(END, matcher.end() + "");
			lists.add(map);
		}
		return lists;
	}

}
