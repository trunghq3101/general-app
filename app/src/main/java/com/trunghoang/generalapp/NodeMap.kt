package com.trunghoang.generalapp

import java.lang.StringBuilder
import kotlin.collections.ArrayList

class NodeMap(
    val nodes: List<Node> = ArrayList(),
    var steps: List<Pair<Int, Int>> = ArrayList(),
    var players: List<Player> = ArrayList()
) {
    fun getAvailableNodes(player: Player): List<Int> {
        return nodes[player.position].availableNodes(this)
    }

    fun stepMap(start: Int, des: Int): NodeMap {
        val newPlayers = stepPlayers(players, start, des)
        return NodeMap(
            step(start, des, newPlayers),
            recordStep(start, des),
            newPlayers)
    }

    fun getState(): State {
        return getState(players)
    }

    fun getState(players: List<Player>): State {
        val humanState = StringBuilder()
        val wolvesState = StringBuilder()
        players.sortedBy {
            it.position
        }.forEach {
            if (it.owner == 1) {
                humanState.append("${it.position}")
            } else {
                wolvesState.append("${it.position}")
            }
        }
        return State(humanState.toString(), wolvesState.toString())
    }

    private fun step(start: Int, des: Int, newPlayers: List<Player>): List<Node> {
        val newNodes = nodes.map { it.copy() }
        newNodes[start].owner = getOwner(newPlayers, start)
        newNodes[des].owner = getOwner(newPlayers, des)
        return newNodes
    }

    private fun recordStep(start: Int, des: Int): List<Pair<Int, Int>> {
        val newSteps = steps.map { it.copy() } as ArrayList<Pair<Int, Int>>
        newSteps.add(Pair(start, des))
        return newSteps
    }

    fun checkWin(): Boolean {
        return (nodes[1].owner == -1 &&
                nodes[2].owner == -1 &&
                nodes[3].owner == -1 &&
                nodes[10].owner == 1 &&
                nodes[11].owner == 1 &&
                nodes[12].owner == 1)
    }

    fun printResult() {
        val s = StringBuilder()
        println("Steps: ${steps.size}")
        steps.forEach {
            s.append("${it.first} -> ${it.second}")
            s.appendln()
        }
        println(s)
    }

    fun stepPlayers(players: List<Player>, start: Int, des: Int): List<Player> {
        val newPlayers = players.map { it.copy() } as ArrayList<Player>
        newPlayers.find {
            it.position == start
        }?.let {
            val player = it.copy()
            newPlayers.remove(it)
            player.position = des
            newPlayers.add(player)
        }
        return newPlayers
    }

    private fun getOwner(players: List<Player>, position: Int): Int {
        players.find { it.position == position }?.let {
            return it.owner
        } ?: let {
            return 2
        }
    }
}