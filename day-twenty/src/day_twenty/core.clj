(ns day-twenty.core)

(def presents-multiplier 10)

(defn divisible-by? [den x]
  (zero? (mod x den)))

(defn num-presents-at-house [house-index]
  (->> (range 1 (inc house-index))
       (repeat house-index)
       (map-indexed #(filter (partial divisible-by? (inc %)) %2))
       (map-indexed #(if (contains? (set %2) house-index) (inc %) nil))
       (filter some?)
       (map #(* presents-multiplier %))
       (reduce +)))

(defn first-house-with-at-least-x-presents [x-presents]
  (->> (iterate inc 1)
       (map num-presents-at-house)
       (take-while #(< % x-presents))
       (count)
       (inc)))

;; (first-house-with-at-least-x-presents 29000000)
