package com.trunghoang.generalapp

import java.lang.StringBuilder

class NodeMap(
    val nodes: List<Node> = ArrayList(),
    var steps: List<Pair<Int, Int>> = ArrayList(),
    var players: List<Player> = ArrayList()
) {
    fun start(): List<Pair<Int, Int>>? {
        if (checkWin()) return steps
        if (isStateExist(states)) return null
        if (steps.size == 30) return null
        val newPlayers = rearrangePlayers(players)
        newPlayers.forEach {
            start(newPlayers, it)?.let { steps ->
                return steps
            }
        }
        return null
    }

    private fun start(players: List<Player>, player: Player): List<Pair<Int, Int>>? {
        val start = player.position
        nodes[start].availableNodes(this).forEach { des ->
            if (checkSteps(steps, des)) {
                if (steps.size <= 16) {
                    println("${states.last()} x${steps.size} : $start $des")
                }
                val newPlayers = stepPlayers(players, start, des)
                genNewStates(newPlayers)
                val newMap = NodeMap(
                    step(start, des, newPlayers),
                    recordStep(start, des),
                    newPlayers)
                newMap.start()?.let { steps ->
                    return steps
                }
            }
        }
        return null
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

    private fun checkWin(): Boolean {
        return (nodes[1].owner == -1 &&
                nodes[2].owner == -1 &&
                nodes[3].owner == -1 &&
                nodes[10].owner == 1 &&
                nodes[11].owner == 1 &&
                nodes[12].owner == 1)
    }

    private fun stepPlayers(players: List<Player>, start: Int, des: Int): List<Player> {
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

    private fun checkSteps(steps: List<Pair<Int, Int>>, des: Int) =
            (steps.isEmpty() || (steps.isNotEmpty() && des != steps.last().first))

    private fun rearrangePlayers(players: List<Player>): List<Player> {
        val newPlayers = players.map { it.copy() } as ArrayList<Player>
        players.forEach { player ->
            nodes[player.position].availableNodes(this).find { index ->
                if (player.owner == 1) {
                    index > player.position
                } else {
                    index < player.position
                }
            } ?: let {
                newPlayers.remove(player)
                newPlayers.add(player)
            }
        }
        return newPlayers.map { it.copy() }
    }

    private fun isStateExist(states: List<Pair<String, String>>): Boolean {
        states.forEachIndexed { index, l ->
            if (index != states.lastIndex && l == states.last()) {
                return true
            }
        }
        return false
    }

    companion object {
        var states = ArrayList<Pair<String, String>>()
        fun genNewStates(players: List<Player>) {
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
            states.add(Pair(humanState.toString(), wolvesState.toString()))
        }
    }
}