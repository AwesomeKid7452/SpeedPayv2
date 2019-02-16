package anawesomekid.speedpay;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tabPayment extends Fragment {


    int [] BookID;
    String [] BookDescription;
    String [] BookName;
    String [] Category;
    String [] LendDate;
    String [] DueDays;
    String [] FineAmount;

    int [] IMAGES = {R.drawable.ic_edit}; //, R.drawable.ic_email, R.drawable.ic_save
    String [] COST;// = {"1.20", "2.20", "17.00"};
    String[] Item;

    //String [] BookID,BookDescription,BookName ,Category,LendDate,DueDays,FineAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.payment_tab, container, false);

        MainActivity activity = (MainActivity) getActivity();
        final String ID = activity.getMyData();

        //BookID = getActivity().getIntent().getExtras().getIntArray("BookID");
        BookName = getActivity().getIntent().getExtras().getStringArray("BookName");
        BookDescription = getActivity().getIntent().getExtras().getStringArray("BookDescription");
        Category = getActivity().getIntent().getExtras().getStringArray("Category");
        LendDate = getActivity().getIntent().getExtras().getStringArray("LendDate");
        DueDays = getActivity().getIntent().getExtras().getStringArray("DueDays");
        FineAmount = getActivity().getIntent().getExtras().getStringArray("FineAmount");



        ListView listView = (ListView)rootView.findViewById(R.id.listView);

        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);


        return rootView;

    }

    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
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

            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_name = (TextView)view.findViewById(R.id.txt_name);
            TextView textView_cost = (TextView)view.findViewById(R.id.txt_cost);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(BookName[i]);
            textView_cost.setText(FineAmount[i]);

            return view;
        }
    }

}
