(ns day-eleven.core
  (:gen-class))

(defn- inc-char [c]
  (char (inc (int c))))

(defn- butlast-str [s]
  (apply str (butlast s)))

(defn- increment [s]
  (cond
    (empty? s)
      \a
    (= (last s) \z)
      (str (increment (butlast-str s)) \a)
    :else
      (str (butlast-str s) (inc-char (last s)))))

(defn- ordered? [coll]
  (->> coll
       (reduce #(if (not= %2 (inc %1)) (reduced false) %2))
       (false?)
       (not)))

(defn- any-increasing-three? [s]
  (->> (partition 3 1 s)
       (map #(ordered? (map int %)))
       (some true?)))

(defn- not-in-blacklist? [s]
  (not-any? #{\i \o \u} s))

(defn- two-overlapping-pairs? [s]
  (->> (partition 2 1 s)
       (filter #(apply = %))
       (distinct)
       (count)
       (<= 2)))

(def ^:private rules
  (juxt any-increasing-three?
        not-in-blacklist?
        two-overlapping-pairs?))

(defn- password? [s]
  (every? true? (rules s)))

(defn next-password [password]
  (loop [candidate (increment password)]
    (if (password? candidate)
      candidate
      (recur (increment candidate)))))
