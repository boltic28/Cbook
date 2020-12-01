package com.boltic28.databind.adapter

import com.boltic28.databind.dto.interf.BaseItem

interface OnRowClicked {
    fun onClick(item: BaseItem)
}

interface OnWorkClicked {
    fun onClick(item: BaseItem)
}