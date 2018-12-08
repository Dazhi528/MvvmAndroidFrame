package com.dazhi.libroot.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：通用适配器
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/8 11:51
 * 修改日期：2018/3/8 11:51
 * 用法：
 * myAdadpter = new MyAdapter<Hero>(mData,R.layout.item_spin_hero) {
 *      @Override
 *      public void bindView(ViewHolder holder, Hero obj) {
 *          holder.setImageResource(R.id.img_icon,obj.gethIcon());
 *          holder.setText(R.id.txt_name, obj.gethName());
 *      }
 * };
 */
public abstract class RootAdapter<T> extends BaseAdapter {
    private int intLayout; //布局id
    private List<T> lsData; //引入数据集合

    public RootAdapter(int intLayout, List<T> lsData) {
        this.intLayout = intLayout;
        this.lsData = lsData;
    }


    @Override
    public int getCount() {
        return lsData!=null ? lsData.size() : 0;
    }
    @Override
    public T getItem(int position) {
        return lsData!=null ? lsData.get(position) : null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView,
                parent, intLayout, position);
        onBindView(holder, getItem(position));
        return holder.getItemView();
    }


    /**=======================================
     * 作者：WangZezhi  (2018/3/8  12:09)
     * 功能：
     * 描述：
     *=======================================*/
    public abstract void onBindView(ViewHolder holder, T obj);

    //添加一个元素
    public void add(T data) {
        if (lsData == null) {
            lsData = new ArrayList<>();
        }
        lsData.add(data);
        notifyDataSetChanged();
    }
    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (lsData == null) {
            lsData = new ArrayList<>();
        }
        lsData.add(position, data);
        notifyDataSetChanged();
    }
    public void remove(T data) {
        if (lsData != null) {
            lsData.remove(data);
        }
        notifyDataSetChanged();
    }
    public void remove(int position) {
        if (lsData != null) {
            lsData.remove(position);
        }
        notifyDataSetChanged();
    }
    public void clear() {
        if (lsData != null) {
            lsData.clear();
        }
        notifyDataSetChanged();
    }


    /**=======================================
     * 作者：WangZezhi  (2018/3/8  12:00)
     * 功能：视图持有器
     * 描述：
     *=======================================*/
    public static class ViewHolder {
        //private Context context;            //Context上下文
        private SparseArray<View> saView;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标

        //构造方法，完成相关初始化
        //private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            //this.context = context;
            saView = new SparseArray<>();
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }
        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }
        /**
         * 获取view
         */
        public <T extends View> T getView(int id) {
            T t = (T)saView.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                saView.put(id, t);
            }
            return t;
        }
        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }
        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }
        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }
        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }
        /**
         * 设置点击监听
         */
        /*public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }*/
        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }
        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }
    }


}
