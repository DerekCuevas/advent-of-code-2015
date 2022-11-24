(ns day-eightteen.core
  (:require [clojure.string :as str])
  (:gen-class))

(def input-grid-size [100 100])

(def input-grid
  (vec (map (fn [line]
              (vec (map #(== 0 (compare (str %) "#")) line)))
            (str/split (slurp "resources/input.txt") #"\n"))))

(defn coords [width height]
  (for [x (range 0 width)
        y (range 0 height)]
    [x y]))

(defn add-coords [[ax ay] [bx by]]
  [(+ ax bx) (+ ay by)])

(defn neighbor-coords [coord]
  (map #(add-coords coord %)
       [[-1 1] [0 1] [1 1] [1 0] [1 -1] [0 -1] [-1 -1] [-1 0]]))

(defn count-lights-on [grid]
  (->> grid
       (flatten)
       (filter identity)
       (count)))

(defn update-grid [[width height] grid]
  (->> (coords width height)
       (reduce (fn [updated-grid coord]
                 (let [current-state (get-in grid coord)
                       neighbor-states (map #(get-in grid % false) (neighbor-coords coord))
                       neighboring-lights-on (count (filter identity neighbor-states))
                       next-state (cond
                                    (and current-state (or (== neighboring-lights-on 2) (== neighboring-lights-on 3))) true
                                    (and (not current-state) (== neighboring-lights-on 3)) true
                                    :else false)]
                   (assoc-in updated-grid coord next-state)))
               grid)))

(defn animate-grid [steps [width height] grid]
  (reduce (fn [updated-grid _]
            (update-grid [width height] updated-grid))
          grid
          (range steps)))

(count-lights-on (animate-grid 100 input-grid-size input-grid))
