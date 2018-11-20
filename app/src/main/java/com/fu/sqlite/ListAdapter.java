package com.fu.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Employee> mEmployeeList;

    public ListAdapter(Context context, List<Employee> employeeList) {
        mContext = context;
        mEmployeeList = employeeList;
    }

    @Override
    public int getCount() {
        if (mEmployeeList==null){
            return 0;
        }else {
            return mEmployeeList.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return mEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.list, parent, false);

            viewHolder.name = convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Employee Employee = (Employee) getItem(position);
        viewHolder.name.setText(Employee.getName());
      return convertView;
    }

    private final static class ViewHolder {
        TextView name;
    }
}
