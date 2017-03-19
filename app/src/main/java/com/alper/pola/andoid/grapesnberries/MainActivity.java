package com.alper.pola.andoid.grapesnberries;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<itemsmodel> itemlList = new ArrayList<itemsmodel>();
    newsadapter adapter;
    private GridView lvitems;
    private int work = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvitems = (GridView) findViewById(R.id.gripview);
        adapter = new newsadapter(getApplicationContext(), R.layout.row, itemlList);
        adapter.notifyDataSetChanged();
        lvitems.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        new jsontask().execute("http://grapes-n-berries.getsandbox.com/new_products?count=10&from=0");


    }

    public class jsontask extends AsyncTask<String, String, List<itemsmodel>> {


        @Override
        protected List<itemsmodel> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }


                String finaljson = buffer.toString();
                JSONArray parentarray = new JSONArray(finaljson);


                for (int i = 0; i < parentarray.length(); i++) {
                    JSONObject finalobject = parentarray.getJSONObject(i);
                    itemsmodel itemsmodel = new itemsmodel();

                    itemsmodel.setDescription(finalobject.getString("price"));
                    itemsmodel.setTitle(finalobject.getString("productDescription"));
                    JSONObject second = finalobject.getJSONObject("image");
                    itemsmodel.setImage(second.getString("url"));


                    itemlList.add(itemsmodel);


                }


                return itemlList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return itemlList;
            } catch (IOException e) {
                e.printStackTrace();
                return itemlList;
            } catch (JSONException e) {

            } finally {
                if (connection != null) {


                }
                try {
                    if (reader != null) {
                        reader.read();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return itemlList;
        }


        @Override
        protected void onPostExecute(List<itemsmodel> result) {
            adapter.notifyDataSetChanged();

            super.onPostExecute(result);


            adapter.notifyDataSetChanged();


        }

    }

    public class newsadapter extends ArrayAdapter {
        private List<itemsmodel> itemlList;
        private int resource;
        private LayoutInflater inflater;

        public newsadapter(Context context, int resource, List<itemsmodel> objects) {
            super(context, resource, objects);
            itemlList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewholder holder = null;
            if (convertView == null) {
                holder = new viewholder();
                convertView = inflater.inflate(resource, null);
                holder.newsimage = (ImageView) convertView.findViewById(R.id.imageView2);
                holder.title = (TextView) convertView.findViewById(R.id.textView2);
                holder.description = (TextView) convertView.findViewById(R.id.textView3);


                convertView.setTag(holder);
            } else {
                holder = (viewholder) convertView.getTag();

            }


            Picasso.with(getApplicationContext()).load(itemlList.get(position).getImage()).into(holder.newsimage);
            holder.title.setText(itemlList.get(position).getTitle());
            holder.description.setText(itemlList.get(position).getDescription());


            lvitems.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    new jsontask().execute("http://grapes-n-berries.getsandbox.com/new_products?count=10&from=" + work);
                    work = work + 10;
                    return false;

                }
            });


            lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("image", itemlList.get(position).getImage());
                    intent.putExtra("desc", itemlList.get(position).getTitle());
                    startActivity(intent);
                }
            });


            return convertView;

        }

        class viewholder {
            private ImageView newsimage;
            private TextView title;
            private TextView description;


        }


    }

}

