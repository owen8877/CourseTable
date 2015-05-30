package tk.xdroid_blog.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity{
    private DayAdapter dayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        Button clickButton = (Button) findViewById(R.id.button_1);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        dayAdapter = new DayAdapter(MainActivity.this, R.layout.course_layout,
                                Database.getDaylist(MainActivity.this));
                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setAdapter(dayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("position", "" + position);
                                startActivity(intent);
                            }
                        });
                    }
                }).run();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dayAdapter != null) dayAdapter.notifyDataSetChanged();
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

