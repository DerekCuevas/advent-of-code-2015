(ns day-twenty.core)

(def presents-multiplier 10)

(def target-presents 29000000)

(defn divisible-by? [den x]
  (zero? (mod x den)))

(defn transpose [xs]
  (apply map list xs))

(defn sum-row [row]
  (reduce + (map-indexed #(* presents-multiplier (inc %) %2) row)))

(defn houses-seq [index]
  (->> (range 1 (inc index))
       (repeat index)
       (map-indexed #(map (fn [x] (if (divisible-by? (inc %) x) 1 0)) %2))
       (transpose)
       (map-indexed #(take (inc %) %2))
       (map sum-row)))

(defn first-house-with-at-least-x-presents [x-presents]
  (->> (houses-seq 100000)
       (take-while #(< % x-presents))
       (count)
       (inc)))

;; (first-house-with-at-least-x-presents target-presents)
