package com.example.asus.androidscruminftel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.activity.NewTaskActivity;
import com.example.asus.androidscruminftel.activity.ProjectsScrum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anandbose on 09/06/15.
 */
public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;

    private ProjectsScrum myProject;

    public ExpandableListAdapter(List<Item> data) {
        this.data = data;
    }

    public void setMyProject (ProjectsScrum theProject) {
        this.myProject = theProject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.project_row, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final Item item = data.get(position);


        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);

                itemController.rlLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        Intent intent = new Intent(myProject, NewTaskActivity.class);
                        intent.putExtra("tittle", itemController.header_title.getText());

                        int pos = data.indexOf(itemController.refferalItem);
                        if(item.invisibleChildren == null) {
                            intent.putExtra("content", data.get(pos + 1).text);
                            intent.putExtra("time", data.get(pos + 2).text);
                            intent.putExtra("date", data.get(pos + 3).text);
                            intent.putExtra("idTask", data.get(pos + 4).text);
                        }else{
                            intent.putExtra("content", item.invisibleChildren.get(0).text);
                            intent.putExtra("time", item.invisibleChildren.get(1).text);
                            intent.putExtra("date", item.invisibleChildren.get(2).text);
                            intent.putExtra("idTask", item.invisibleChildren.get(3).text);
                        }
                        myProject.startActivity(intent);
                        return true;
                    }
                });


                itemController.rlLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            item.invisibleChildren = null;
                        }
                    }
                });

                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).text);
                break;

        }


    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(v.getContext(), "OnLongClick Version :", Toast.LENGTH_SHORT).show();
        return true;
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public Item refferalItem;
        RelativeLayout rlLayout;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            rlLayout = (RelativeLayout) itemView.findViewById(R.id.rlLayout);
            header_title = (TextView) itemView.findViewById(R.id.tv_text);
        }

    }

    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}
