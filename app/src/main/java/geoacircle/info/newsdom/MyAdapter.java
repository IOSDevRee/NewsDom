package geoacircle.info.newsdom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myclass>{

    Context context;
    WorldFragment worldFragment;
    LayoutInflater inflater;

    ArrayList<GetterSetter> arrayList;
    ImageView imgView;
    private List<GetterSetter> searchList = null;

    public MyAdapter(Context context, List<GetterSetter> searchList) {
        this.context = context;
        this.searchList = searchList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<GetterSetter>();
        this.arrayList.addAll(searchList);
    }

    public MyAdapter(Context context, ArrayList<GetterSetter> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    public MyAdapter(WorldFragment worldFragment, ArrayList<GetterSetter> filteredModelList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public Myclass onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowfile, parent, false);
        Myclass myclass = new Myclass(v, context, arrayList);
        return myclass;
    }


    @Override
    public void onBindViewHolder(final Myclass holder, int position) {

        final GetterSetter g1 = arrayList.get(position);

        holder.title.setText(g1.getTitle());
        holder.description.setText(g1.getDescription());
        Glide.with(context).load(g1.getNewsImageUrl()).into(holder.imgView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+g1.getTitle(), Toast.LENGTH_SHORT).show();

                holder.onClick(v);

            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myclass extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView title;
        TextView description;
        TextView link;
        TextView published;

        ArrayList<GetterSetter> arrayList;
        Context context;
        ImageView imgView;


        public Myclass(View itemView, Context context, ArrayList<GetterSetter> arrayList) {
            super(itemView);
            this.arrayList = arrayList;
            this.context = context;
            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.tv);
            description = itemView.findViewById(R.id.desc);
            link = itemView.findViewById(R.id.link);
            published = itemView.findViewById(R.id.pubDate);
            imgView = itemView.findViewById(R.id.imgID);



        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            GetterSetter arrayList = this.arrayList.get(position);
            Intent intent = new Intent(this.context, SecondActivity.class);
            intent.putExtra("title", arrayList.getTitle());
            intent.putExtra("description", arrayList.getDescription());
            intent.putExtra("link", arrayList.getLink());
            intent.putExtra("pubDate", arrayList.getPublished());
            intent.putExtra("url", arrayList.getNewsImageUrl());
            this.context.startActivity(intent);

        }
    }


}
