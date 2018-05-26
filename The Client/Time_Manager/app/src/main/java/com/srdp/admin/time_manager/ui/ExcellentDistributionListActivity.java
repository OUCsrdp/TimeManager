package com.srdp.admin.time_manager.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;

import java.util.ArrayList;
import java.util.List;

public class ExcellentDistributionListActivity extends AppCompatActivity {

    private List<ExcellentStudentsDistribution> distributionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_list);

        //ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学","计算机系","新出炉的时间分配表，请君共赏，现在二十个字了","2017-9-6",129);
        //distributionList.add(apple);
        //distributionList.add(0,apple);

        //initDistributions(); // 初始化数据
//        ExcellentDistributionListAdapter adapter = new ExcellentDistributionListAdapter(ExcellentDistributionListActivity.this, R.layout.list_item, distributionList);
//        ListView listView = (ListView) findViewById(R.id.distribution_list);
//        listView.setAdapter(adapter);

    }

    private void initDistributions() {

            ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(apple);
            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(banana);
            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(orange);
            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(watermelon);
            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(pear);
            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(grape);
            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(pineapple);
            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(strawberry);
            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(cherry);
            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
            distributionList.add(mango);



    }
}
