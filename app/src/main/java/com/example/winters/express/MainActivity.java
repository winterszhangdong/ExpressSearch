package com.example.winters.express;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String address = "http://winterszhang.sinaapp.com/search_express";
    private TextView textView;
    private EditText editText;
    private ProgressBar progressBar;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        rememberCheck = (CheckBox) findViewById(R.id.check_box);

        // 看是否记住了单号，如果记住了则将单号取出
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("is_remember", false);
        if (isRemember) {
            String expressCode = pref.getString("express_code", "");
            editText.setText(expressCode);
            rememberCheck.setChecked(true);
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                progressBar = (ProgressBar) findViewById(R.id.progressbar);
                textView = (TextView) findViewById(R.id.text_view);
                editText = (EditText) findViewById(R.id.edit_text);
                rememberCheck = (CheckBox) findViewById(R.id.check_box);
                textView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                // 获取editText中的单号，并将其放入parmList中作为POST的数据传入AsyncTask对象中
                String expressCode = editText.getText().toString();
                ArrayList<NameValuePair> parmList = new ArrayList<NameValuePair>();
                parmList.add(new BasicNameValuePair("express_code", expressCode));
                Log.d("parmList: ----->", parmList.toString());

                RenewTask renewTask = new RenewTask(textView, progressBar,parmList);
                renewTask.execute(address);

                // 查看是否选择记住单号，如果选择记住，则将单号保存，否则清除SharePreference中的数据
                editor = pref.edit();
                if (rememberCheck.isChecked()) {
                    editor.putBoolean("is_remember", true);
                    editor.putString("express_code", expressCode);
                } else editor.clear();
                editor.commit();

                break;

            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
