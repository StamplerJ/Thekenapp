package com.jann_luellmann.thekenapp.data.util

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Editable(val stringId: Int)