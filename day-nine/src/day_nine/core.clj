(ns day-nine.core
  (:require [clojure.string :refer [split]]
            [clojure.math.combinatorics :as combo])
  (:gen-class))

(defn- parse-connection [s]
  (let [[lhs rhs] (split s #" = ")]
    {:pair (mapv keyword (split lhs #" to "))
     :distance (read-string rhs)}))

(defn- add-connection [graph {distance :distance [to from] :pair}]
  (-> graph
      (update from #(conj % {to distance}))
      (update to #(conj % {from distance}))))

(defn build-graph [connections]
  (->> (map parse-connection connections)
       (reduce add-connection {})))

(defn- distance [graph to from]
  (->> (get graph to)
       (some from)))

(defn- path-length [graph path]
  (->> (partition 2 1 path)
       (map #(apply distance graph %))
       (reduce +)))

(defn- compute-distances [graph]
  (->> (combo/permutations (keys graph))
       (map (partial path-length graph))))

(defn shortest-route [graph]
  (apply min (compute-distances graph)))

(defn longest-route [graph]
  (apply max (compute-distances graph)))
