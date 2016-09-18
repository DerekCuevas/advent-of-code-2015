(ns day-thirteen.core
  (:require [clojure.math.combinatorics :refer [permutations]])
  (:gen-class))

(def ^:private instruction-format
  #"(\S+) would (gain|lose) (\d+) happiness units by sitting next to (\S+).")

(defn- parse-edge [s]
  (let [[_ guest sign happiness neighbor] (re-find instruction-format s)
        value (read-string happiness)]
    {:pair [(keyword guest) (keyword neighbor)]
     :distance (if (= sign "lose") (* -1 value) value)}))

(defn- add-edge [graph {distance :distance [from to] :pair}]
  (update graph from #(conj % {to distance})))

(defn build-graph [edges]
  (->> (map parse-edge edges)
       (reduce add-edge {})))

(defn- happiness [graph from to]
  (some to (get graph from)))

(defn- sum-happiness [graph arrangement]
  (->> arrangement
       (map #(+ (apply happiness graph %)
                (apply happiness graph (reverse %))))
       (reduce +)))

(defn max-happiness [graph]
  (->> (keys graph)
       (permutations)
       (map #(partition 2 1 [(first %)] %))
       (map (partial sum-happiness graph))
       (apply max)))

;; part two
(defn add-guest [graph guest happiness]
  (->> (keys graph)
       (mapcat (fn [neighbor] [[guest neighbor] [neighbor guest]]))
       (map (fn [[from to]] {:pair [from to] :distance happiness}))
       (reduce add-edge graph)))
