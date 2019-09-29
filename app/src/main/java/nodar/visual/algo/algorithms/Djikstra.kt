package nodar.visual.algo.algorithms

import android.util.Log
import nodar.visual.algo.getNeighbourCells
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Djikstra {
    interface OnCompleteListener {
        fun onNextStep(position: Int)
    }

    var onCompleteListener: OnCompleteListener? = null
    val graph = HashMap<Int, Node>()
    var _distance = Array(1000) { IntArray(1000) }
    var destinationNodeName = 0
    var startNodeName = 0
    val pq = PriorityQueue<Node>()
    val parent = IntArray(1000)

    fun addonCompleteListener(_onCompleteListener: OnCompleteListener) {
        onCompleteListener = _onCompleteListener
    }

    fun initialise(start: Int, end: Int, emptyCells: ArrayList<Int>) {
        emptyCells.forEach { emptyCell ->
            emptyCell.getNeighbourCells()
                .forEach { neighbour ->
                    if (emptyCells.contains(neighbour)) {
                        _distance[emptyCell][neighbour] = 1
                        getNode(emptyCell).getChildren().add(getNode(neighbour))
                    }
                }
        }
        startNodeName = start
        destinationNodeName = end
        parent[startNodeName] = -1
        getNode(startNodeName).apply {
            distance = 0
            visited = true
        }
        pq.add(getNode(startNodeName))
    }

    fun djikstra() {
        while (pq.isNotEmpty()) {
            val node = pq.poll()
            node?.apply {
                visited = true
                getChildren().forEach { child ->
                    if (child.distance > node.distance + _distance[nodeName][child.nodeName] && !child.visited) {
                        child.distance = node.distance + _distance[nodeName][child.nodeName]
                        parent[child.nodeName] = nodeName
                        pq.add(child)
                    }
                }
            }
        }
        backTrack(parent, destinationNodeName)
    }

    fun backTrack(parent: IntArray, i: Int) {
        if (i == -1)
            return
        onCompleteListener?.onNextStep(i)
        Log.d("pathe", i.toString())
        backTrack(parent, parent[i])
    }

    class Node(val nodeName: Int, var distance: Int = Int.MAX_VALUE) : Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return distance.compareTo(other.distance)
        }

        var visited = false
        private val children = ArrayList<Node>()
        fun getChildren(): ArrayList<Node> {
            return children
        }
    }

    fun getNode(nodeName: Int): Node {
        if (!graph.containsKey(nodeName))
            graph[nodeName] = Node(nodeName)
        return graph[nodeName]!!
    }
}