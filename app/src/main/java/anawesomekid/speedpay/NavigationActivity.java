package anawesomekid.speedpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity {

    private static String URL_BOOKS = "http://www.a2k.online/SpeedPay/Android/books.php";

    SessionManager sessionManager;
    String getID;
    //String url = "http://www.a2k.online/SpeedPay/Android/Attachment/C%23forDummies.jpg";

    ImageView imageView;

    int [] IMAGES = {R.drawable.ic_edit};
    String [] url;
    String [] BookDescription;
    String [] BookName;
    String [] Category;
    String [] LendDate;
    String [] DueDays;
    String [] FineAmount;

    private Button btn_fine, btn_books, btn_others, btn_payAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        btn_payAll = findViewById(R.id.btn_payall);

        Bundle extras = getIntent().getExtras();
        BookName = extras.getStringArray("BookName");
        FineAmount = extras.getStringArray("FineAmount");
        BookDescription = extras.getStringArray("BookDescription");
        Category = extras.getStringArray("Category");
        LendDate = extras.getStringArray("LendDate");
        DueDays = extras.getStringArray("DueDays");
        url = extras.getStringArray("url");

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(sessionManager.ID);

        ListView listView = (ListView) findViewById(R.id.listView);

         CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

    }

    private void books() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("books");
                            int num = 0;

                            if (success.equals("1")) {

                                String[] arr = new String[jsonArray.length()];

                                BookName = new String[jsonArray.length()];
                                BookDescription = new String[jsonArray.length()];
                                Category = new String[jsonArray.length()];
                                LendDate = new String[jsonArray.length()];
                                DueDays = new String[jsonArray.length()];
                                FineAmount = new String[jsonArray.length()];
                                url = new String[jsonArray.length()];


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    //arr[i] = object.getString("BookID").trim();
                                    //BookID[i] = object.getInt("BookID");
                                    BookDescription[i] = object.getString("BookDescription").trim();
                                    BookName[i] = object.getString("BookName").trim();
                                    Category[i] = object.getString("Category").trim();
                                    LendDate[i] = object.getString("LendDate").trim();
                                    DueDays[i] = object.getString("DueDays").trim();
                                    FineAmount[i] = object.getString("FineAmount").trim();
                                    url[i] = object.getString("url").trim();

                                }

                                Intent intent = new Intent(NavigationActivity.this, NavigationActivity.class);
                                //intent.putExtra("BookID", BookID);
                                intent.putExtra("BookDescription", BookDescription);
                                intent.putExtra("BookName", BookName);
                                intent.putExtra("Category", Category);
                                intent.putExtra("LendDate", LendDate);
                                intent.putExtra("DueDays", DueDays);
                                intent.putExtra("FineAmount", FineAmount);
                                intent.putExtra("url", url);

                                NavigationActivity.this.startActivity(intent);

                            } else {

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", getID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return BookName.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);

            imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_name = (TextView)view.findViewById(R.id.txt_name);
            TextView textView_cost = (TextView)view.findViewById(R.id.txt_cost);

            //imageView.setImageResource(IMAGES[i]);
            textView_name.setText(BookName[i]);
            textView_cost.setText(FineAmount[i]);
            LoadImageFromURL(url[i]);

            return view;
        }
    }

    private void LoadImageFromURL(String url) {

        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


    }
}
