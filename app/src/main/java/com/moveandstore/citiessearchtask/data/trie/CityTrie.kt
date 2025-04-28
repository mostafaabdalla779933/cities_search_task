package com.moveandstore.citiessearchtask.data.trie

import com.moveandstore.citiessearchtask.data.model.City

/**
In the beginning, I was undecided between two data structures: TreeMap and Trie.
I ultimately preferred using Trie because it offers faster performance for autocomplete and prefix-based string search operations.
However, the main drawback of Trie is that it consumes more memory (RAM) compared to other structures like TreeMap.
 */

class CityTrie {
    private val root = TrieNode()

    fun insert(city: City) {
        var node = root
        val name = city.name.lowercase()

        for (char in name) {
            node = node.children.getOrPut(char) { TrieNode() }
            node.cities.add(city)
        }

        node.isEndOfWord = true
    }

    fun searchPrefix(prefix: String): List<City> {
        var node = root
        for (char in prefix.lowercase()) {
            node = node.children[char] ?: return emptyList()
        }
        return node.cities
    }
}