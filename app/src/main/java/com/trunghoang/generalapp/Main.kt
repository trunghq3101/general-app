package com.trunghoang.generalapp

import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val map = ArrayList<Node>()
    with(map) {
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
    val initPlayers = ArrayList<Player>().apply {
        add(Player(1, 1))
        add(Player(1, 2))
        add(Player(1, 3))
        add(Player(-1, 10))
        add(Player(-1, 11))
        add(Player(-1, 12))
    }
    val queue: Queue<NodeMap> = LinkedList<NodeMap>()
    val states = ArrayList<State>()
    queue.add(NodeMap(map, players = initPlayers))
    states.add(queue.peek().getState())
    while (queue.isNotEmpty()) {
        val nodeMap = queue.remove()
        with(nodeMap) {
            if (checkWin()) {
                printResult()
                return
            }
            players.forEach {
                getAvailableNodes(it).forEach { des ->
                    val start = it.position
                    if (!states.isStateExist(
                            getState(stepPlayers(players, start, des))
                        )
                    ) {
                        val newMap = stepMap(start, des)
                        queue.add(newMap)
                        states.add(newMap.getState())
                    }
                }
            }
        }
    }

    println("No Result")
}

fun ArrayList<State>.isStateExist(state: State) = this.find {
    (it.humanState == state.humanState) && (it.wolvesState == state.wolvesState)
}?.let {
    true
} ?: run {
    false
}