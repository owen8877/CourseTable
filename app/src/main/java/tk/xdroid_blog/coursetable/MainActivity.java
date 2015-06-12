package tk.xdroid_blog.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
    private DayAdapter dayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                case R.id.action_settings:
                    Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
                }
            return true;
            }
        });
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dayAdapter != null) dayAdapter.notifyDataSetChanged();
    }

    private void init(){
        new Thread(new Runnable() {
            public void run() {
                dayAdapter = new DayAdapter(MainActivity.this, R.layout.day_layout,
                        Database.getdaylist(MainActivity.this));
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(dayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
            }
        }).run();
    }
    /*
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
        switch (item.getItemId()){
            default:
        }

        return super.onOptionsItemSelected(item);
    }
    */

}

