package com.enation.javashop.connectview.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.enation.javashop.connectview.R;
import com.enation.javashop.connectview.logic.UmengLogin;
import com.enation.javashop.connectview.utils.ConnectViewLogger;
import com.enation.javashop.connectview.utils.NotUseConnectLoginException;
import com.enation.javashop.connectview.utils.Utils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录UI控件
 * @author LDD
 */

public class ConnectView extends LinearLayout {
    /**
     * 第一次Layout标识
     */
    private boolean isFirstLayout = true;

    /**
     * 是否开启微博登录
     */
    private boolean isUseSina   = false;

    /**
     * 是否开启微信登录
     */
    private boolean isUseWeChat = false;

    /**
     * 是否开启QQ登录
     */
    private boolean isUseQQ     = false;

    /**
     * ConnectView的高
     */
    private int     height = 0;

    /**
     * ConnectView的宽度
     */
    private int     width = 0;

    /**
     * 开启的三方登录数量
     */
    private int     connectNum;

    /**
     * Item高度
     */
    private int     widgetHeight = 0;

    /**
     * Item宽度
     */
    private int     widgetWidth  = 0;

    /**
     * 微信图标
     */
    @DrawableRes private int wechatImage = R.drawable.umeng_socialize_wechat;

    /**
     * QQ图标
     */
    @DrawableRes private int qqImage     = R.drawable.umeng_socialize_qq;

    /**
     * 新浪图标
     */
    @DrawableRes private int sinaImage   = R.drawable.umeng_socialize_sina;

    /**
     * SubView集合
     */
    private Map<String,View> subViews;

    /**
     * 用户上下文
     */
    private Activity activity;

    /**
     * 初始化第三方登录
     * @param activity 上下文
     */
    public void initConnect(Activity activity){
        this.activity = activity;
    }

    /**
     * 二参构造
     * @param context  用户上下文
     * @param attrs    xml参数集
     */
    public ConnectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**初始化子View集合*/
        subViews = new HashMap<>();
        /** 初始化，获得屏幕宽高及密度*/
        Utils.initScreenInfo(getContext());
        /**对数据初始化*/
        initData(attrs);
        /**初始化ConnectView预制第三方Item*/
        initSubViews(context);
        /**错误处理*/
        ConnectViewLogger.logE("您使用的第三方登录共有"+connectNum+"种！");
    }

    /**
     * 初始化ConnectView预制登录方式Item
     * @param context 上下文
     */
    private void initSubViews(Context context){
        if (isUseQQ)     subViews.put("QQ",new LinearLayout(context));
        if (isUseSina)   subViews.put("微博",new LinearLayout(context));
        if (isUseWeChat) subViews.put("微信",new LinearLayout(context));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**是否第一Layout*/
        if (isFirstLayout){
            /**如果宽为wrap_content，或者match_parent，获取messure后的宽*/
            if (width==-1||width==-2){
                width = getWidth();
                width = Utils.px2dip(getContext(),width);
            }
            /**如果高为wrap_content，或者match_parent，获取messure后的高*/
            if (height==-1||height==-2){
                height = getHeight();
                height = Utils.px2dip(getContext(),height);
            }
            /**绘制UI*/
            createUi();
            /**打印View信息*/
            ConnectViewLogger.logE("width: "+width+"    height: "+height+"    isUseQQ: "+isUseQQ+"    isUseWeChat: "+isUseWeChat+"    isUseSina: "+isUseSina);
            /**设置Layout标记，使其只能运行一次*/
            isFirstLayout=false;
        }
    }

    /**
     * 绘制界面
     */
    private void createUi(){
        /**设置子View居中*/
        this.setGravity(Gravity.CENTER);
        /**设置LinearLayout为横向居中*/
        this.setOrientation(HORIZONTAL);
        /**获取Item的宽，并转换为像素px*/
        widgetWidth  =   getWidgetWidth();
        /**获取Item的高，并转换为像素px*/
        widgetHeight =   getWidgetHeight();
        /**开启QQ第三方登录的话，显示QQItem*/
        if (isUseQQ)     addView(createConnectItem(qqImage,"QQ"));
        /**开启微信第三方登录的话，显示微信Item*/
        if (isUseWeChat) addView(createConnectItem(wechatImage,"微信"));
        /**开启微博第三方登录的话，显示微博Item*/
        if (isUseSina)   addView(createConnectItem(sinaImage,"微博"));
    }

    /**
     * 设置监听事件
     * @param type      第三方类别
     * @param listener  监听事件
     */
    public void setConnectListener(@UmengLogin.ConnectType final int type, final UmengLogin.ConnectListener listener){
        switch (type){
            case UmengLogin.QQ:
                setConnectEvent("QQ",SHARE_MEDIA.QQ,listener);
                break;
            case UmengLogin.WECHAT:
                setConnectEvent("微信",SHARE_MEDIA.WEIXIN,listener);
                break;
            case UmengLogin.WEIBO:
                setConnectEvent("微博",SHARE_MEDIA.SINA,listener);
                break;
        }
    }

    /**
     * 绑定登录事件
     * @param key       查询View的Key
     * @param type      第三方登录Type
     * @param listener  登录监听
     */
    private void setConnectEvent(String key, final SHARE_MEDIA type, final UmengLogin.ConnectListener listener){
        if (subViews.get(key)==null){
            ConnectViewLogger.logE("您没有启用该三方登录("+key+")！");
            return;
        }
        subViews.get(key).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener==null){
                    ConnectViewLogger.logE("没有设置绑定事件");
                    return;
                }
                UmengLogin.login(activity,type,listener);
            }
        });
    }

    /**
     * 获取item的宽
     * @return 宽
     */
    private int getWidgetWidth(){
        /**item的宽是ConnectView宽度的（connectNum*1.5）之一份*/
        int tWidth = (int) (width/(connectNum*1.5));

        /**如果获取到的宽比控件的高还要宽，那么控件的宽取ConnectView高度的一半，转换为像素px*/
        if (tWidth>=height*0.7){

            tWidth = (int) (height*0.6);

        }
        return Utils.dip2px(getContext(),tWidth);
    }

    /**
     * 获取Item的高度
     * @return 高
     */
    private int getWidgetHeight(){
        /**Item的高度是ConnectView高的0.9倍,转换为像素px*/
        return Utils.dip2px(getContext(),(int) (height*0.9));
    }

    /**
     * 获取文本视图高度
     * @return
     */
    private int getTextHeight(){
        /**文本视图高度为Item高减去Item宽*/
        int tHeiget = widgetHeight-widgetWidth;
        /**如果Item的宽高差大于Item的宽，那么文本视图的高度取item宽的一半*/
        if (tHeiget>widgetWidth){
            tHeiget = widgetWidth/2;
        }
        return tHeiget;
    }

    /**
     *  绘制ConnectView的Item视图
     * @param  imageId         Item图片ID
     * @param  connectMessage  Item提示文字
     * @return Item视图
     */
    private LinearLayout createConnectItem(@DrawableRes int imageId,String connectMessage){
        /**创建Item父视图*/
        LinearLayout item;
        /**如果是预制登录方式，直接从SubViews中取*/
        switch (connectMessage){
            case "QQ":
                item = (LinearLayout) subViews.get("QQ");
                break;
            case "微信":
                item = (LinearLayout) subViews.get("微信");
                break;
            case "微博":
                item = (LinearLayout) subViews.get("微博");
                break;
            default:
                /**不是预制登录方式，直接初始化*/
                item = new LinearLayout(getContext());
                break;
        }
        /**设置子视图居中*/
        item.setGravity(Gravity.CENTER);
        /**设置视图为垂直方向*/
        item.setOrientation(VERTICAL);
        /**设置视图宽高*/
        item.setLayoutParams(new LinearLayout.LayoutParams(widgetWidth,widgetHeight));
        /**获取文本视图高度，为Item宽度的一半*/
        int textviewHeight = getTextHeight();
        /**创建文本视图，并初始化*/
        TextView connectHint = new TextView(getContext());
        /**设置文本居中显示*/
        connectHint.setGravity(Gravity.CENTER);
        /**设置宽度，与父视图一样*/
        connectHint.setWidth(Utils.px2sp(getContext(),widgetWidth));
        /**设置文本视图高度*/
        connectHint.setHeight(textviewHeight);
        /**设置最多输入一行*/
        connectHint.setLines(1);
        /**通过高度，获取到适合文本视图大小的TextSize*/
        connectHint.setTextSize(Utils.px2sp(getContext(),textviewHeight)/2);
        /**创建图片视图，并初始化*/
        ImageView imageView = new ImageView(getContext());
        /**设置图片视图大小*/
        imageView.setLayoutParams(new LayoutParams(widgetWidth,widgetWidth));
        /**设置图片视图显示方式*/
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        /**设置Item提示文字*/
        connectHint.setText(connectMessage);
        /**设置Item显示的图片*/
        imageView.setImageResource(imageId);
        /**将图片视图添加到Item父视图中*/
        item.addView(imageView);
        /**将文本视图添加到Item父视图中*/
        item.addView(connectHint);
        /**将View放入集合中*/
        subViews.put(connectMessage,item);
        return item;
    }

    /**
     * 初始化数据
     * @param attrs xml数据集合
     */
    private void initData(AttributeSet attrs){
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            /**获取高*/
            if (attrs.getAttributeName(i).equals("layout_height")) {
                height = Integer.valueOf(strFilter(attrs.getAttributeValue(i)));
            }
            /**获取宽*/
            if (attrs.getAttributeName(i).equals("layout_width")) {
                width = Integer.valueOf(strFilter(attrs.getAttributeValue(i)));
            }
            /**获取开启QQ标记*/
            if (attrs.getAttributeName(i).equals("useQQ")){
                isUseQQ = attrs.getAttributeBooleanValue(i,false);
            }
            /**获取开启微信标记*/
            if (attrs.getAttributeName(i).equals("useWechat")){
                isUseWeChat = attrs.getAttributeBooleanValue(i,false);
            }
            /**获取开启微博标记*/
            if (attrs.getAttributeName(i).equals("useSina")){
                isUseSina = attrs.getAttributeBooleanValue(i,false);
            }
            /**获取是否开启DebugLogger标记*/
            if (attrs.getAttributeName(i).equals("debugLogger")){
                if(attrs.getAttributeBooleanValue(i,false)) ConnectViewLogger.openConnectDebugLogger();
            }
        }
            /**获取一共开启的几种三方登录*/
            connectNum = getConnectNum();
    }


    /**
     * 获取开启几种三方登录
     * @return connectNum
     */
    private int getConnectNum() {

        int num = 0;

        /**如果开启微博登录，加一*/
        if (isUseSina)   num+=1;

        /**如果开启微信登录，加一*/
        if (isUseWeChat) num+=1;

        /**如果开启QQ登录，加一*/
        if (isUseQQ)     num+=1;

        /**没有开启任何三方登录，提示错误！*/
        if (num==0){
            try {
                ConnectViewLogger.logE("您没有使用任何第三方登录！");
                throw new NotUseConnectLoginException("Not Found ConnectLogin Type In This Application!");
            } catch (NotUseConnectLoginException e) {
                e.printStackTrace();
            }
        }
        return num;

    }

    /**
     * 从字符串中获取数字，并且去除小数点
     * @param str    StringValue
     * @return       去除完毕的字符串
     */
    private String  strFilter(String str){
         String value = str.replaceAll("[a-zA-Z]","");
         if (value.contains(".")){
             value = value.substring(0,value.indexOf("."));
         }
        return value;
    }


    /**
     * 用户自己添加ConnectItem
     * @param resId      图标ID
     * @param text       提示
     * @param listener   点击监听
     */
    public void addConnectItem(@DrawableRes int resId,String text,OnClickListener listener){
        if ((getChildCount()+1)*widgetWidth>Utils.dip2px(getContext(),width)){
            ConnectViewLogger.logE("超过控件Width，不可继续添加！（"+"ConnectView_Width:"+Utils.dip2px(getContext(),width)+"px  Item_Width:"+widgetWidth+"px   ItemCount:"+getChildCount()+"）");
            return;
        }else{
            LinearLayout item = createConnectItem(resId,text);
            item.setOnClickListener(listener);
            addView(item);
        }
    }

    /**
     * 获取当前有几个三方登录
     * @return num
     */
    public int getConnectItemCount(){
        return getChildCount();
    }

    /**
     * 设置Umeng接收Activity回调
     * @param activity       调用的Activity
     * @param requestCode    状态吗
     * @param resultCode     返回码
     * @param data           数据
     */
    public void initResult(Activity activity,int requestCode, int resultCode, Intent data){
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }
}
