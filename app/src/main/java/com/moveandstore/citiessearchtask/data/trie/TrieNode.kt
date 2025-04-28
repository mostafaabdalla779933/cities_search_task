package com.moveandstore.citiessearchtask.data.trie

import com.moveandstore.citiessearchtask.data.model.City

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    val cities = mutableListOf<City>()
    var isEndOfWord = false
}