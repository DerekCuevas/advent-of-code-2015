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

(defn corner-coords [[width height]]
  [[0 0] [(dec width) 0]
   [0 (dec height)] [(dec width) (dec height)]])

(defn corner? [size [x y]]
  (not (empty?
        (filter (fn [[cx cy]] (and (== x cx) (== y cy)))
                (corner-coords size)))))

(defn update-grid [[width height] grid]
  (reduce
   (fn [updated-grid coord]
     (update-in updated-grid coord
                (fn [current-state]
                  (let [neighbor-states (map #(get-in grid % false) (neighbor-coords coord))
                        neighboring-lights-on (count (filter identity neighbor-states))]
                    (cond
                      (corner? [width height] coord) true
                      (and current-state (or (== neighboring-lights-on 2) (== neighboring-lights-on 3))) true
                      (and (not current-state) (== neighboring-lights-on 3)) true
                      :else false)))))
   grid (coords width height)))

(defn animate-grid [steps [width height] grid]
  (reduce (fn [updated-grid _]
            (update-grid [width height] updated-grid))
          grid
          (range steps)))

;; (count-lights-on (animate-grid 100 input-grid-size input-grid))

(def input-part-two
  (reduce #(assoc-in %1 %2 true) input-grid
          (corner-coords input-grid-size)))

(count-lights-on (animate-grid 100 input-grid-size input-part-two))
