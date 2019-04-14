package com.trunghoang.generalapp

fun main() {
    val map = ArrayList<Node>()
    with (map) {
        add(Node(0, listOf(), 2))
        add(Node(1, listOf(6, 8), 1))
        add(Node(2, listOf(7, 9), 1))
        add(Node(3, listOf(4, 8), 1))
        add(Node(4, listOf(3, 9, 11), 2))
        add(Node(5, listOf(10, 12), 2))
        add(Node(6, listOf(1, 7, 11), 2))
        add(Node(7, listOf(2, 6, 12), 2))
        add(Node(8, listOf(1, 3), 2))
        add(Node(9, listOf(2, 4, 10), 2))
        add(Node(10, listOf(5, 9), -1))
        add(Node(11, listOf(4, 6), -1))
        add(Node(12, listOf(5, 7), -1))
    }
    val players = ArrayList<Player>().apply {
        add(Player(1, 1))
        add(Player(1, 2))
        add(Player(1, 3))
        add(Player(-1, 10))
        add(Player(-1, 11))
        add(Player(-1, 12))
    }
    NodeMap.genNewStates(players)
    val nodeMap = NodeMap(map, players = players)
    nodeMap.start()?.let {
        val s = StringBuilder()
        it.forEach {
            s.append("${it.first} -> ${it.second}")
            s.appendln()
        }
        println(s)
    } ?: run {
        println("No result")
    }
}