package com.boltic28.coroutinen

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class ItemForContext(
    val id: Long,
    val name: String,
    val icon: Int
) : AbstractCoroutineContextElement(ItemForContext) {
    companion object Key : CoroutineContext.Key<ItemForContext>
}