(ns day-one.core
  (:gen-class))

(defn travel [directions]
  (->> directions
       (map #(case % \( 1 \) -1))
       (reduce +)))

(defn floors-to-basement [directions]
  (reduce-kv
   (fn [floor position direction]
     (let [next-floor (+ floor direction)]
       (if (= next-floor -1)
         (reduced (inc position))
         next-floor)))
   0
   (mapv #(case % \( 1 \) -1) directions)))
