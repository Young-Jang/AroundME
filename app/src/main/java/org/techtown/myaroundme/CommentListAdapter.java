package org.techtown.myaroundme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter{
    LayoutInflater inflater = null;
    private ArrayList<CommentList> m_oData = null;
    private int nListCnt = 0;

    public CommentListAdapter(ArrayList<CommentList> _oData)
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
            convertView = inflater.inflate(R.layout.comment_list, parent, false);
        }

        TextView oCommentNickname = (TextView) convertView.findViewById(R.id.nickname);
        TextView oCommentContent = (TextView) convertView.findViewById(R.id.content);

        oCommentNickname.setText(m_oData.get(position).conickname);
        oCommentContent.setText(m_oData.get(position).cocontent);

        return convertView;
    }
}
