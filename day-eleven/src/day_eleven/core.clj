(ns day-eleven.core
  (:gen-class))

(defn- inc-char [c]
  (char (inc (int c))))

(defn- butlast-str [s]
  (apply str (butlast s)))

(defn- inc-str [s]
  (let [last-char (last s)
        butlast-char (butlast-str s)]
    (cond
      (empty? s)
        "a"
      (= last-char \z)
        (str (inc-str butlast-char) \a)
      :else
        (str butlast-char (inc-char last-char)))))

(defn- ordered? [[x :as coll]]
  (->> (iterate inc x)
       (take (count coll))
       (= coll)))

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
  (loop [candidate (inc-str password)]
    (if (password? candidate)
      candidate
      (recur (inc-str candidate)))))
