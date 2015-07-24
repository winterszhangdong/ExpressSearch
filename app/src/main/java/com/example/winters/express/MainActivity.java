package com.example.winters.express;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener{
    private String address = "http://192.168.0.108:9000/search_express";
    private TextView textView;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                this.progressBar = (ProgressBar) findViewById(R.id.progressbar);
                this.textView = (TextView) findViewById(R.id.text_view);
                this.editText = (EditText) findViewById(R.id.edit_text);
                this.textView.setVisibility(View.GONE);
                this.progressBar.setVisibility(View.VISIBLE);
                String expressCode = this.editText.getText().toString();
                ArrayList<NameValuePair> parmList = new ArrayList<NameValuePair>();
                parmList.add(new BasicNameValuePair("express_code", expressCode));
                Log.d("parmList: ----->", parmList.toString());
                RenewTask renewTask = new RenewTask(this.textView, this.progressBar,parmList);
                renewTask.execute(this.address);
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
