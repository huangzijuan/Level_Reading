package com.example.level_reading.ui;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.example.level_reading.R;
import com.example.level_reading.adapter.TitleDataAdapter;
import com.example.level_reading.data.Chapter;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ListView lvTitleData;
	private List<Chapter> arrayChapter = new ArrayList<Chapter>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        InputStream fis = null;
        //ArrayList<Chapter> arrayChapter = new ArrayList<Chapter>();
        final String RE = "(Lesson )[0-9]*";
        StringBuffer sbContent = null;
        Chapter chapter = null;
        
        try {
        	fis = getAssets().open("English4.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			String line = null;
			int titleSign = 0;
			try {
				while((line = br.readLine())!=null){	
					if(titleSign == 2){
						titleSign = 0;
						if(chapter!=null){
							chapter.setC_title(line.trim());
						}
					}else if(titleSign == 1){
						titleSign = 2;
						if(chapter!=null){
							chapter.setE_title(line.trim());
						}
					}else if (line.trim().matches(RE)) {
						if(chapter!=null){
							chapter.setContent(new String(sbContent));
							arrayChapter.add(chapter);
						}
						chapter = new Chapter();
						chapter.setLesson(line.trim());
						titleSign = 1;
						sbContent = new StringBuffer();
					}else{
						if(chapter!=null){
							sbContent.append(line);
						}
					}
				}
				if(chapter!=null){
					chapter.setContent(new String(sbContent));
					arrayChapter.add(chapter);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        for(int i=0; i<arrayChapter.size(); i++){
        	System.out.println(arrayChapter.get(i).toString());
        }
        
     
	}
	
	private void init() {
		lvTitleData = (ListView) findViewById(R.id.lvTitleData);
    	TitleDataAdapter adapter = new TitleDataAdapter(this, arrayChapter);
    	lvTitleData.setAdapter(adapter);
    	
    	lvTitleData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ArticleInfoActivity.class);
				intent.putExtra("articleInfo", arrayChapter.get(arg2));
				startActivity(intent);
			}
		});
    }
   
}
