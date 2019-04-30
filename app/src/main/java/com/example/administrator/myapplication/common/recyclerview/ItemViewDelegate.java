package com.example.administrator.myapplication.common.recyclerview;


/**
 * item 项
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);  //判断传入的 item 是不是自己应该处理的类型

    void convert(ViewHolder holder, T t, int position);

}
