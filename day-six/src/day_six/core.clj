(ns day-six.core
  (:require [clojure.string :as string])
  (:gen-class))

(def ^:private grid-size 1000)

(defn- parse-instruction [s]
  (let [format #"(turn off|turn on|toggle) (\d+),(\d+) through (\d+),(\d+)"
        [type & coords] (vec (rest (re-find format s)))
        [sx sy ex ey] (mapv read-string coords)]
    {:type (keyword (string/replace type #" " "-"))
     :start [sx sy]
     :end [ex ey]}))

(defn- init-grid [size init]
  (vec (repeat size (vec (repeat size init)))))

(defn- coordinates [[sx sy] [ex ey]]
  (for [i (range sx (inc ex))
        j (range sy (inc ey))]
    [i j]))

(defn- update-light [type state]
  (case type
    :turn-on true
    :turn-off false
    :toggle (not state)))

(defn- update-grid [grid {:keys [type start end]}]
  (->> (coordinates start end)
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
