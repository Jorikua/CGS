package ua.vsevolodkaganovych.testtaskcgs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class FragmentSearch extends ListFragment {

    private ProgressDialog mProgressDialog;
    private ArrayList<Item> mItems;
    private CustomListAdapter mAdapter;
    private Button mButton;
    private EditText mEditText;
    private TextView mTextView;
    private int mStart = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_view, container, false);

        mEditText = (EditText) rootView.findViewById(R.id.editText);
        mTextView = (TextView) rootView.findViewById(android.R.id.empty);
        mTextView.setText("Google found nothing");

        mItems = new ArrayList<Item>();
        mAdapter = new CustomListAdapter(getActivity(), mItems);
        setListAdapter(mAdapter);

        mButton = (Button) rootView.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.execute();
            }
        });


        return rootView;
    }


    public class Task extends AsyncTask<Void, Void, ArrayList<Item>> {

        JSONObject jsonObject = null;

        @Override
        protected ArrayList<Item> doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url("https://www.googleapis.com/customsearch/v1?key=AIzaSyAN1zvkleJByUNRjaD8oJrDBgCMI-9AhMg&cx=017835053804997538402:qgs6pq6bxmu&q=" + mEditText.getText().toString()
                    + "&start=" + mStart)
                    .build();
            try {
                Response response = HttpApp.getOkHttpClient().newCall(request).execute();
                jsonObject = new JSONObject(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<Item> items = new ArrayList<Item>();
            JSONArray array = jsonObject.optJSONArray("items");
            if (jsonObject.has("items") && null != jsonObject.optJSONArray("items") && jsonObject.optJSONArray("items").length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    Item item = new Item();
                    if (object.has("pagemap") && object.optJSONObject("pagemap") != null && object.optJSONObject("pagemap").length() != 0) {
                        JSONObject pagemap = object.optJSONObject("pagemap");
                        if (pagemap.has("cse_image") && null != pagemap.optJSONArray("cse_image") && pagemap.optJSONArray("cse_image").length() != 0) {
                            JSONArray imageArray = pagemap.optJSONArray("cse_image");
                            for (int j = 0; j < imageArray.length(); j++) {
                                JSONObject object2 = imageArray.optJSONObject(j);
                                item.image = object2.optString("src");
                            }
                        }
                    }
                    item.title = object.optString("title");
                    items.add(item);
                }
            } else {
                mTextView.getText();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> result) {

            getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    int count = getListView().getCount();
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (getListView().getLastVisiblePosition() >= count -1) {
                            new LoadMoreData().execute();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

            if (mItems != null) {
                mItems.clear();
            }
            mItems.addAll(result);
            mAdapter.notifyDataSetChanged();
        }
    }

    public class LoadMoreData extends AsyncTask<Void, Void, ArrayList<Item>> {

        JSONObject jsonObject = null;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Google is loading more results");
            mProgressDialog.setMessage("Loading more...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<Item> doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url("https://www.googleapis.com/customsearch/v1?key=AIzaSyAN1zvkleJByUNRjaD8oJrDBgCMI-9AhMg&cx=017835053804997538402:qgs6pq6bxmu&q=" + mEditText.getText().toString()
                    + "&start=" + (mStart+=10))
                    .build();
            Log.d("TAG", String.valueOf(mStart));
            try {
                Response response = HttpApp.getOkHttpClient().newCall(request).execute();
                jsonObject = new JSONObject(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<Item> items = new ArrayList<Item>();
            JSONArray array = jsonObject.optJSONArray("items");
            if (jsonObject.has("items") && null != jsonObject.optJSONArray("items") && jsonObject.optJSONArray("items").length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    Item item = new Item();
                    if (object.has("pagemap") && object.optJSONObject("pagemap") != null && object.optJSONObject("pagemap").length() != 0) {
                        JSONObject pagemap = object.optJSONObject("pagemap");
                        if (pagemap.has("cse_image") && null != pagemap.optJSONArray("cse_image") && pagemap.optJSONArray("cse_image").length() != 0) {
                            JSONArray imageArray = pagemap.optJSONArray("cse_image");
                            for (int j = 0; j < imageArray.length(); j++) {
                                JSONObject object2 = imageArray.optJSONObject(j);
                                item.image = object2.optString("src");
                            }
                        }
                    }
                    item.title = object.optString("title");
                    Log.d("TAG", item.title);
                    items.add(item);
                }
            } else {
                mTextView.getText();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> result) {
            int position = getListView().getLastVisiblePosition();
            getListView().setSelectionFromTop(position, 0);
            mProgressDialog.dismiss();
            mItems.addAll(result);
            mAdapter.notifyDataSetChanged();
        }
    }

}
