package com.yuanye.t12_cit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanye.t12_cit.R;
import java.util.List;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<String> testName;
    private List<String> testResult;

    public MainAdapter(Context context, List<String> testName, List<String> testResult) {
        this.context = context;
        this.testName = testName;
        this.testResult = testResult;
    }

    @Override
    public int getCount() {
        return testName.size();
    }

    @Override
    public String getItem(int i) {
        return testName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_list_main, null);
            holder.txtName = view.findViewById(R.id.test_name);
            holder.imgResult = view.findViewById(R.id.test_result);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.txtName.setText(testName.get(i));
        int color;
        if (testResult.size() != 0){
            switch (testResult.get(i)){
                case "P":
//                    color = context.getResources().getColor(R.color.colorPass);
//                    view.setBackgroundColor(color);
                    holder.imgResult.setImageResource(R.drawable.success);
                    break;
                case "F":
//                    color = context.getResources().getColor(R.color.colorFail);
//                    view.setBackgroundColor(color);
                    holder.imgResult.setImageResource(R.drawable.fail);
                    break;
                default:
                    holder.imgResult.setImageResource(R.drawable.alert);
                    break;
            }

        }
        return view;
    }

    class ViewHolder{
        TextView txtName;
        ImageView imgResult;
    }
}
