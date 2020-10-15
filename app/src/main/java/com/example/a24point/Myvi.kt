package com.example.a24point

import androidx.lifecycle.ViewModel

class Myvi:ViewModel(){
    val kapianList = ArrayList<Kapian>()
    val xuanzeList = ArrayList<Kapian>()
    lateinit var ad:Selectadapter
    lateinit var ac:Equationadapter
}