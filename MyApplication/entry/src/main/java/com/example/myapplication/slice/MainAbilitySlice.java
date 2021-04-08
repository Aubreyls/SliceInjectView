package com.example.myapplication.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.CommonEventSubscriber;
import ohos.event.commonevent.CommonEventSupport;
import ohos.event.commonevent.MatchingSkills;
import ohos.rpc.RemoteException;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.common.utils.BindView;
import com.example.myapplication.common.utils.BindViewClick;
import com.example.myapplication.common.utils.Log;
import com.example.myapplication.common.utils.MyViewUtils;

public class MainAbilitySlice extends AbilitySlice {
    private final static String TAG = "MainAbilitySlice";
    private final String event = "com.my.test";
    private MyCommonEventSubscriber subscriber;
    @BindView(ResourceTable.Id_text_helloworld)
    private Text content;
    @BindView(ResourceTable.Id_send_event)
    private Button button;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        MyViewUtils.bind(this);

        //创建并且订阅公共事件
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(event); // 自定义事件
        matchingSkills.addEvent(CommonEventSupport.COMMON_EVENT_SCREEN_ON); // 亮屏事件
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        subscriber = new MyCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception occurred during subscribeCommonEvent invocation.");
        }

    }

    @BindViewClick(ResourceTable.Id_send_event)
    private void onClick(Component component) {
        try {
            //发送公共事件
            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                .withAction(event).build();
            intent.setOperation(operation);
            CommonEventData eventData = new CommonEventData(intent);
            CommonEventManager.publishCommonEvent(eventData);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception occurred during publishCommonEvent invocation.");
        }
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
        //退订公共事件
        try {
            CommonEventManager.unsubscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            Log.e(TAG, "Exception occurred during unsubscribeCommonEvent invocation.");
        }
    }


    //公共事件接收器
    class MyCommonEventSubscriber extends CommonEventSubscriber {

        public MyCommonEventSubscriber(CommonEventSubscribeInfo subscribeInfo) {
            super(subscribeInfo);
        }

        @Override
        public void onReceiveEvent(CommonEventData commonEventData) {
            content.setText("收到广播啦");
        }
    }
}
