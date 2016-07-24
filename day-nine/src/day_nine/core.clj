(ns day-nine.core
  (:require [clojure.string :refer [split]]
            [clojure.math.combinatorics :refer [permutations]])
  (:gen-class))

(defn- parse-edge [s]
  (let [[lhs rhs] (split s #" = ")]
    {:pair (mapv keyword (split lhs #" to "))
     :distance (read-string rhs)}))

(defn- add-edge [graph {distance :distance [to from] :pair}]
  (-> graph
      (update from #(conj % {to distance}))
      (update to #(conj % {from distance}))))

(defn build-graph [edges]
  (->> (map parse-edge edges)
       (reduce add-edge {})))

(defn- distance [graph to from]
  (some from (get graph to)))

(defn- path-length [graph path]
  (->> (partition 2 1 path)
       (map #(apply distance graph %))
       (reduce +)))

(defn- possible-path-lengths [graph]
  (->> (permutations (keys graph))
       (map (partial path-length graph))))

(defn shortest-route [graph]
  (apply min (possible-path-lengths graph)))

(defn longest-route [graph]
  (apply max (possible-path-lengths graph)))
