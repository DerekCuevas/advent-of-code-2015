(ns day-seventeen.core
  (:require [day-seventeen.utils :as utils])
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def liters 150)

(def capacities (map #(Integer/parseInt %) (split (slurp "resources/input.txt") #"\n")))

(def sum (partial reduce +))

(defn count-combinations [liters capacities]
  (->> capacities
       (utils/all-combinations)
       (map sum)
       (filter #(== % liters))
       (count)))

(defn count-min-combinations [liters capacities]
  (let [combs (->> capacities
                   (utils/all-combinations)
                   (filter #(== liters (sum %))))
        smallest (apply min (map count combs))]
    (->> combs
         (filter #(== smallest (count %)))
         (count))))

(count-combinations 25 [20 15 10 5 5])

(count-combinations liters capacities)

(count-min-combinations 25 [20 15 10 5 5])

(count-min-combinations liters capacities)
