(ns day-twelve.core
  (:gen-class))

(defn sum-numbers
  ([json]
   (sum-numbers 0 json))
  ([sum json]
   (cond
     (coll? json)
       (reduce sum-numbers sum json)
     (number? json)
       (+ sum json)
     :else
       sum)))

(defn sum-numbers-without-red
  ([json]
   (sum-numbers-without-red 0 json))
  ([sum json]
   (let [decend (partial reduce sum-numbers-without-red sum)]
     (cond
       (map? json)
         (if-not (some #{"red"} (vals json))
           (decend (vals json))
           sum)
       (vector? json)
         (decend json)
       (number? json)
         (+ sum json)
       :else
         sum))))
