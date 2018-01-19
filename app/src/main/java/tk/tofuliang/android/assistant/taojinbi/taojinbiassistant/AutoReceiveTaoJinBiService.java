package tk.tofuliang.android.assistant.taojinbi.taojinbiassistant;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AutoReceiveTaoJinBiService extends AccessibilityService {
    private static final String TAG = "ReceiveTaoJinBiService";

//    static AccessibilityNodeInfo EntryNode = null;

    static Boolean entryTaoJinBi = false;
    /**
     * 淘宝主页
     */
    private String TAOBAO_MAIN_ACTIVITY = "com.taobao.tao.homepage.MainActivity3";
    private String TAOBAO_TAOJINBI_INDEX_ACTIVITY = "com.taobao.browser.BrowserActivity";

    public AutoReceiveTaoJinBiService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent ev) {

        CharSequence className = ev.getClassName();
        Log.d(TAG, "Activity:" + className);

        if (ev.getSource() == null) {
            return;
        }
        if (TAOBAO_MAIN_ACTIVITY.equals(className)) {
            entryTaoJinBi = false;
            int count = ev.getSource().getChildCount();
            Log.i(TAG, "view count:" + count);
            Log.d(TAG, "Activity:" + className);
            Log.d(TAG, "Event:" + ev.toString());

            for (int i = 0; i < count; i++) {
                if (entryTaoJinBi) break;
                AccessibilityNodeInfo node = ev.getSource().getChild(i);
                recycleMethod(node);
            }

//            if (!entryTaoJinBi && EntryNode != null) {
//                EntryNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }
        }
        if (TAOBAO_TAOJINBI_INDEX_ACTIVITY.equals(className)) {
            int count = ev.getSource().getChildCount();
            Log.i(TAG, "view count:" + count);
            Log.d(TAG, "Activity:" + className);
            Log.d(TAG, "Event:" + ev.toString());
            findAndPerformAction("今日任务");

//            for (int i = 0; i < count; i++) {
//                AccessibilityNodeInfo node = ev.getSource().getChild(i);
//                recycleMethod(node);
//            }

        }
//        if (SHOPPING_CART_ACTIVITY.equals(className)){
//            findAndPerformAction("去结算");
//        }
//        if (XIAOMI_SHOP_MAINACTIVITY.equals(className)){
//            int count = ev.getSource().getChildCount();
//            for (int i = 0; i < count;i++)
//            {
//                AccessibilityNodeInfo node = ev.getSource().getChild(i);
//                recycleMethod(node);
//            }
//            //   findAndPerformAction("手环及配件");
//        }
//        findAndPerformAction("去付款");
    }

    public void recycleMethod(AccessibilityNodeInfo node) {
        if (node != null) {
            int count = node.getChildCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Log.i(TAG, "父容器：" + node.getClassName());
                    AccessibilityNodeInfo nodes = node.getChild(i);
                    recycleMethod(nodes);
                }
            } else {
                Log.i(TAG, "child:" + node.getClassName());
                Log.i(TAG, "text:" + node.getText());
                if (!TextUtils.isEmpty(node.getContentDescription()) && "领金币".equals(node.getContentDescription().toString())) {
                    Log.e(TAG, "获取到 领金币按钮 父节点 " + node.getParent());
                    Log.e(TAG, "获取到 领金币按钮 " + node);
                    Boolean result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.e(TAG, "领金币按钮 点击结果  " + result);
                    entryTaoJinBi = true;
                    SystemClock.sleep(3000);
                }
                if (!TextUtils.isEmpty(node.getContentDescription()) && "今日任务".equals(node.getContentDescription().toString())) {
                    if (!node.isClickable()) node.setClickable(true);
                    AccessibilityNodeInfo n = node;
                    Integer i = 0;
                    while (n != null) {
                        i = i + 1;
                        Log.d(TAG, "第" + i + " : " + n);
                        n = n.getParent();
                    }
                    Boolean result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.e(TAG, "今日任务按钮 点击结果  " + result);
                }
                if (!TextUtils.isEmpty(node.getContentDescription()) && "瓜分金币".equals(node.getContentDescription().toString())) {
                    if (!node.isClickable()) node.setClickable(true);
                    Boolean result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.e(TAG, "瓜分金币按钮 点击结果  " + result);
                }
                if (!TextUtils.isEmpty(node.getContentDescription()) && "\\u70B9\\u51FB\\u5F00\\u5B9D\\u7BB1\\u9886\\u91D1\\u5E01".equals(node.getContentDescription().toString())) {
                    Boolean result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.e(TAG, "点击开宝箱领金币按钮 点击结果  " + result);
                }
                if (!TextUtils.isEmpty(node.getContentDescription()) && "每日签到领金币".equals(node.getContentDescription().toString())) {
                    Boolean result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.e(TAG, "点击每日签到领金币按钮 点击结果  " + result);
                }

            }
        }
    }


    @Override
    public void onInterrupt() {
    }

    @SuppressLint("NewApi")
    private void findAndPerformAction(String text) {
        //查找当前窗口中包含“购物车”文字的按钮
        if (getRootInActiveWindow() == null) {
            return;
        }
        AccessibilityNodeInfo root = getRootInActiveWindow();
        Integer c = root.getChildCount();
        for (int j=0;j<c;j++){
            Log.i(TAG,"child: "+root.getChild(j));
        }
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);

        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            Log.i(TAG, node.getClassName() + "----" + node.getText());
            //执行按钮点击行为
//            if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
//                Log.i(TAG, "获取到结算按钮" + node.getParent());
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }else
            if (text.equals(node.getText().toString())) {
                Log.i(TAG, "获取到"+ text +"控件" + node.getParent());
                node.getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

        }

    }
}
