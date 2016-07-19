(ns day-six.core
  (:require [clojure.string :as string])
  (:gen-class))

(def ^:private grid-size 1000)
(def ^:private input (string/split (slurp "resources/input.txt") #"\n"))

(defn- parse-instruction [s]
  (let [format #"(turn off|turn on|toggle) (\d+),(\d+) through (\d+),(\d+)"
        [type & coords] (vec (rest (re-find format s)))
        [fx fy tx ty] (mapv read-string coords)]
    {:type (keyword (string/replace type #" " "-"))
     :from [fx fy]
     :to [tx ty]}))

(defn- init-grid [n init]
  (vec (for [x (range n)]
         (vec (repeat n init)))))

(defn- coordinates [[fx fy] [tx ty]]
  (for [i (range fx (inc tx))
        j (range fy (inc ty))]
    [i j]))

(defn- update-light [type prev]
  (case type
    :turn-on true
    :turn-off false
    :toggle (not prev)))

(defn- update-grid [grid {:keys [type from to]}]
  (->> (coordinates from to)
       (reduce #(update-in %1 %2 (partial update-light type)) grid)))

(defn- configure-lights [coll]
  (->> (map parse-instruction coll)
       (reduce update-grid (init-grid grid-size false))))

(defn- lights-on [lights]
  (reduce
   #(if (get-in lights %2) (inc %1) %1)
   0
   (coordinates [0 0] [(dec grid-size) (dec grid-size)])))

(defn count-lights-on [coll]
  (lights-on (configure-lights coll)))
