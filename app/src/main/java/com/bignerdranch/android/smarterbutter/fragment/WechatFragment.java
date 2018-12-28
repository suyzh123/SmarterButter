package com.bignerdranch.android.smarterbutter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bignerdranch.android.smarterbutter.adapter.WeChatAdapter;
import com.bignerdranch.android.smarterbutter.entity.WeChatData;
import com.bignerdranch.android.smarterbutter.ui.WebViewActivity;
import com.bignerdranch.android.smarterbutter.utils.L;
import com.kymjs.rxvolley.RxVolley;

import com.bignerdranch.android.smarterbutter.R;
import com.bignerdranch.android.smarterbutter.utils.StaticClass;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WechatFragment extends Fragment{
    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;}
    //初始化
    private void findView(View view){
        mListView= view.findViewById(R.id.mListView);
        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                L.i("wechat json:" + t);
                parsingJson(t);
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:"+position);
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
            }
        });
    }
        //解析Json
        private void parsingJson(String t) {
            try {
                JSONObject jsonObject = new JSONObject(t);
                JSONObject jsonresult = jsonObject.getJSONObject("result");
                JSONArray jsonList = jsonresult.getJSONArray("list");
                for (int i = 0; i < jsonList.length(); i++) {
                    JSONObject json = (JSONObject) jsonList.get(i);
                    WeChatData data = new WeChatData();

                    String titlr = json.getString("title");
                   String url = json.getString("url");

                    data.setTitle(titlr);
                    data.setSource(json.getString("source"));
                    data.setImgUrl(json.getString("firstImg"));

                    mList.add(data);

                    mListTitle.add(titlr);
                    mListUrl.add(url);
                }
                WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
                mListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





