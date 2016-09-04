(ns day-eleven.core
  (:gen-class))

(defn increment [s]
  (cond
    (empty? s)
      \a
    (= (last s) \z)
      (str (increment (butlast s)) \a)
    :else
      (str (apply str (butlast s))
           (char (inc (int (last s)))))))

(defn ordered? [coll]
  (not
   (false?
    (reduce
   	 (fn [prev next]
       (if (not= next (inc prev))
         (reduced false)
         next))
     coll))))

(defn increasing-three? [s]
  (->> (partition 3 1 s)
       (map #(ordered? (map int %)))
       (some true?)))

(defn not-in-blacklist? [s]
  (not-any? #{\i \o \u} s))

(defn two-overlapping-pairs? [s]
  (->> (partition 2 1 s)
       (filter #(apply = %))
       (distinct)
       (count)
       (<= 2)))

(def rules
  (juxt increasing-three?
        not-in-blacklist?
        two-overlapping-pairs?))

(defn password? [s]
  (every? true? (rules s)))

(defn next-password [password]
  (loop [candidate (increment password)]
    (if (password? candidate)
      candidate
      (recur (increment candidate)))))
