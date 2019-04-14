package com.trunghoang.generalapp

data class Node(
    var index: Int,
    var adjacent: List<Int>,
    var owner: Int
) {
    fun availableNodes(map: NodeMap) = adjacent.filter {
        map.nodes[it].adjacent.find {
            map.nodes[it].owner == -owner
        }?.let {
            false
        } ?: true
    }.let { availablePositions ->
        if (owner == 1) {
            availablePositions.reversed()
        } else {
            availablePositions
        }
    }
}