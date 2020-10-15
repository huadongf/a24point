package com.example.a24point

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val kapians= mutableListOf(
        Kapian("fangkuai1", R.drawable.fangkuai1,1),
        Kapian("heitao1", R.drawable.heitao1,1),
        Kapian("hongtao1", R.drawable.hongtao1,1),
        Kapian("meihua1", R.drawable.meihua1,1),
        Kapian("fangkuai2", R.drawable.fangkuai2,2),
        Kapian("heitao2", R.drawable.heitao2,2),
        Kapian("hongtao2", R.drawable.hongtao2,2),
        Kapian("meihua2", R.drawable.meihua2,2) ,
        Kapian("fangkuai3", R.drawable.fangkuai3,3),
        Kapian("heitao3", R.drawable.heitao3,3),
        Kapian("hongtao3", R.drawable.hongtao3,3),
        Kapian("meihua3", R.drawable.meihua3,3),
        Kapian("fangkuai4", R.drawable.fangkuai4,4),
        Kapian("heitao4", R.drawable.heitao4,4),
        Kapian("hongtao4", R.drawable.hongtao4,4),
        Kapian("meihua4", R.drawable.meihua4,4),
        Kapian("fangkuai5", R.drawable.fangkuai5,5),
        Kapian("heitao5", R.drawable.heitao5,5),
        Kapian("hongtao5", R.drawable.hongtao5,5),
        Kapian("meihua5", R.drawable.meihua5,5),
        Kapian("fangkuai6", R.drawable.fangkuai6,6),
        Kapian("heitao6", R.drawable.heitao6,6),
        Kapian("hongtao6", R.drawable.hongtao6,6),
        Kapian("meihua6", R.drawable.meihua6,6),
        Kapian("fangkuai7", R.drawable.fangkuai7,7),
        Kapian("heitao7", R.drawable.heitao7,7),
        Kapian("hongtao7", R.drawable.hongtao7,7),
        Kapian("meihua7", R.drawable.meihua7,7),
        Kapian("fangkuai8", R.drawable.fangkuai8,8),
        Kapian("heitao8", R.drawable.heitao8,8),
        Kapian("hongtao8", R.drawable.hongtao8,8),
        Kapian("meihua8", R.drawable.meihua8,8),
        Kapian("fangkuai9", R.drawable.fangkuai9,9),
        Kapian("heitao9", R.drawable.heitao9,9),
        Kapian("hongtao9", R.drawable.hongtao9,9),
        Kapian("meihua9", R.drawable.meihua9,9),
        Kapian("fangkuai10", R.drawable.fangkuai10,10),
        Kapian("heitao10", R.drawable.heitao10,10),
        Kapian("hongtao10", R.drawable.hongtao10,10),
        Kapian("meihua10", R.drawable.meihua10,10),
        Kapian("fangkuai11", R.drawable.fangkuai11,11),
        Kapian("heitao11", R.drawable.heitao11,11),
        Kapian("hongtao11", R.drawable.hongtao11,11),
        Kapian("meihua11", R.drawable.meihua11,11),
        Kapian("fangkuai12", R.drawable.fangkuai12,12),
        Kapian("heitao12", R.drawable.heitao12,12),
        Kapian("hongtao12", R.drawable.hongtao12,12),
        Kapian("meihua12", R.drawable.meihua12,12),
        Kapian("fangkuai13", R.drawable.fangkuai13,13),
        Kapian("heitao13", R.drawable.heitao13,13),
        Kapian("hongtao13", R.drawable.hongtao13,13),
        Kapian("meihua13", R.drawable.meihua13,13)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val my= ViewModelProvider(this).get(Myvi::class.java)
        fab.setOnClickListener {
            if(my.xuanzeList.size<4)
                Toast.makeText(this,"请选择4张扑克牌!",Toast.LENGTH_SHORT).show()
            else
            {
                val intent=Intent(this,MainActivity2::class.java).apply {
                    putExtra(MainActivity2.ONE, my.xuanzeList[0].siz.toDouble())
                    putExtra(MainActivity2.TWO, my.xuanzeList[1].siz.toDouble())
                    putExtra(MainActivity2.THREE, my.xuanzeList[2].siz.toDouble())
                    putExtra(MainActivity2.FOUR, my.xuanzeList[3].siz.toDouble())
                }
                startActivity(intent)
            }
        }
        for(i in 0..51)
            my.kapianList.add(kapians[i])
        val lay=GridLayoutManager(this, 4)
        rec.layoutManager=lay
        my.ad=Selectadapter(this, my.xuanzeList)
        rec.adapter=my.ad
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter =Kapianadapter(this, my.kapianList)
        recyclerView.adapter = adapter

    }

}
