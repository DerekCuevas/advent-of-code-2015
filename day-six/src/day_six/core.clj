(ns day-six.core
  (:require [clojure.string :as string])
  (:gen-class))

(def ^:private grid-size 1000)

(defn- parse-instruction [s]
  (let [format #"(turn off|turn on|toggle) (\d+),(\d+) through (\d+),(\d+)"
        [op & coords] (vec (rest (re-find format s)))
        [x1 y1 x2 y2] (mapv read-string coords)]
    {:op (keyword (string/replace op #" " "-"))
     :start [x1 y1]
     :end [x2 y2]}))

(defn- init-grid [size init]
  (vec (repeat size (vec (repeat size init)))))

(defn- coordinates [[x1 y1] [x2 y2]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))]
    [x y]))

(defn- update-grid [light-type grid {:keys [op start end]}]
  (->> (coordinates start end)
       (reduce #(update-in %1 %2 (partial light-type op)) grid)))

(defn- configure-lights [light-type coll]
  (->> (map parse-instruction coll)
       (reduce (partial update-grid light-type) (init-grid grid-size 0))))

(defn- sum-brightness [grid]
  (reduce + (flatten grid)))

;; part one

(defn- binary-light [op state]
  (case op
    :turn-on 1
    :turn-off 0
    :toggle (bit-flip state 0)))

(defn total-lights-on [coll]
  (sum-brightness (configure-lights binary-light coll)))

;; part two

(defn- multi-light [op state]
  (case op
    :turn-on (inc state)
    :turn-off (if (zero? state) 0 (dec state))
    :toggle (+ 2 state)))

(defn total-brightness [coll]
  (sum-brightness (configure-lights multi-light coll)))
