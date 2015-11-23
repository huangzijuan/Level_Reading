package com.example.level_reading.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.level_reading.R;
import com.example.level_reading.adapter.TitleDataAdapter;
import com.example.level_reading.data.Chapter;

public class MainActivity extends Activity {
	private ListView lvTitleData;
	private List<Chapter> arrayChapter = new ArrayList<Chapter>();
	private Map<String, String> wordMap = new HashMap<String, String>();
	private TitleDataAdapter adapter = new TitleDataAdapter(this, arrayChapter);
	Handler mhandler = new Handler() {

		public void handleMessage(Message msg) {
			arrayChapter = (List<Chapter>) msg.getData().get("data");
			wordMap = (Map<String, String>) msg.getData().get("map");
			adapter.setTitleDateList(arrayChapter);
			adapter.notifyDataSetChanged();

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		Thread managethread = new Thread() {
			List<Chapter> temparrayChapter = getArticle();
			Map<String, String> tempwordMap = getWordList();
			@Override
			public void run() {
				Message msg = Message.obtain();
				Bundle b = new Bundle();
				b.putSerializable("data", (Serializable) temparrayChapter);
				b.putSerializable("map", (Serializable) tempwordMap);
				msg.setData(b);
				mhandler.sendMessage(msg);
			}
		};
		managethread.start();

	}

	private void init() {
		lvTitleData = (ListView) findViewById(R.id.lvTitleData);
		lvTitleData.setAdapter(adapter);

		lvTitleData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ArticleInfoActivity.class);
				intent.putExtra("articleInfo", arrayChapter.get(arg2));
				intent.putExtra("map", (Serializable) wordMap);
				startActivity(intent);
			}
		});
	}

	private ArrayList<Chapter> getArticle() {
		final String RE = "(Lesson )[0-9]*";
		List<Chapter> temparrayChapter1 = new ArrayList<Chapter>();
		InputStream fis = null;
		StringBuffer sbContent = null;
		Chapter chapter = null;
		String line = null;
		int titleSign = 0;
		try {
			fis = getAssets().open("English4.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,
					"UTF-8"));

			while ((line = br.readLine()) != null) {
				if (titleSign == 2) {
					titleSign = 0;
					if (chapter != null) {
						chapter.setC_title(line.trim());
					}
				} else if (titleSign == 1) {
					titleSign = 2;
					if (chapter != null) {
						chapter.setE_title(line.trim());
					}
				} else if (line.trim().matches(RE)) {
					if (chapter != null) {
						chapter.setContent(new String(sbContent));
						temparrayChapter1.add(chapter);
					}
					chapter = new Chapter();
					chapter.setLesson(line.trim());
					titleSign = 1;
					sbContent = new StringBuffer();
				} else {
					if (chapter != null) {
						sbContent.append(line).append("\n");
					}
				}
			}
			if (chapter != null) {
				chapter.setContent(new String(sbContent));
				temparrayChapter1.add(chapter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis !=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return (ArrayList<Chapter>) temparrayChapter1;
	}

	private Map<String, String> getWordList() {
		InputStream fis = null;
		List<String> dictsList = new ArrayList<String>();
		Map<String, String> tempwordMap1 = new HashMap<String, String>();
		try {
			fis = getAssets().open("nce4_words.txt");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(fis,
					"UTF-8"));

			String tempString = br1.readLine();
			while (null != tempString) {
				dictsList.add(tempString);
				tempString = br1.readLine();
			}

		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(fis !=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (int i = 1; i < dictsList.size(); i++) {
			String[] strArray = dictsList.get(i).split("\t");
			tempwordMap1.put(strArray[0], strArray[1]);
		}
		return tempwordMap1;
	}

}
