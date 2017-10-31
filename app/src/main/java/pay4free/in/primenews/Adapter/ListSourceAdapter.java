package pay4free.in.primenews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pay4free.in.primenews.Common.Common;
import pay4free.in.primenews.Interface.IconBetterIdeaService;
import pay4free.in.primenews.Interface.ItemClickListener;
import pay4free.in.primenews.ListNews;
import pay4free.in.primenews.Model.IconBetterIdea;
import pay4free.in.primenews.Model.WebSite;
import pay4free.in.primenews.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AAKASH on 23-10-2017.
 */
class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
 ItemClickListener itemClickListener;
    TextView source_title;
    CircleImageView source_image;
    public ListSourceViewHolder(View itemView) {
        super(itemView);
        source_image=(CircleImageView)itemView.findViewById(R.id.source_image);
        source_title=(TextView) itemView.findViewById(R.id.source_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        mService= Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {
     StringBuilder iconBetterAPI=new StringBuilder("https://icons.better-idea.org/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());
        mService.getIconUrl(iconBetterAPI.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
               if (response.body()!=null && response.body().getIcons()!=null && response.body().getIcons().size() > 0
                        && !TextUtils.isEmpty(response.body().getIcons().get(0).getUrl()))
                {
                    Picasso.with(context)
                            .load(response.body().getIcons().get(0).getUrl())
                            .into(holder.source_image);

                }
                else
               {
                   Picasso.with(context)
                           .load(R.drawable.icon)
                           .into(holder.source_image);
               }

            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {
                Toast.makeText(context, "Cant load Icon", Toast.LENGTH_SHORT).show();

            }
        });
        holder.source_title.setText(webSite.getSources().get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent=new Intent(context, ListNews.class);
                intent.putExtra("source", webSite.getSources().get(position).getId());
                intent.putExtra("sortBy", webSite.getSources().get(position).getSortBysAvailable().get(0));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
