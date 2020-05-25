package com.m3sv.dotttask.shared.navigation

interface Router<R> {
    fun navigateTo(route: R)
}
