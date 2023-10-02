package com.tarantini.pantry.authentication

data class UserSession(val state: String, val accessToken: String)
