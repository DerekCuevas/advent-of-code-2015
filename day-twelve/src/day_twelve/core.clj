(ns day-twelve.core
  (:gen-class))

(defn sum
 ([json]
  (sum 0 json))
 ([running-sum json]
  (cond
    (vector? json)
      (reduce sum running-sum json)
    (map? json)
      (reduce sum running-sum (vals json))
    (number? json)
      (+ running-sum json)
    :else
      running-sum)))

(defn sum-without-red
 ([json]
  (sum-without-red 0 json))
 ([running-sum json]
  (cond
    (vector? json)
      (reduce sum-without-red running-sum json)
    (map? json)
      (if (not-any? (partial = "red") (vals json))
        (reduce sum-without-red running-sum (vals json))
        running-sum)
    (number? json)
      (+ running-sum json)
    :else
      running-sum)))
