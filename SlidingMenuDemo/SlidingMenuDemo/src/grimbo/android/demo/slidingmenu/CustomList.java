package grimbo.android.demo.slidingmenu;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomList extends ArrayAdapter<String>{
private final Activity context;
private final String[] web;
private final Integer[] imageId;
private final boolean menu;
private final int imageId1;
private final String sync;
public CustomList(Activity context,String[] web, Integer[] imageId, boolean menu, int imageId1,String sync) {
super(context, R.layout.menu_inner_view, web);
this.context = context;
this.web = web;
this.imageId = imageId;
this.menu=menu;
this.sync=sync;
this.imageId1=imageId1;
}
@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView;
if(menu && position==1){
	rowView= inflater.inflate(R.layout.menu_inner_view1, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	TextView txtTitle1 = (TextView) rowView.findViewById(R.id.txt1);
	ImageView imageView1 = (ImageView) rowView.findViewById(R.id.img1);
	txtTitle.setText(web[position]);
	imageView.setImageResource(imageId[position]);
	txtTitle1.setText(sync);
	imageView1.setImageResource(imageId1);
	
}
else if(menu && position==5){
	rowView= inflater.inflate(R.layout.menu_inner_view2, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	txtTitle.setText(web[position]);
	txtTitle.setAlpha(1.0f);
}
else if(!menu && position==0)
{
	rowView= inflater.inflate(R.layout.menu_inner_view, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	txtTitle.setText(web[position]);
	imageView.setImageResource(imageId[position]);
	View l = (View)rowView.findViewById(R.id.layout);
	txtTitle.setAlpha(1.0f);
	l.setBackgroundColor(Color.rgb(247, 138, 40));
	imageView.setAlpha(1.0f);
	rowView.setBackgroundColor(Color.BLACK);
}
else
{
rowView= inflater.inflate(R.layout.menu_inner_view, null, true);
TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
txtTitle.setText(web[position]);
imageView.setImageResource(imageId[position]);
}

return rowView;
}
}