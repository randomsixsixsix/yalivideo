package com.video.yali.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.video.yali.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistoryUtils {


    private final static String PREFERENCE_NAME = "yalishipin";
    private final static String SEARCH_HISTORY="yalisearch_history";

    public static void saveSearchHistory(String inputText) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }

            historyList.add(0, inputText);
            if (historyList.size() > 8) {
                historyList.remove(historyList.size() - 1);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }

            editor.putString(SEARCH_HISTORY, sb.toString());
            editor.commit();
        } else {

            editor.putString(SEARCH_HISTORY, inputText + ",");
            editor.commit();
        }
    }
    //获取搜索记录
    public static List<String> getSearchHistory(){
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory =sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            historyList.clear();
        }
        return historyList;
    }
}
