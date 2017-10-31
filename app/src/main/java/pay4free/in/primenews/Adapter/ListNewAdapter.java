package pay4free.in.primenews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pay4free.in.primenews.Common.ISO8601Parse;
import pay4free.in.primenews.DetailActivity;
import pay4free.in.primenews.Interface.ItemClickListener;
import pay4free.in.primenews.Model.Articles;
import pay4free.in.primenews.R;

/**
 * Created by AAKASH on 23-10-2017.
 */
class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;
    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListNewsViewHolder(View itemView) {
        super(itemView);
        article_image=(CircleImageView)itemView.findViewById(R.id.article_image);
        article_title=(TextView)itemView.findViewById(R.id.article_title);
        article_time=(RelativeTimeTextView)itemView.findViewById(R.id.article_time);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}

public class ListNewAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<Articles> articlesList;
    private Context context;

    public ListNewAdapter(List<Articles> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.news_layout,parent,false);
        return new ListNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position) {

        Picasso.with(context).load(articlesList.get(position).getUrlToimage()).into(holder.article_image);
        if(articlesList.get(position).getTitle().length()>65)
            holder.article_title.setText(articlesList.get(position).getTitle().substring(0,65)+"......");

        else
            holder.article_title.setText(articlesList.get(position).getTitle());
        Date date=null;
        try
        {
            date=ISO8601Parse.parse(articlesList.get(position).getPublishedAt());
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        holder.article_time.setReferenceTime(date.getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("webURL",articlesList.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}
