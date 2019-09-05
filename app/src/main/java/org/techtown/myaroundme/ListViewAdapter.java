package org.techtown.myaroundme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ListViewItem> m_oData = null;
    private int nListCnt = 0;

    public ListViewAdapter(ArrayList<ListViewItem> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.title);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.content);
        ImageView oPhoto = (ImageView) convertView.findViewById(R.id.PostPhoto);
        TextView oTextLike = (TextView) convertView.findViewById(R.id.like);
        TextView oTextComment = (TextView) convertView.findViewById(R.id.comment);
        TextView oTextNickname = (TextView) convertView.findViewById(R.id.nickname);
        TextView oTextKey = (TextView) convertView.findViewById(R.id.key);


        oTextTitle.setText(m_oData.get(position).strTitle);
        oTextDate.setText(m_oData.get(position).strDate);
        oPhoto.setImageResource(m_oData.get(position).PostPhoto);
        oTextLike.setText(m_oData.get(position).like);
        oTextComment.setText(m_oData.get(position).comment);
        oTextNickname.setText(m_oData.get(position).nickname);
        oTextKey.setText(m_oData.get(position).key);

        return convertView;
    }
}