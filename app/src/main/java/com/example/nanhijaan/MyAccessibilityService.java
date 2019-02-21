package com.example.nanhijaan;


import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    int mDebugDepth;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        mDebugDepth = 0;
        AccessibilityNodeInfo mNodeInfo = event.getSource();
        printAllViews(mNodeInfo);
    }

    private void printAllViews(AccessibilityNodeInfo mNodeInfo) {
        if (mNodeInfo == null) return;
        String log ="";
        log+=mNodeInfo.getText() +". ";
        if (mNodeInfo.getChildCount() < 1) return;
        mDebugDepth++;

        for (int i = 0; i < mNodeInfo.getChildCount(); i++) {
            printAllViews(mNodeInfo.getChild(i));
        }
        mDebugDepth--;
    }

    @Override
    public void onInterrupt() {

    }
}