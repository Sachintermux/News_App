package com.sna.newsapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;



public class MainActivity_ViewModel extends ViewModel {

    MutableLiveData<ArrayList<NewsTab_DataModel>> newsArrayListMutableLiveData = new MutableLiveData<>(new ArrayList<>());

    public void setNewsArrayListMutableLiveData( ArrayList<NewsTab_DataModel> newsArrayListMutableLiveData ) {
        this.newsArrayListMutableLiveData.postValue(newsArrayListMutableLiveData);
    }

}
