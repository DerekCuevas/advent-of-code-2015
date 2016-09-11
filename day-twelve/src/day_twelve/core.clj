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
