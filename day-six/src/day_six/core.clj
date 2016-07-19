(ns day-six.core
  (:require [clojure.string :as string])
  (:gen-class))

(def ^:private grid-size 1000)
(def ^:private grid-edge (dec grid-size))

(defn- parse-instruction [s]
  (let [format #"(turn off|turn on|toggle) (\d+),(\d+) through (\d+),(\d+)"
        [op & coords] (vec (rest (re-find format s)))
        [sx sy ex ey] (mapv read-string coords)]
    {:op (keyword (string/replace op #" " "-"))
     :start [sx sy]
     :end [ex ey]}))

(defn- init-grid [size init]
  (vec (repeat size (vec (repeat size init)))))

(defn- coordinates [[sx sy] [ex ey]]
  (for [i (range sx (inc ex))
        j (range sy (inc ey))]
    [i j]))

(defn- update-grid [light-type grid {:keys [op start end]}]
  (->> (coordinates start end)
       (reduce #(update-in %1 %2 (partial light-type op)) grid)))

(defn- configure-lights [light-type coll]
  (->> (map parse-instruction coll)
       (reduce (partial update-grid light-type) (init-grid grid-size 0))))

(defn- sum-brightness [grid]
  (->> (coordinates [0 0] [grid-edge grid-edge])
       (reduce #(+ %1 (get-in grid %2)) 0)))

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
    :turn-off (if-not (neg? (dec state)) (dec state) 0)
    :toggle (+ 2 state)))

(defn total-brightness [coll]
  (sum-brightness (configure-lights multi-light coll)))
