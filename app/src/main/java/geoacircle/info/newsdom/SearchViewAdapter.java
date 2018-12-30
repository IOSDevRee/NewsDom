package geoacircle.info.newsdom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<GetterSetter> arrayList;
    ImageView imgView;
    private List<GetterSetter> searchList = null;

    private int position;
    private ViewGroup parent;

    public SearchViewAdapter(Context context, List<GetterSetter> searchList) {
        this.context = context;
        this.searchList = searchList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<GetterSetter>();
        this.arrayList.addAll(searchList);
    }

    public class ViewHolder {
        TextView name;
    }


    public int getCount() {
        return searchList.size();
    }


    public GetterSetter getItem(int position) {
        return searchList.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final SearchViewAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_view, null);

            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(searchList.get(position).getTitle());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if (charText.length() == 0) {
            searchList.addAll(arrayList);
            Log.d("mydat", "Pass 4: "+ searchList);
        } else {
            for (GetterSetter wp : arrayList)
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchList.add(wp);
                    Log.d("mydat", "Pass 5: "+ searchList);
                }
        }
        notifyDataSetChanged();
        Log.d("mydat", "Pass 6: "+ "");
    }
}